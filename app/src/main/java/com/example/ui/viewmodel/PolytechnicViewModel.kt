package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.AppDatabase
import com.example.data.model.AcademicProgramme
import com.example.data.model.AdmissionApplication
import com.example.data.model.SchoolAnnouncement
import com.example.data.model.SchoolPayment
import com.example.data.model.User
import com.example.data.repository.AdminMetrics
import com.example.data.repository.PolytechnicRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class ApplicationFormState(
  // Step 1: Personal
  val fullName: String = "",
  val dateOfBirth: String = "2005-06-15",
  val gender: String = "Male",
  val nationality: String = "Nigerian",
  val stateOfOrigin: String = "Akwa Ibom State",
  val lga: String = "Uyo",
  val residentialAddress: String = "",
  val phone: String = "",
  val whatsappNumber: String = "",
  val email: String = "",

  // Step 2: Academic
  val previousSchool: String = "",
  val examType: String = "WAEC",
  val examNumber: String = "",
  val examYear: String = "2024",
  val englishGrade: String = "B3",
  val mathsGrade: String = "B2",
  val subject3Name: String = "Physics",
  val subject3Grade: String = "B3",
  val subject4Name: String = "Chemistry",
  val subject4Grade: String = "C4",
  val subject5Name: String = "Biology",
  val subject5Grade: String = "B2",
  val jambRegNo: String = "",
  val previousQualifications: String = "Senior Secondary School Certificate (SSCE)",
  val programme: String = "Computer Science",
  val level: String = "ND", // ND or HND

  // Step 3: Next of Kin
  val nextOfKinName: String = "",
  val nextOfKinRelationship: String = "Father",
  val nextOfKinPhone: String = "",
  val nextOfKinAddress: String = "",

  // Step 4: Documents Upload Status
  val passportUploaded: Boolean = true,
  val resultSlipUploaded: Boolean = true,
  val birthCertUploaded: Boolean = true,
  val lgaIdUploaded: Boolean = true,
  val prevCertUploaded: Boolean = false,

  val currentStep: Int = 1,
  val isSubmitting: Boolean = false,
  val submissionSuccessAppNo: String? = null,
  val errorMessage: String? = null
)

data class PaymentCheckoutState(
  val isOpen: Boolean = false,
  val applicationNumber: String = "",
  val studentName: String = "",
  val paymentTitle: String = "Admission Application Fee",
  val amount: Double = 10000.0,
  val selectedGateway: String = "Paystack", // Paystack, Flutterwave
  val channel: String = "Debit Card", // Debit Card, Bank Transfer, USSD
  val isProcessing: Boolean = false
)

class PolytechnicViewModel(application: Application) : AndroidViewModel(application) {

  private val database = AppDatabase.getDatabase(application, viewModelScope)
  val repository = PolytechnicRepository(
    userDao = database.userDao(),
    applicationDao = database.applicationDao(),
    paymentDao = database.paymentDao(),
    announcementDao = database.announcementDao()
  )

  // Current navigation screen: "home", "programmes", "apply", "track", "student_portal", "admin_dashboard", "about"
  private val _currentScreen = MutableStateFlow("home")
  val currentScreen: StateFlow<String> = _currentScreen.asStateFlow()

  // User session
  private val _currentUser = MutableStateFlow<User?>(null)
  val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

  // Student linked application
  private val _studentApplication = MutableStateFlow<AdmissionApplication?>(null)
  val studentApplication: StateFlow<AdmissionApplication?> = _studentApplication.asStateFlow()

  // Tracking screen state
  private val _trackingSearchQuery = MutableStateFlow("")
  val trackingSearchQuery: StateFlow<String> = _trackingSearchQuery.asStateFlow()

  private val _trackingResult = MutableStateFlow<AdmissionApplication?>(null)
  val trackingResult: StateFlow<AdmissionApplication?> = _trackingResult.asStateFlow()

  private val _trackingNotFound = MutableStateFlow(false)
  val trackingNotFound: StateFlow<Boolean> = _trackingNotFound.asStateFlow()

  // Form State
  private val _formState = MutableStateFlow(ApplicationFormState())
  val formState: StateFlow<ApplicationFormState> = _formState.asStateFlow()

  // Payment checkout state
  private val _paymentState = MutableStateFlow(PaymentCheckoutState())
  val paymentState: StateFlow<PaymentCheckoutState> = _paymentState.asStateFlow()

  // Generated recent receipt (for receipt dialog)
  private val _activeReceipt = MutableStateFlow<SchoolPayment?>(null)
  val activeReceipt: StateFlow<SchoolPayment?> = _activeReceipt.asStateFlow()

  // Admission letter dialog state
  private val _viewingAdmissionLetterFor = MutableStateFlow<AdmissionApplication?>(null)
  val viewingAdmissionLetterFor: StateFlow<AdmissionApplication?> = _viewingAdmissionLetterFor.asStateFlow()

  // Registration slip dialog state
  private val _viewingRegistrationSlipFor = MutableStateFlow<AdmissionApplication?>(null)
  val viewingRegistrationSlipFor: StateFlow<AdmissionApplication?> = _viewingRegistrationSlipFor.asStateFlow()

  // Admin Selected Application for review
  private val _adminSelectedApp = MutableStateFlow<AdmissionApplication?>(null)
  val adminSelectedApp: StateFlow<AdmissionApplication?> = _adminSelectedApp.asStateFlow()

  private val _adminSearchQuery = MutableStateFlow("")
  val adminSearchQuery: StateFlow<String> = _adminSearchQuery.asStateFlow()

  private val _adminMetrics = MutableStateFlow(AdminMetrics(0, 0, 0, 0.0))
  val adminMetrics: StateFlow<AdminMetrics> = _adminMetrics.asStateFlow()

  // SnackBar / Alert message
  private val _toastMessage = MutableStateFlow<String?>(null)
  val toastMessage: StateFlow<String?> = _toastMessage.asStateFlow()

  // Flows from DB
  val allApplications: StateFlow<List<AdmissionApplication>> = repository.allApplications
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  val allAnnouncements: StateFlow<List<SchoolAnnouncement>> = repository.allAnnouncements
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  val allPayments: StateFlow<List<SchoolPayment>> = repository.allPayments
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  val programmes: List<AcademicProgramme> = repository.getProgrammes()

  init {
    refreshAdminMetrics()
  }

  fun navigateTo(screen: String) {
    _currentScreen.value = screen
    if (screen == "admin_dashboard") {
      refreshAdminMetrics()
    }
  }

  fun showToast(msg: String) {
    _toastMessage.value = msg
  }

  fun clearToast() {
    _toastMessage.value = null
  }

  fun refreshAdminMetrics() {
    viewModelScope.launch {
      _adminMetrics.value = repository.getAdminMetrics()
    }
  }

  // Application Form management
  fun updateForm(update: (ApplicationFormState) -> ApplicationFormState) {
    _formState.value = update(_formState.value)
  }

  fun nextStep() {
    val current = _formState.value
    if (current.currentStep < 5) {
      _formState.value = current.copy(currentStep = current.currentStep + 1, errorMessage = null)
    }
  }

  fun prevStep() {
    val current = _formState.value
    if (current.currentStep > 1) {
      _formState.value = current.copy(currentStep = current.currentStep - 1, errorMessage = null)
    }
  }

  fun setProgrammeForApplication(progName: String, level: String = "ND") {
    _formState.value = _formState.value.copy(programme = progName, level = level)
    navigateTo("apply")
  }

  fun submitApplication() {
    val form = _formState.value
    if (form.fullName.isBlank() || form.email.isBlank() || form.phone.isBlank()) {
      _formState.value = form.copy(errorMessage = "Please fill in all required personal information.")
      return
    }

    viewModelScope.launch {
      _formState.value = form.copy(isSubmitting = true, errorMessage = null)
      try {
        val subjectGrades = "English: ${form.englishGrade}, Maths: ${form.mathsGrade}, ${form.subject3Name}: ${form.subject3Grade}, ${form.subject4Name}: ${form.subject4Grade}, ${form.subject5Name}: ${form.subject5Grade}"

        val app = AdmissionApplication(
          applicationNumber = "",
          fullName = form.fullName.trim(),
          dateOfBirth = form.dateOfBirth,
          gender = form.gender,
          nationality = form.nationality,
          stateOfOrigin = form.stateOfOrigin,
          lga = form.lga,
          residentialAddress = form.residentialAddress,
          phone = form.phone,
          whatsappNumber = if (form.whatsappNumber.isNotBlank()) form.whatsappNumber else form.phone,
          email = form.email.trim().lowercase(),
          previousSchool = form.previousSchool,
          examType = form.examType,
          examNumber = form.examNumber,
          examYear = form.examYear,
          subjectGrades = subjectGrades,
          jambRegNo = form.jambRegNo,
          previousQualifications = form.previousQualifications,
          programme = form.programme,
          level = form.level,
          nextOfKinName = form.nextOfKinName,
          nextOfKinRelationship = form.nextOfKinRelationship,
          nextOfKinPhone = form.nextOfKinPhone,
          nextOfKinAddress = form.nextOfKinAddress,
          status = "Application Submitted",
          adminNotes = "Application submitted successfully. Awaiting academic verification by Admissions Committee."
        )

        val generatedAppNo = repository.submitAdmissionApplication(app)
        _formState.value = form.copy(
          isSubmitting = false,
          submissionSuccessAppNo = generatedAppNo,
          currentStep = 5
        )
        refreshAdminMetrics()
        showToast("Application $generatedAppNo submitted successfully!")
      } catch (e: Exception) {
        _formState.value = form.copy(
          isSubmitting = false,
          errorMessage = "Submission failed: ${e.localizedMessage}"
        )
      }
    }
  }

  fun resetApplicationForm() {
    _formState.value = ApplicationFormState()
  }

  // Tracking
  fun updateTrackingQuery(query: String) {
    _trackingSearchQuery.value = query
    _trackingNotFound.value = false
  }

  fun trackApplication() {
    val query = _trackingSearchQuery.value.trim()
    if (query.isBlank()) return

    viewModelScope.launch {
      val found = if (query.contains("@")) {
        repository.getApplicationByEmail(query.lowercase())
      } else {
        repository.getApplicationByNumber(query.uppercase())
      }

      if (found != null) {
        _trackingResult.value = found
        _trackingNotFound.value = false
      } else {
        _trackingResult.value = null
        _trackingNotFound.value = true
      }
    }
  }

  fun setTrackTarget(app: AdmissionApplication) {
    _trackingResult.value = app
    _trackingSearchQuery.value = app.applicationNumber
    _trackingNotFound.value = false
    navigateTo("track")
  }

  // Student Auth
  fun loginStudent(email: String, pass: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
    viewModelScope.launch {
      val result = repository.login(email, pass)
      result.onSuccess { user ->
        _currentUser.value = user
        loadStudentApplication(user)
        onSuccess()
      }.onFailure { err ->
        onError(err.message ?: "Authentication failed")
      }
    }
  }

  fun registerStudent(
    name: String,
    email: String,
    pass: String,
    phone: String,
    onSuccess: () -> Unit,
    onError: (String) -> Unit
  ) {
    viewModelScope.launch {
      val result = repository.registerStudent(name, email, pass, phone)
      result.onSuccess { user ->
        _currentUser.value = user
        loadStudentApplication(user)
        onSuccess()
      }.onFailure { err ->
        onError(err.message ?: "Registration failed")
      }
    }
  }

  fun resetPassword(email: String, newPass: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
    viewModelScope.launch {
      val result = repository.resetPassword(email, newPass)
      result.onSuccess {
        onSuccess()
      }.onFailure {
        onError(it.message ?: "Password reset failed")
      }
    }
  }

  fun logout() {
    _currentUser.value = null
    _studentApplication.value = null
    _adminSelectedApp.value = null
  }

  private fun loadStudentApplication(user: User) {
    viewModelScope.launch {
      val app = if (user.applicationNumber.isNotBlank()) {
        repository.getApplicationByNumber(user.applicationNumber)
      } else {
        repository.getApplicationByEmail(user.email)
      }
      _studentApplication.value = app
    }
  }

  // Payments
  fun openPayment(
    applicationNumber: String,
    studentName: String,
    paymentTitle: String,
    amount: Double
  ) {
    _paymentState.value = PaymentCheckoutState(
      isOpen = true,
      applicationNumber = applicationNumber,
      studentName = studentName,
      paymentTitle = paymentTitle,
      amount = amount,
      selectedGateway = "Paystack",
      channel = "Debit Card"
    )
  }

  fun updatePaymentGateway(gateway: String) {
    _paymentState.value = _paymentState.value.copy(selectedGateway = gateway)
  }

  fun updatePaymentChannel(channel: String) {
    _paymentState.value = _paymentState.value.copy(channel = channel)
  }

  fun dismissPaymentDialog() {
    _paymentState.value = _paymentState.value.copy(isOpen = false)
  }

  fun executeSimulatedPayment() {
    val state = _paymentState.value
    viewModelScope.launch {
      _paymentState.value = state.copy(isProcessing = true)
      // Simulate gateway authorization
      kotlinx.coroutines.delay(1200)
      val payment = repository.processPayment(
        applicationNumber = state.applicationNumber,
        studentName = state.studentName,
        paymentTitle = state.paymentTitle,
        amount = state.amount,
        gateway = state.selectedGateway,
        channel = state.channel
      )
      _paymentState.value = state.copy(isOpen = false, isProcessing = false)
      _activeReceipt.value = payment
      refreshAdminMetrics()
      // Reload current application if active
      _currentUser.value?.let { loadStudentApplication(it) }
      _trackingResult.value?.let {
        _trackingResult.value = repository.getApplicationByNumber(it.applicationNumber)
      }
      showToast("Payment of ₦${String.format(java.util.Locale.US, "%,.2f", state.amount)} successful!")
    }
  }

  fun dismissReceiptDialog() {
    _activeReceipt.value = null
  }

  fun viewAdmissionLetter(app: AdmissionApplication) {
    _viewingAdmissionLetterFor.value = app
  }

  fun dismissAdmissionLetter() {
    _viewingAdmissionLetterFor.value = null
  }

  fun viewRegistrationSlip(app: AdmissionApplication) {
    _viewingRegistrationSlipFor.value = app
  }

  fun dismissRegistrationSlip() {
    _viewingRegistrationSlipFor.value = null
  }

  // Admin Functions
  fun setAdminSelectedApp(app: AdmissionApplication?) {
    _adminSelectedApp.value = app
  }

  fun updateAdminSearchQuery(query: String) {
    _adminSearchQuery.value = query
  }

  fun updateAppStatusByAdmin(
    appId: Long,
    newStatus: String,
    notes: String,
    matricNumber: String
  ) {
    viewModelScope.launch {
      repository.updateApplicationStatus(appId, newStatus, notes, matricNumber)
      refreshAdminMetrics()
      _adminSelectedApp.value = _adminSelectedApp.value?.copy(
        status = newStatus,
        adminNotes = notes,
        matricNumber = matricNumber
      )
      showToast("Application status updated to '$newStatus'")
    }
  }

  fun publishNewAnnouncement(title: String, category: String, content: String, isImportant: Boolean) {
    viewModelScope.launch {
      repository.publishAnnouncement(title, category, content, isImportant)
      showToast("Announcement published successfully")
    }
  }
}

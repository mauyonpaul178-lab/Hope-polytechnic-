package com.example.data.repository

import com.example.data.local.AnnouncementDao
import com.example.data.local.ApplicationDao
import com.example.data.local.PaymentDao
import com.example.data.local.UserDao
import com.example.data.model.AcademicProgramme
import com.example.data.model.AdmissionApplication
import com.example.data.model.ProgrammeData
import com.example.data.model.SchoolAnnouncement
import com.example.data.model.SchoolPayment
import com.example.data.model.User
import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.random.Random

class PolytechnicRepository(
  private val userDao: UserDao,
  private val applicationDao: ApplicationDao,
  private val paymentDao: PaymentDao,
  private val announcementDao: AnnouncementDao
) {

  val allApplications: Flow<List<AdmissionApplication>> = applicationDao.getAllApplications()
  val allAnnouncements: Flow<List<SchoolAnnouncement>> = announcementDao.getAllAnnouncements()
  val allPayments: Flow<List<SchoolPayment>> = paymentDao.getAllPayments()

  fun getProgrammes(): List<AcademicProgramme> = ProgrammeData.defaultProgrammes

  fun searchApplications(query: String): Flow<List<AdmissionApplication>> =
    applicationDao.searchApplications(query)

  fun observeApplicationByNumber(appNo: String): Flow<AdmissionApplication?> =
    applicationDao.observeByApplicationNumber(appNo)

  fun observeApplicationByEmail(email: String): Flow<AdmissionApplication?> =
    applicationDao.observeByEmail(email)

  suspend fun getApplicationByNumber(appNo: String): AdmissionApplication? =
    applicationDao.getByApplicationNumber(appNo)

  suspend fun getApplicationByEmail(email: String): AdmissionApplication? =
    applicationDao.getByEmail(email)

  fun getPaymentsByApplication(appNo: String): Flow<List<SchoolPayment>> =
    paymentDao.getPaymentsByApplication(appNo)

  suspend fun login(email: String, password: String):Result<User> {
    val user = userDao.getUserByEmail(email.trim().lowercase())
    return if (user != null && user.passwordHash == password) {
      Result.success(user)
    } else {
      Result.failure(Exception("Invalid email address or password. Please verify credentials."))
    }
  }

  suspend fun registerStudent(
    fullName: String,
    email: String,
    password: String,
    phone: String
  ): Result<User> {
    val cleanEmail = email.trim().lowercase()
    val existing = userDao.getUserByEmail(cleanEmail)
    if (existing != null) {
      return Result.failure(Exception("An account with this email address already exists."))
    }

    val user = User(
      fullName = fullName.trim(),
      email = cleanEmail,
      passwordHash = password,
      phone = phone.trim(),
      role = "STUDENT"
    )
    val id = userDao.insertUser(user)
    return Result.success(user.copy(id = id))
  }

  suspend fun resetPassword(email: String, newPassword: String): Result<Boolean> {
    val cleanEmail = email.trim().lowercase()
    val user = userDao.getUserByEmail(cleanEmail)
    if (user == null) {
      return Result.failure(Exception("No account found matching this email address."))
    }
    userDao.updatePassword(cleanEmail, newPassword)
    return Result.success(true)
  }

  suspend fun submitAdmissionApplication(app: AdmissionApplication): String {
    // Generate unique application number HP/2026/00XXX
    val count = applicationDao.getCount()
    val randomSuffix = Random.nextInt(100, 999)
    val appNo = "HP/2026/${String.format(Locale.US, "%03d", count + 145)}$randomSuffix".take(13)

    val finalizedApp = app.copy(
      applicationNumber = appNo,
      submittedAt = System.currentTimeMillis(),
      updatedAt = System.currentTimeMillis()
    )
    applicationDao.insertApplication(finalizedApp)

    // Update user link if user exists
    val user = userDao.getUserByEmail(app.email.trim().lowercase())
    if (user != null) {
      userDao.updateUser(user.copy(applicationNumber = appNo))
    }

    return appNo
  }

  suspend fun updateApplicationStatus(
    id: Long,
    status: String,
    adminNotes: String,
    matricNumber: String
  ) {
    applicationDao.updateStatus(id, status, adminNotes, matricNumber)
  }

  suspend fun updateApplicationDetails(application: AdmissionApplication) {
    applicationDao.updateApplication(application.copy(updatedAt = System.currentTimeMillis()))
  }

  suspend fun processPayment(
    applicationNumber: String,
    studentName: String,
    paymentTitle: String,
    amount: Double,
    gateway: String,
    channel: String
  ): SchoolPayment {
    val randomId = Random.nextInt(1000, 9999)
    val receiptNo = "REC-HP-2026-$randomId"
    val reference = "${gateway.uppercase().take(4)}_HP_${System.currentTimeMillis().toString().takeLast(8)}$randomId"

    val payment = SchoolPayment(
      receiptNumber = receiptNo,
      applicationNumber = applicationNumber,
      studentName = studentName,
      paymentTitle = paymentTitle,
      amount = amount,
      gateway = gateway,
      channel = channel,
      status = "Paid",
      transactionReference = reference,
      timestamp = System.currentTimeMillis()
    )

    paymentDao.insertPayment(payment)

    if (paymentTitle.contains("Application", ignoreCase = true) ||
        paymentTitle.contains("Acceptance", ignoreCase = true)) {
      applicationDao.markFeePaid(applicationNumber)
    }

    return payment
  }

  suspend fun publishAnnouncement(title: String, category: String, content: String, isImportant: Boolean) {
    val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.US)
    val dateStr = dateFormat.format(Date())
    announcementDao.insertAnnouncement(
      SchoolAnnouncement(
        title = title.trim(),
        category = category,
        content = content.trim(),
        date = dateStr,
        isImportant = isImportant
      )
    )
  }

  suspend fun getAdminMetrics(): AdminMetrics {
    val total = applicationDao.getCount()
    val approved = applicationDao.getApprovedCount()
    val pending = applicationDao.getPendingCount()
    val revenue = paymentDao.getTotalRevenue() ?: 0.0
    return AdminMetrics(
      totalApplications = total,
      approvedApplications = approved,
      pendingApplications = pending,
      totalRevenue = revenue
    )
  }

  fun exportApplicationsToCsv(applications: List<AdmissionApplication>): String {
    val header = "Application No,Full Name,Programme,Level,State,LGA,Phone,Email,Exam Type,Exam No,Status,Matric No,Fee Paid\n"
    val rows = applications.joinToString("\n") { app ->
      "\"${app.applicationNumber}\",\"${app.fullName}\",\"${app.programme}\",\"${app.level}\",\"${app.stateOfOrigin}\",\"${app.lga}\",\"${app.phone}\",\"${app.email}\",\"${app.examType}\",\"${app.examNumber}\",\"${app.status}\",\"${app.matricNumber}\",\"${if (app.isFeePaid) "YES" else "NO"}\""
    }
    return header + rows
  }
}

data class AdminMetrics(
  val totalApplications: Int,
  val approvedApplications: Int,
  val pendingApplications: Int,
  val totalRevenue: Double
)

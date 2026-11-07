package com.example

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.components.AdmissionLetterDialog
import com.example.ui.components.AppBottomNav
import com.example.ui.components.AppTopBar
import com.example.ui.components.PaymentGatewayDialog
import com.example.ui.components.PaymentReceiptDialog
import com.example.ui.components.RegistrationSlipDialog
import com.example.ui.screens.AdminDashboardScreen
import com.example.ui.screens.AdmissionRegistrationScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.ProgrammesScreen
import com.example.ui.screens.SchoolInfoScreen
import com.example.ui.screens.StudentPortalScreen
import com.example.ui.screens.TrackApplicationScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.viewmodel.PolytechnicViewModel

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      MyApplicationTheme {
        MainAppContent()
      }
    }
  }
}

@Composable
fun MainAppContent(viewModel: PolytechnicViewModel = viewModel()) {
  val currentScreen by viewModel.currentScreen.collectAsState()
  val toastMessage by viewModel.toastMessage.collectAsState()
  val activeReceipt by viewModel.activeReceipt.collectAsState()
  val admissionLetterApp by viewModel.viewingAdmissionLetterFor.collectAsState()
  val registrationSlipApp by viewModel.viewingRegistrationSlipFor.collectAsState()
  val paymentState by viewModel.paymentState.collectAsState()

  val snackbarHostState = remember { SnackbarHostState() }

  // Handle hardware / gesture back navigation
  BackHandler(enabled = currentScreen != "home") {
    viewModel.navigateTo("home")
  }

  // Toast listener
  LaunchedEffect(toastMessage) {
    toastMessage?.let { msg ->
      snackbarHostState.showSnackbar(msg)
      viewModel.clearToast()
    }
  }

  Scaffold(
    topBar = {
      AppTopBar(
        currentScreen = currentScreen,
        onNavigateAdmin = { viewModel.navigateTo("admin_dashboard") },
        onHomeClick = { viewModel.navigateTo("home") }
      )
    },
    bottomBar = {
      AppBottomNav(
        currentScreen = currentScreen,
        onNavigate = { screen -> viewModel.navigateTo(screen) }
      )
    },
    snackbarHost = { SnackbarHost(snackbarHostState) },
    modifier = Modifier.fillMaxSize()
  ) { innerPadding ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
    ) {
      when (currentScreen) {
        "home" -> HomeScreen(
          viewModel = viewModel,
          onNavigate = { screen -> viewModel.navigateTo(screen) }
        )
        "programmes" -> ProgrammesScreen(
          viewModel = viewModel,
          onApplyForProgramme = { progName, level ->
            viewModel.setProgrammeForApplication(progName, level)
          }
        )
        "apply" -> AdmissionRegistrationScreen(
          viewModel = viewModel,
          onNavigate = { screen -> viewModel.navigateTo(screen) }
        )
        "track" -> TrackApplicationScreen(
          viewModel = viewModel,
          onNavigate = { screen -> viewModel.navigateTo(screen) }
        )
        "student_portal" -> StudentPortalScreen(
          viewModel = viewModel,
          onNavigate = { screen -> viewModel.navigateTo(screen) }
        )
        "admin_dashboard" -> AdminDashboardScreen(
          viewModel = viewModel,
          onNavigate = { screen -> viewModel.navigateTo(screen) }
        )
        "about" -> SchoolInfoScreen()
        else -> HomeScreen(
          viewModel = viewModel,
          onNavigate = { screen -> viewModel.navigateTo(screen) }
        )
      }
    }
  }

  // Modal Dialogs
  admissionLetterApp?.let { app ->
    AdmissionLetterDialog(
      application = app,
      onDismiss = { viewModel.dismissAdmissionLetter() }
    )
  }

  registrationSlipApp?.let { app ->
    RegistrationSlipDialog(
      application = app,
      onDismiss = { viewModel.dismissRegistrationSlip() }
    )
  }

  activeReceipt?.let { receipt ->
    PaymentReceiptDialog(
      payment = receipt,
      onDismiss = { viewModel.dismissReceiptDialog() }
    )
  }

  if (paymentState.isOpen) {
    PaymentGatewayDialog(
      state = paymentState,
      onGatewaySelected = { gw -> viewModel.updatePaymentGateway(gw) },
      onChannelSelected = { ch -> viewModel.updatePaymentChannel(ch) },
      onPayClicked = { viewModel.executeSimulatedPayment() },
      onDismiss = { viewModel.dismissPaymentDialog() }
    )
  }
}

package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Print
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.UploadFile
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AdmissionApplication
import com.example.data.model.User
import com.example.ui.theme.CrimsonRed
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.NavyDark
import com.example.ui.theme.NavyLight
import com.example.ui.viewmodel.PolytechnicViewModel

@Composable
fun StudentPortalScreen(
  viewModel: PolytechnicViewModel,
  onNavigate: (String) -> Unit
) {
  val currentUser by viewModel.currentUser.collectAsState()
  val studentApp by viewModel.studentApplication.collectAsState()

  if (currentUser != null && currentUser?.role == "STUDENT") {
    // Logged in Student Dashboard
    StudentDashboard(
      user = currentUser!!,
      app = studentApp,
      viewModel = viewModel,
      onNavigate = onNavigate
    )
  } else {
    // Student Login / Register Screen
    StudentAuthView(viewModel = viewModel)
  }
}

@Composable
private fun StudentAuthView(viewModel: PolytechnicViewModel) {
  var selectedTab by remember { mutableStateOf(0) } // 0: Login, 1: Register

  var loginEmail by remember { mutableStateOf("emmanuel.bassey@gmail.com") }
  var loginPass by remember { mutableStateOf("student123") }

  var regName by remember { mutableStateOf("") }
  var regEmail by remember { mutableStateOf("") }
  var regPhone by remember { mutableStateOf("") }
  var regPass by remember { mutableStateOf("") }

  var showForgotDialog by remember { mutableStateOf(false) }
  var forgotEmail by remember { mutableStateOf("") }
  var newPassword by remember { mutableStateOf("") }

  var errorMessage by remember { mutableStateOf<String?>(null) }

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .background(Color(0xFFF6F8FB))
      .padding(16.dp)
      .testTag("student_auth_view")
  ) {
    // Header
    item {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Box(
          modifier = Modifier
            .size(56.dp)
            .background(NavyDark, CircleShape),
          contentAlignment = Alignment.Center
        ) {
          Icon(imageVector = Icons.Default.School, contentDescription = null, tint = GoldAccent, modifier = Modifier.size(30.dp))
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
          text = "Student Admission Portal",
          fontWeight = FontWeight.Black,
          fontSize = 19.sp,
          color = NavyDark
        )
        Text(
          text = "Hope Polytechnic, Mbak Itam III, Uyo",
          fontSize = 11.sp,
          color = Color(0xFF64748B)
        )
      }
    }

    // Tabs: Login / Register
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          TabRow(
            selectedTabIndex = selectedTab,
            containerColor = Color(0xFFF1F5F9),
            contentColor = NavyDark,
            modifier = Modifier.clip(RoundedCornerShape(8.dp))
          ) {
            Tab(
              selected = selectedTab == 0,
              onClick = { selectedTab = 0; errorMessage = null },
              text = { Text("Log In", fontWeight = FontWeight.Bold) }
            )
            Tab(
              selected = selectedTab == 1,
              onClick = { selectedTab = 1; errorMessage = null },
              text = { Text("Create Account", fontWeight = FontWeight.Bold) }
            )
          }

          Spacer(modifier = Modifier.height(16.dp))

          errorMessage?.let { err ->
            Box(
              modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
                .background(Color(0xFFFEE2E2), RoundedCornerShape(6.dp))
                .padding(8.dp)
            ) {
              Text(err, color = Color(0xFF991B1B), fontSize = 11.sp)
            }
          }

          if (selectedTab == 0) {
            // LOGIN FORM
            OutlinedTextField(
              value = loginEmail,
              onValueChange = { loginEmail = it },
              label = { Text("Email Address") },
              leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
              modifier = Modifier
                .fillMaxWidth()
                .testTag("student_login_email")
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
              value = loginPass,
              onValueChange = { loginPass = it },
              label = { Text("Password") },
              leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
              visualTransformation = PasswordVisualTransformation(),
              modifier = Modifier
                .fillMaxWidth()
                .testTag("student_login_password")
            )

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.End
            ) {
              TextButton(onClick = { showForgotDialog = true }) {
                Text("Forgot Password?", fontSize = 11.sp, color = CrimsonRed)
              }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Button(
              onClick = {
                viewModel.loginStudent(
                  loginEmail,
                  loginPass,
                  onSuccess = { errorMessage = null },
                  onError = { errorMessage = it }
                )
              },
              colors = ButtonDefaults.buttonColors(containerColor = NavyDark),
              shape = RoundedCornerShape(8.dp),
              modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .testTag("student_login_btn")
            ) {
              Text("Log In to Portal", fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(10.dp))

            Box(
              modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF8FAFC), RoundedCornerShape(6.dp))
                .padding(8.dp)
            ) {
              Text(
                "Demo Student Login:\nEmail: emmanuel.bassey@gmail.com\nPassword: student123",
                fontSize = 10.sp,
                color = Color(0xFF475569)
              )
            }
          } else {
            // REGISTER FORM
            OutlinedTextField(
              value = regName,
              onValueChange = { regName = it },
              label = { Text("Full Name") },
              leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
              modifier = Modifier
                .fillMaxWidth()
                .testTag("student_reg_name")
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
              value = regEmail,
              onValueChange = { regEmail = it },
              label = { Text("Email Address") },
              leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
              modifier = Modifier
                .fillMaxWidth()
                .testTag("student_reg_email")
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
              value = regPhone,
              onValueChange = { regPhone = it },
              label = { Text("Phone Number") },
              leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null) },
              modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
              value = regPass,
              onValueChange = { regPass = it },
              label = { Text("Password") },
              leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
              visualTransformation = PasswordVisualTransformation(),
              modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(14.dp))

            Button(
              onClick = {
                if (regName.isBlank() || regEmail.isBlank() || regPass.isBlank()) {
                  errorMessage = "Please complete all registration fields."
                  return@Button
                }
                viewModel.registerStudent(
                  regName,
                  regEmail,
                  regPass,
                  regPhone,
                  onSuccess = { errorMessage = null },
                  onError = { errorMessage = it }
                )
              },
              colors = ButtonDefaults.buttonColors(containerColor = CrimsonRed),
              shape = RoundedCornerShape(8.dp),
              modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .testTag("student_register_btn")
            ) {
              Text("Create Student Account", fontWeight = FontWeight.Bold)
            }
          }
        }
      }
    }

    item {
      Spacer(modifier = Modifier.height(60.dp))
    }
  }

  // Password Reset Dialog
  if (showForgotDialog) {
    AlertDialog(
      onDismissRequest = { showForgotDialog = false },
      title = { Text("Reset Forgotten Password", fontWeight = FontWeight.Bold, fontSize = 15.sp) },
      text = {
        Column {
          Text("Enter your registered email address to set a new password.", fontSize = 12.sp, color = Color(0xFF64748B))
          Spacer(modifier = Modifier.height(8.dp))
          OutlinedTextField(
            value = forgotEmail,
            onValueChange = { forgotEmail = it },
            label = { Text("Account Email") },
            modifier = Modifier.fillMaxWidth()
          )
          Spacer(modifier = Modifier.height(8.dp))
          OutlinedTextField(
            value = newPassword,
            onValueChange = { newPassword = it },
            label = { Text("New Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
          )
        }
      },
      confirmButton = {
        Button(
          onClick = {
            if (forgotEmail.isNotBlank() && newPassword.isNotBlank()) {
              viewModel.resetPassword(
                forgotEmail,
                newPassword,
                onSuccess = {
                  showForgotDialog = false
                  viewModel.showToast("Password reset successfully. You can now log in.")
                },
                onError = {
                  viewModel.showToast(it)
                }
              )
            }
          },
          colors = ButtonDefaults.buttonColors(containerColor = NavyDark)
        ) {
          Text("Reset")
        }
      },
      dismissButton = {
        TextButton(onClick = { showForgotDialog = false }) {
          Text("Cancel")
        }
      }
    )
  }
}

@Composable
private fun StudentDashboard(
  user: User,
  app: AdmissionApplication?,
  viewModel: PolytechnicViewModel,
  onNavigate: (String) -> Unit
) {
  val announcements by viewModel.allAnnouncements.collectAsState()
  var dashboardTab by remember { mutableStateOf(0) } // 0: Application Dossier, 1: Payments & Fees, 2: Announcements

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .background(Color(0xFFF6F8FB))
      .padding(16.dp)
      .testTag("student_dashboard")
  ) {
    // Profile Header Card
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = NavyDark),
        shape = RoundedCornerShape(12.dp)
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Box(
            modifier = Modifier
              .size(54.dp)
              .clip(CircleShape)
              .background(GoldAccent),
            contentAlignment = Alignment.Center
          ) {
            Icon(imageVector = Icons.Default.Person, contentDescription = null, tint = NavyDark, modifier = Modifier.size(32.dp))
          }

          Spacer(modifier = Modifier.width(12.dp))

          Column(modifier = Modifier.weight(1f)) {
            Text(user.fullName, fontWeight = FontWeight.Black, fontSize = 16.sp, color = Color.White)
            Text(user.email, fontSize = 11.sp, color = Color.White.copy(alpha = 0.8f))
            if (app != null) {
              Text("App No: ${app.applicationNumber}", fontSize = 11.sp, color = GoldAccent, fontWeight = FontWeight.Bold)
            }
          }

          IconButton(
            onClick = { viewModel.logout() },
            modifier = Modifier.testTag("student_logout_btn")
          ) {
            Icon(imageVector = Icons.Default.ExitToApp, contentDescription = "Log Out", tint = Color.White)
          }
        }
      }
    }

    // Dashboard Tabs
    item {
      Spacer(modifier = Modifier.height(12.dp))
      TabRow(
        selectedTabIndex = dashboardTab,
        containerColor = Color.White,
        contentColor = NavyDark,
        modifier = Modifier.clip(RoundedCornerShape(8.dp))
      ) {
        Tab(
          selected = dashboardTab == 0,
          onClick = { dashboardTab = 0 },
          text = { Text("Application", fontSize = 11.sp, fontWeight = FontWeight.Bold) }
        )
        Tab(
          selected = dashboardTab == 1,
          onClick = { dashboardTab = 1 },
          text = { Text("Fees & Bursary", fontSize = 11.sp, fontWeight = FontWeight.Bold) }
        )
        Tab(
          selected = dashboardTab == 2,
          onClick = { dashboardTab = 2 },
          text = { Text("Notices", fontSize = 11.sp, fontWeight = FontWeight.Bold) }
        )
      }
      Spacer(modifier = Modifier.height(12.dp))
    }

    when (dashboardTab) {
      0 -> {
        // Tab 0: Application Status & Documents
        item {
          if (app != null) {
            Card(
              modifier = Modifier.fillMaxWidth(),
              colors = CardDefaults.cardColors(containerColor = Color.White),
              shape = RoundedCornerShape(12.dp)
            ) {
              Column(modifier = Modifier.padding(16.dp)) {
                Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.SpaceBetween,
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Text("Admission Status", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = NavyDark)
                  Box(
                    modifier = Modifier
                      .background(
                        when (app.status) {
                          "Approved" -> Color(0xFFDCFCE7)
                          "Under Review" -> Color(0xFFFEF3C7)
                          else -> Color(0xFFDBEAFE)
                        },
                        RoundedCornerShape(4.dp)
                      )
                      .padding(horizontal = 8.dp, vertical = 3.dp)
                  ) {
                    Text(
                      text = app.status.uppercase(),
                      fontWeight = FontWeight.Bold,
                      fontSize = 10.sp,
                      color = when (app.status) {
                        "Approved" -> Color(0xFF16A34A)
                        "Under Review" -> Color(0xFFD97706)
                        else -> Color(0xFF2563EB)
                      }
                    )
                  }
                }

                Divider(modifier = Modifier.padding(vertical = 8.dp), color = Color(0xFFE2E8F0))

                Text("Admissions Desk Remarks:", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF64748B))
                Text(app.adminNotes, fontSize = 12.sp, color = NavyDark, modifier = Modifier.padding(top = 2.dp, bottom = 12.dp))

                // Action buttons
                if (app.status == "Approved") {
                  Button(
                    onClick = { viewModel.viewAdmissionLetter(app) },
                    colors = ButtonDefaults.buttonColors(containerColor = NavyDark),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                      .fillMaxWidth()
                      .height(44.dp)
                      .testTag("portal_view_admission_letter")
                  ) {
                    Icon(imageVector = Icons.Default.Verified, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Official Admission Letter", fontWeight = FontWeight.Bold)
                  }

                  Spacer(modifier = Modifier.height(8.dp))
                }

                OutlinedButton(
                  onClick = { viewModel.viewRegistrationSlip(app) },
                  shape = RoundedCornerShape(8.dp),
                  modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp)
                    .testTag("portal_view_reg_slip")
                ) {
                  Icon(imageVector = Icons.Default.Print, contentDescription = null, modifier = Modifier.size(16.dp))
                  Spacer(modifier = Modifier.width(6.dp))
                  Text("Print Registration Slip")
                }
              }
            }
          } else {
            Card(
              modifier = Modifier.fillMaxWidth(),
              colors = CardDefaults.cardColors(containerColor = Color.White),
              shape = RoundedCornerShape(12.dp)
            ) {
              Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
              ) {
                Text("No Admission Application Linked", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = NavyDark)
                Text(
                  "You have not submitted an admission application for the 2026/2027 session yet.",
                  fontSize = 11.5.sp,
                  color = Color(0xFF64748B),
                  modifier = Modifier.padding(vertical = 8.dp)
                )
                Button(
                  onClick = { onNavigate("apply") },
                  colors = ButtonDefaults.buttonColors(containerColor = CrimsonRed),
                  shape = RoundedCornerShape(8.dp)
                ) {
                  Text("Fill Admission Application Now")
                }
              }
            }
          }
        }
      }

      1 -> {
        // Tab 1: Fees & Bursary
        item {
          Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(12.dp)
          ) {
            Column(modifier = Modifier.padding(16.dp)) {
              Text("Approved Institutional Fees", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = NavyDark)
              Text("Official 2026/2027 schedule of fees payable via Paystack/Flutterwave.", fontSize = 11.sp, color = Color(0xFF64748B))

              Divider(modifier = Modifier.padding(vertical = 10.dp), color = Color(0xFFE2E8F0))

              val fees = listOf(
                Triple("Admission Application Fee", 10000.0, "Mandatory for screening"),
                Triple("Provisional Acceptance Fee", 25000.0, "Payable upon admission approval"),
                Triple("First Semester Tuition & Dues", 65000.0, "Lectures and course materials"),
                Triple("ICT, Library & Laboratory Levy", 15000.0, "Access to engineering and computing hubs")
              )

              fees.forEach { (title, amt, desc) ->
                Row(
                  modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
                    .background(Color(0xFFF8FAFC), RoundedCornerShape(8.dp))
                    .padding(10.dp),
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Column(modifier = Modifier.weight(1f)) {
                    Text(title, fontWeight = FontWeight.Bold, fontSize = 12.sp, color = NavyDark)
                    Text(desc, fontSize = 10.sp, color = Color(0xFF64748B))
                    Text("₦${String.format(java.util.Locale.US, "%,.2f", amt)}", fontWeight = FontWeight.Black, fontSize = 13.sp, color = Color(0xFF16A34A))
                  }

                  Button(
                    onClick = {
                      viewModel.openPayment(
                        applicationNumber = app?.applicationNumber ?: user.applicationNumber.ifEmpty { "HP/2026/00142" },
                        studentName = user.fullName,
                        paymentTitle = title,
                        amount = amt
                      )
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = NavyDark),
                    shape = RoundedCornerShape(6.dp),
                    modifier = Modifier.testTag("pay_fee_btn_${title.take(5)}")
                  ) {
                    Text("Pay Now", fontSize = 11.sp)
                  }
                }
              }
            }
          }
        }
      }

      2 -> {
        // Tab 2: School Announcements
        items(announcements) { notice ->
          Card(
            modifier = Modifier
              .fillMaxWidth()
              .padding(vertical = 4.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(10.dp)
          ) {
            Column(modifier = Modifier.padding(14.dp)) {
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
              ) {
                Text(notice.category.uppercase(), fontSize = 9.sp, fontWeight = FontWeight.Bold, color = CrimsonRed)
                Text(notice.date, fontSize = 10.sp, color = Color(0xFF94A3B8))
              }
              Text(notice.title, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = NavyDark, modifier = Modifier.padding(top = 4.dp))
              Text(notice.content, fontSize = 11.5.sp, color = Color(0xFF475569), modifier = Modifier.padding(top = 2.dp))
            }
          }
        }
      }
    }

    item {
      Spacer(modifier = Modifier.height(60.dp))
    }
  }
}

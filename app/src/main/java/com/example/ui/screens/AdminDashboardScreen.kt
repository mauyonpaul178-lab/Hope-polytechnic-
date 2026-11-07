package com.example.ui.screens

import android.content.Intent
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.FileDownload
import androidx.compose.material.icons.filled.HourglassTop
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Warning
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AdmissionApplication
import com.example.ui.theme.CrimsonRed
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.NavyDark
import com.example.ui.theme.NavyLight
import com.example.ui.viewmodel.PolytechnicViewModel

@Composable
fun AdminDashboardScreen(
  viewModel: PolytechnicViewModel,
  onNavigate: (String) -> Unit
) {
  val currentUser by viewModel.currentUser.collectAsState()

  if (currentUser != null && currentUser?.role == "ADMIN") {
    AdminMainConsole(viewModel = viewModel)
  } else {
    AdminLoginView(viewModel = viewModel)
  }
}

@Composable
private fun AdminLoginView(viewModel: PolytechnicViewModel) {
  var email by remember { mutableStateOf("admin@hopepolytechnic.edu.ng") }
  var pass by remember { mutableStateOf("admin123") }
  var error by remember { mutableStateOf<String?>(null) }

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .background(Color(0xFFF6F8FB))
      .padding(16.dp)
      .testTag("admin_login_screen")
  ) {
    item {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Box(
          modifier = Modifier
            .size(60.dp)
            .background(CrimsonRed, CircleShape),
          contentAlignment = Alignment.Center
        ) {
          Icon(imageVector = Icons.Default.AdminPanelSettings, contentDescription = null, tint = Color.White, modifier = Modifier.size(36.dp))
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
          text = "Admissions Office Console",
          fontWeight = FontWeight.Black,
          fontSize = 20.sp,
          color = NavyDark
        )
        Text(
          text = "Authorised Administrators & Registry Staff Only",
          fontSize = 11.sp,
          color = Color(0xFF64748B)
        )
      }
    }

    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          error?.let { err ->
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

          OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Staff Email Address") },
            modifier = Modifier
              .fillMaxWidth()
              .testTag("admin_email_input")
          )

          Spacer(modifier = Modifier.height(10.dp))

          OutlinedTextField(
            value = pass,
            onValueChange = { pass = it },
            label = { Text("Administrative Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
              .fillMaxWidth()
              .testTag("admin_password_input")
          )

          Spacer(modifier = Modifier.height(16.dp))

          Button(
            onClick = {
              viewModel.loginStudent(
                email,
                pass,
                onSuccess = { error = null },
                onError = { error = it }
              )
            },
            colors = ButtonDefaults.buttonColors(containerColor = NavyDark),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
              .fillMaxWidth()
              .height(48.dp)
              .testTag("admin_login_btn")
          ) {
            Icon(imageVector = Icons.Default.Lock, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("Access Admin Console", fontWeight = FontWeight.Bold)
          }

          Spacer(modifier = Modifier.height(12.dp))

          Box(
            modifier = Modifier
              .fillMaxWidth()
              .background(Color(0xFFF8FAFC), RoundedCornerShape(6.dp))
              .padding(10.dp)
          ) {
            Text(
              "Default Admin Account:\nEmail: admin@hopepolytechnic.edu.ng\nPassword: admin123",
              fontSize = 10.5.sp,
              color = Color(0xFF475569)
            )
          }
        }
      }
    }

    item {
      Spacer(modifier = Modifier.height(60.dp))
    }
  }
}

@Composable
private fun AdminMainConsole(viewModel: PolytechnicViewModel) {
  val context = LocalContext.current
  val metrics by viewModel.adminMetrics.collectAsState()
  val allApps by viewModel.allApplications.collectAsState()
  val searchQuery by viewModel.adminSearchQuery.collectAsState()
  val selectedApp by viewModel.adminSelectedApp.collectAsState()

  var adminTab by remember { mutableStateOf(0) } // 0: Applications, 1: Publish Announcement, 2: Export & Tools
  var reviewDialogApp by remember { mutableStateOf<AdmissionApplication?>(null) }
  var newAnnouncementTitle by remember { mutableStateOf("") }
  var newAnnouncementContent by remember { mutableStateOf("") }
  var newAnnouncementCategory by remember { mutableStateOf("Admissions") }
  var newAnnouncementImportant by remember { mutableStateOf(false) }

  val filteredApps = allApps.filter { app ->
    app.fullName.contains(searchQuery, ignoreCase = true) ||
        app.applicationNumber.contains(searchQuery, ignoreCase = true) ||
        app.programme.contains(searchQuery, ignoreCase = true)
  }

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .background(Color(0xFFF6F8FB))
      .padding(16.dp)
      .testTag("admin_main_console")
  ) {
    // Top Bar with Logout
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Text("Admin Control Console", fontWeight = FontWeight.Black, fontSize = 18.sp, color = NavyDark)
          Text("Hope Polytechnic Admissions & Registry", fontSize = 11.sp, color = Color(0xFF64748B))
        }

        IconButton(
          onClick = { viewModel.logout() },
          modifier = Modifier.testTag("admin_logout_btn")
        ) {
          Icon(imageVector = Icons.Default.ExitToApp, contentDescription = "Log Out", tint = CrimsonRed)
        }
      }
      Spacer(modifier = Modifier.height(12.dp))
    }

    // Metrics Row
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        MetricCard("Total Applicants", "${metrics.totalApplications}", NavyDark, Modifier.weight(1f))
        MetricCard("Approved", "${metrics.approvedApplications}", Color(0xFF16A34A), Modifier.weight(1f))
        MetricCard("Pending Review", "${metrics.pendingApplications}", Color(0xFFD97706), Modifier.weight(1f))
        MetricCard("Revenue", "₦${(metrics.totalRevenue / 1000).toInt()}k", CrimsonRed, Modifier.weight(1f))
      }
      Spacer(modifier = Modifier.height(12.dp))
    }

    // Tabs
    item {
      TabRow(
        selectedTabIndex = adminTab,
        containerColor = Color.White,
        contentColor = NavyDark,
        modifier = Modifier.clip(RoundedCornerShape(8.dp))
      ) {
        Tab(
          selected = adminTab == 0,
          onClick = { adminTab = 0 },
          text = { Text("Applications (${allApps.size})", fontSize = 11.sp, fontWeight = FontWeight.Bold) }
        )
        Tab(
          selected = adminTab == 1,
          onClick = { adminTab = 1 },
          text = { Text("Publish Notice", fontSize = 11.sp, fontWeight = FontWeight.Bold) }
        )
        Tab(
          selected = adminTab == 2,
          onClick = { adminTab = 2 },
          text = { Text("Export & Data", fontSize = 11.sp, fontWeight = FontWeight.Bold) }
        )
      }
      Spacer(modifier = Modifier.height(12.dp))
    }

    when (adminTab) {
      0 -> {
        // Tab 0: Applications List & Search
        item {
          OutlinedTextField(
            value = searchQuery,
            onValueChange = { viewModel.updateAdminSearchQuery(it) },
            placeholder = { Text("Search by student name or application number...") },
            trailingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            modifier = Modifier
              .fillMaxWidth()
              .testTag("admin_search_input")
          )
          Spacer(modifier = Modifier.height(8.dp))
        }

        items(filteredApps) { app ->
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
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Column {
                  Text(app.fullName, fontWeight = FontWeight.Bold, fontSize = 13.5.sp, color = NavyDark)
                  Text("${app.applicationNumber} • ${app.level} ${app.programme}", fontSize = 11.sp, color = Color(0xFF64748B))
                }

                Box(
                  modifier = Modifier
                    .background(
                      when (app.status) {
                        "Approved" -> Color(0xFFDCFCE7)
                        "Under Review" -> Color(0xFFFEF3C7)
                        "Documents Required" -> Color(0xFFFFEDD5)
                        else -> Color(0xFFF1F5F9)
                      },
                      RoundedCornerShape(4.dp)
                    )
                    .padding(horizontal = 8.dp, vertical = 2.dp)
                ) {
                  Text(
                    text = app.status,
                    fontSize = 9.5.sp,
                    fontWeight = FontWeight.Bold,
                    color = when (app.status) {
                      "Approved" -> Color(0xFF16A34A)
                      "Under Review" -> Color(0xFFD97706)
                      "Documents Required" -> Color(0xFFEA580C)
                      else -> NavyDark
                    }
                  )
                }
              }

              Spacer(modifier = Modifier.height(6.dp))
              Text("O'Levels: ${app.subjectGrades}", fontSize = 10.sp, color = Color(0xFF475569))

              Spacer(modifier = Modifier.height(8.dp))

              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
              ) {
                Button(
                  onClick = { reviewDialogApp = app },
                  colors = ButtonDefaults.buttonColors(containerColor = NavyDark),
                  shape = RoundedCornerShape(6.dp),
                  modifier = Modifier.testTag("review_app_${app.applicationNumber.takeLast(4)}")
                ) {
                  Icon(imageVector = Icons.Default.Edit, contentDescription = null, modifier = Modifier.size(14.dp))
                  Spacer(modifier = Modifier.width(4.dp))
                  Text("Review & Decision", fontSize = 11.sp)
                }
              }
            }
          }
        }
      }

      1 -> {
        // Tab 1: Publish Announcement
        item {
          Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(12.dp)
          ) {
            Column(modifier = Modifier.padding(16.dp)) {
              Text("Broadcast School Notice", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = NavyDark)
              Text("Publish announcements to students and admission applicants.", fontSize = 11.sp, color = Color(0xFF64748B))

              Divider(modifier = Modifier.padding(vertical = 8.dp), color = Color(0xFFE2E8F0))

              OutlinedTextField(
                value = newAnnouncementTitle,
                onValueChange = { newAnnouncementTitle = it },
                label = { Text("Notice Title") },
                placeholder = { Text("e.g. Schedule for Physical Screening") },
                modifier = Modifier.fillMaxWidth()
              )

              Spacer(modifier = Modifier.height(8.dp))

              OutlinedTextField(
                value = newAnnouncementCategory,
                onValueChange = { newAnnouncementCategory = it },
                label = { Text("Category") },
                placeholder = { Text("Admissions, Academic Calendar, Events") },
                modifier = Modifier.fillMaxWidth()
              )

              Spacer(modifier = Modifier.height(8.dp))

              OutlinedTextField(
                value = newAnnouncementContent,
                onValueChange = { newAnnouncementContent = it },
                label = { Text("Full Announcement Content") },
                minLines = 3,
                modifier = Modifier.fillMaxWidth()
              )

              Spacer(modifier = Modifier.height(12.dp))

              Button(
                onClick = {
                  if (newAnnouncementTitle.isNotBlank() && newAnnouncementContent.isNotBlank()) {
                    viewModel.publishNewAnnouncement(
                      newAnnouncementTitle,
                      newAnnouncementCategory,
                      newAnnouncementContent,
                      newAnnouncementImportant
                    )
                    newAnnouncementTitle = ""
                    newAnnouncementContent = ""
                  }
                },
                colors = ButtonDefaults.buttonColors(containerColor = CrimsonRed),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
              ) {
                Icon(imageVector = Icons.Default.Campaign, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Broadcast Announcement")
              }
            }
          }
        }
      }

      2 -> {
        // Tab 2: Export Student Records
        item {
          Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(12.dp)
          ) {
            Column(modifier = Modifier.padding(16.dp)) {
              Text("Export & Institutional Reports", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = NavyDark)
              Text("Generate and download student registration records to Excel CSV.", fontSize = 11.sp, color = Color(0xFF64748B))

              Divider(modifier = Modifier.padding(vertical = 10.dp), color = Color(0xFFE2E8F0))

              Text(
                "Total Registered Applicants: ${allApps.size}\n" +
                    "Approved Candidates: ${allApps.count { it.status == "Approved" }}\n" +
                    "Pending Candidates: ${allApps.count { it.status == "Under Review" || it.status == "Application Submitted" }}",
                fontSize = 12.sp,
                color = Color(0xFF334155),
                lineHeight = 18.sp
              )

              Spacer(modifier = Modifier.height(14.dp))

              Button(
                onClick = {
                  val csvData = viewModel.repository.exportApplicationsToCsv(allApps)
                  val sendIntent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/csv"
                    putExtra(Intent.EXTRA_SUBJECT, "Hope Polytechnic 2026/2027 Admission Records")
                    putExtra(Intent.EXTRA_TEXT, csvData)
                  }
                  context.startActivity(Intent.createChooser(sendIntent, "Export Records (Excel/CSV)"))
                },
                colors = ButtonDefaults.buttonColors(containerColor = NavyDark),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                  .fillMaxWidth()
                  .height(46.dp)
                  .testTag("export_records_btn")
              ) {
                Icon(imageVector = Icons.Default.FileDownload, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Export Student Dossiers to Excel (CSV)")
              }
            }
          }
        }
      }
    }

    item {
      Spacer(modifier = Modifier.height(60.dp))
    }
  }

  // Application Review & Decision Dialog
  reviewDialogApp?.let { app ->
    AdminReviewDialog(
      app = app,
      onDismiss = { reviewDialogApp = null },
      onUpdateStatus = { newStatus, notes, matricNo ->
        viewModel.updateAppStatusByAdmin(app.id, newStatus, notes, matricNo)
        reviewDialogApp = null
      }
    )
  }
}

@Composable
private fun AdminReviewDialog(
  app: AdmissionApplication,
  onDismiss: () -> Unit,
  onUpdateStatus: (String, String, String) -> Unit
) {
  var selectedDecision by remember { mutableStateOf(app.status) }
  var notes by remember { mutableStateOf(app.adminNotes) }
  var matricNo by remember {
    mutableStateOf(
      if (app.matricNumber.isNotBlank()) app.matricNumber
      else "HP/${app.level}/${app.programme.take(3).uppercase()}/26/${(100..999).random()}"
    )
  }

  AlertDialog(
    onDismissRequest = onDismiss,
    title = {
      Text(
        text = "Review: ${app.fullName}",
        fontWeight = FontWeight.Bold,
        fontSize = 15.sp,
        color = NavyDark
      )
    },
    text = {
      Column {
        Text("App No: ${app.applicationNumber} • ${app.level} ${app.programme}", fontSize = 11.sp, color = Color(0xFF64748B))
        Text("Exam: ${app.examType} (${app.examYear}) ${app.examNumber}", fontSize = 11.sp, color = Color(0xFF64748B))
        Text("Grades: ${app.subjectGrades}", fontSize = 10.5.sp, color = NavyDark, modifier = Modifier.padding(vertical = 4.dp))

        Divider(modifier = Modifier.padding(vertical = 8.dp), color = Color(0xFFE2E8F0))

        Text("Select Admission Decision:", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = NavyDark)

        listOf("Approved", "Under Review", "Documents Required", "Rejected").forEach { decision ->
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .clickable { selectedDecision = decision }
              .padding(vertical = 3.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            androidx.compose.material3.RadioButton(
              selected = selectedDecision == decision,
              onClick = { selectedDecision = decision }
            )
            Text(decision, fontSize = 12.sp, fontWeight = FontWeight.Medium)
          }
        }

        if (selectedDecision == "Approved") {
          Spacer(modifier = Modifier.height(4.dp))
          OutlinedTextField(
            value = matricNo,
            onValueChange = { matricNo = it },
            label = { Text("Generate Matriculation Number") },
            modifier = Modifier.fillMaxWidth()
          )
        }

        Spacer(modifier = Modifier.height(6.dp))

        OutlinedTextField(
          value = notes,
          onValueChange = { notes = it },
          label = { Text("Remarks to Applicant") },
          minLines = 2,
          modifier = Modifier.fillMaxWidth()
        )
      }
    },
    confirmButton = {
      Button(
        onClick = {
          onUpdateStatus(selectedDecision, notes, if (selectedDecision == "Approved") matricNo else "")
        },
        colors = ButtonDefaults.buttonColors(containerColor = NavyDark)
      ) {
        Text("Save Decision")
      }
    },
    dismissButton = {
      TextButton(onClick = onDismiss) {
        Text("Cancel")
      }
    }
  )
}

@Composable
private fun MetricCard(
  title: String,
  value: String,
  valueColor: Color,
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier,
    colors = CardDefaults.cardColors(containerColor = Color.White),
    shape = RoundedCornerShape(8.dp)
  ) {
    Column(modifier = Modifier.padding(8.dp)) {
      Text(title, fontSize = 9.sp, color = Color(0xFF64748B), maxLines = 1)
      Text(value, fontSize = 15.sp, fontWeight = FontWeight.Black, color = valueColor)
    }
  }
}

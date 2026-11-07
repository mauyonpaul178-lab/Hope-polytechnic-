package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.HourglassTop
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Print
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AdmissionApplication
import com.example.ui.theme.CrimsonRed
import com.example.ui.theme.NavyDark
import com.example.ui.theme.NavyLight
import com.example.ui.viewmodel.PolytechnicViewModel

@Composable
fun TrackApplicationScreen(
  viewModel: PolytechnicViewModel,
  onNavigate: (String) -> Unit
) {
  val searchQuery by viewModel.trackingSearchQuery.collectAsState()
  val result by viewModel.trackingResult.collectAsState()
  val notFound by viewModel.trackingNotFound.collectAsState()

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .background(Color(0xFFF6F8FB))
      .padding(16.dp)
      .testTag("track_app_screen")
  ) {
    // Title
    item {
      Column(modifier = Modifier.padding(bottom = 12.dp)) {
        Text(
          text = "Track Admission Application",
          fontWeight = FontWeight.Black,
          fontSize = 20.sp,
          color = NavyDark
        )
        Text(
          text = "Check the live progress of your 2026/2027 admission application.",
          fontSize = 12.sp,
          color = Color(0xFF64748B),
          modifier = Modifier.padding(top = 2.dp)
        )
      }
    }

    // Search Input Card
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Text(
            text = "Enter Application Number or Registered Email",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = NavyDark
          )

          Spacer(modifier = Modifier.height(8.dp))

          OutlinedTextField(
            value = searchQuery,
            onValueChange = { viewModel.updateTrackingQuery(it) },
            placeholder = { Text("e.g. HP/2026/00142 or email@domain.com") },
            trailingIcon = {
              Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = NavyDark)
            },
            singleLine = true,
            modifier = Modifier
              .fillMaxWidth()
              .testTag("track_search_input")
          )

          Spacer(modifier = Modifier.height(10.dp))

          Button(
            onClick = { viewModel.trackApplication() },
            colors = ButtonDefaults.buttonColors(containerColor = NavyDark),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
              .fillMaxWidth()
              .height(46.dp)
              .testTag("track_submit_button")
          ) {
            Icon(imageVector = Icons.Default.Search, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("Track Application Status", fontWeight = FontWeight.Bold)
          }

          // Sample chips for quick test
          Spacer(modifier = Modifier.height(12.dp))
          Text(
            text = "Quick Demo Applications to Test:",
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF64748B)
          )
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(top = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            listOf(
              "HP/2026/00142" to "Approved",
              "HP/2026/00143" to "Under Review",
              "HP/2026/00144" to "Docs Required"
            ).forEach { (appNo, label) ->
              Box(
                modifier = Modifier
                  .clip(RoundedCornerShape(4.dp))
                  .background(Color(0xFFF1F5F9))
                  .clickable {
                    viewModel.updateTrackingQuery(appNo)
                    viewModel.trackApplication()
                  }
                  .padding(horizontal = 6.dp, vertical = 3.dp)
              ) {
                Text("$appNo ($label)", fontSize = 9.sp, color = NavyDark, fontWeight = FontWeight.SemiBold)
              }
            }
          }
        }
      }
    }

    // Not Found Message
    if (notFound) {
      item {
        Spacer(modifier = Modifier.height(16.dp))
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFFEF2F2), RoundedCornerShape(10.dp))
            .border(1.dp, Color(0xFFFCA5A5), RoundedCornerShape(10.dp))
            .padding(16.dp)
        ) {
          Row(verticalAlignment = Alignment.Top) {
            Icon(imageVector = Icons.Default.Cancel, contentDescription = null, tint = Color(0xFFDC2626), modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.width(10.dp))
            Column {
              Text("Application Record Not Found", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color(0xFF991B1B))
              Text(
                "No active admission application matches \"$searchQuery\". Please double-check your application number or apply newly.",
                fontSize = 11.5.sp,
                color = Color(0xFF7F1D1D),
                lineHeight = 16.sp
              )
            }
          }
        }
      }
    }

    // Tracking Result Card
    result?.let { app ->
      item {
        Spacer(modifier = Modifier.height(16.dp))
        ApplicationStatusDossier(
          app = app,
          viewModel = viewModel,
          onNavigate = onNavigate
        )
      }
    }

    item {
      Spacer(modifier = Modifier.height(60.dp))
    }
  }
}

@Composable
private fun ApplicationStatusDossier(
  app: AdmissionApplication,
  viewModel: PolytechnicViewModel,
  onNavigate: (String) -> Unit
) {
  val (statusColor, statusBg, statusIcon) = when (app.status) {
    "Approved" -> Triple(Color(0xFF16A34A), Color(0xFFDCFCE7), Icons.Default.CheckCircle)
    "Under Review" -> Triple(Color(0xFFD97706), Color(0xFFFEF3C7), Icons.Default.HourglassTop)
    "Documents Required" -> Triple(Color(0xFFEA580C), Color(0xFFFFEDD5), Icons.Default.Warning)
    "Rejected" -> Triple(Color(0xFFDC2626), Color(0xFFFEE2E2), Icons.Default.Cancel)
    else -> Triple(Color(0xFF2563EB), Color(0xFFDBEAFE), Icons.Default.Info)
  }

  Card(
    modifier = Modifier.fillMaxWidth(),
    colors = CardDefaults.cardColors(containerColor = Color.White),
    shape = RoundedCornerShape(12.dp),
    elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
  ) {
    Column(modifier = Modifier.padding(16.dp)) {
      // Header with status badge
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Text("APPLICATION DOSSIER", fontSize = 10.sp, color = Color(0xFF64748B), fontWeight = FontWeight.Bold)
          Text(app.applicationNumber, fontSize = 16.sp, fontWeight = FontWeight.Black, color = NavyDark)
        }

        Box(
          modifier = Modifier
            .background(statusBg, RoundedCornerShape(6.dp))
            .padding(horizontal = 10.dp, vertical = 4.dp)
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = statusIcon, contentDescription = null, tint = statusColor, modifier = Modifier.size(14.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text(app.status.uppercase(), fontSize = 10.sp, fontWeight = FontWeight.Black, color = statusColor)
          }
        }
      }

      Divider(modifier = Modifier.padding(vertical = 10.dp), color = Color(0xFFE2E8F0))

      // Timeline Steps
      AdmissionTimeline(currentStatus = app.status)

      Spacer(modifier = Modifier.height(14.dp))

      // Applicant Summary Box
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .background(Color(0xFFF8FAFC), RoundedCornerShape(8.dp))
          .padding(12.dp)
      ) {
        DossierRow("Candidate:", app.fullName)
        DossierRow("Programme:", "${app.level} ${app.programme}")
        DossierRow("State / LGA:", "${app.stateOfOrigin} / ${app.lga}")
        DossierRow("Exam Details:", "${app.examType} (${app.examYear}) - ${app.examNumber}")
        if (app.matricNumber.isNotBlank()) {
          DossierRow("Matric Number:", app.matricNumber)
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      // Official Admin Feedback Message
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .background(statusBg.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
          .border(1.dp, statusColor.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
          .padding(10.dp)
      ) {
        Column {
          Text("Admissions Committee Remarks:", fontSize = 10.5.sp, fontWeight = FontWeight.Bold, color = statusColor)
          Text(
            text = app.adminNotes,
            fontSize = 11.sp,
            color = Color(0xFF1E293B),
            lineHeight = 15.sp,
            modifier = Modifier.padding(top = 2.dp)
          )
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      // Dynamic Contextual Action Buttons
      when (app.status) {
        "Approved" -> {
          // Actions for Approved Students
          Button(
            onClick = { viewModel.viewAdmissionLetter(app) },
            colors = ButtonDefaults.buttonColors(containerColor = NavyDark),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
              .fillMaxWidth()
              .height(44.dp)
              .testTag("track_view_admission_letter_btn")
          ) {
            Icon(imageVector = Icons.Default.Verified, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("Download / View Official Admission Letter", fontWeight = FontWeight.Bold, fontSize = 12.5.sp)
          }

          Spacer(modifier = Modifier.height(8.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            Button(
              onClick = {
                viewModel.openPayment(
                  applicationNumber = app.applicationNumber,
                  studentName = app.fullName,
                  paymentTitle = "Provisional Acceptance Fee",
                  amount = 25000.0
                )
              },
              colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF16A34A)),
              shape = RoundedCornerShape(8.dp),
              modifier = Modifier
                .weight(1f)
                .height(42.dp)
                .testTag("pay_acceptance_fee_btn")
            ) {
              Icon(imageVector = Icons.Default.CreditCard, contentDescription = null, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(4.dp))
              Text("Pay Acceptance (₦25k)", fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }

            OutlinedButton(
              onClick = { viewModel.viewRegistrationSlip(app) },
              shape = RoundedCornerShape(8.dp),
              modifier = Modifier
                .weight(1f)
                .height(42.dp)
                .testTag("track_view_reg_slip_btn")
            ) {
              Icon(imageVector = Icons.Default.Print, contentDescription = null, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(4.dp))
              Text("Print Reg Slip", fontSize = 11.sp)
            }
          }
        }

        "Documents Required" -> {
          Button(
            onClick = {
              viewModel.showToast("Document upload portal opened. Please provide missing credentials.")
            },
            colors = ButtonDefaults.buttonColors(containerColor = CrimsonRed),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
              .fillMaxWidth()
              .height(44.dp)
          ) {
            Text("Upload Required Documents Now", fontWeight = FontWeight.Bold)
          }
        }

        else -> {
          OutlinedButton(
            onClick = { viewModel.viewRegistrationSlip(app) },
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
              .fillMaxWidth()
              .height(44.dp)
          ) {
            Icon(imageVector = Icons.Default.Print, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("Print Registration Slip")
          }
        }
      }
    }
  }
}

@Composable
private fun AdmissionTimeline(currentStatus: String) {
  val steps = listOf(
    "Application Submitted",
    "Under Review",
    "Documents Verification",
    if (currentStatus == "Rejected") "Rejected" else "Approved"
  )

  val activeIndex = when (currentStatus) {
    "Application Submitted" -> 0
    "Under Review" -> 1
    "Documents Required" -> 2
    "Approved", "Rejected" -> 3
    else -> 0
  }

  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = 6.dp),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    steps.forEachIndexed { idx, label ->
      val isPast = idx < activeIndex
      val isCurrent = idx == activeIndex

      Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.weight(1f)
      ) {
        Box(
          modifier = Modifier
            .size(20.dp)
            .clip(CircleShape)
            .background(
              when {
                isCurrent -> if (currentStatus == "Rejected") Color(0xFFDC2626) else CrimsonRed
                isPast -> Color(0xFF16A34A)
                else -> Color(0xFFCBD5E1)
              }
            ),
          contentAlignment = Alignment.Center
        ) {
          if (isPast) {
            Icon(imageVector = Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(12.dp))
          }
        }
        Text(
          text = label,
          fontSize = 8.sp,
          fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Normal,
          color = if (isCurrent) NavyDark else Color(0xFF64748B),
          maxLines = 1,
          modifier = Modifier.padding(top = 2.dp)
        )
      }
    }
  }
}

@Composable
private fun DossierRow(label: String, value: String) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = 1.5.dp)
  ) {
    Text(label, fontSize = 11.sp, fontWeight = FontWeight.Medium, color = Color(0xFF64748B), modifier = Modifier.width(100.dp))
    Text(value, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = NavyDark)
  }
}

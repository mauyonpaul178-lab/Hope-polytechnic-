package com.example.ui.components

import android.content.Intent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Print
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.R
import com.example.data.model.AdmissionApplication
import com.example.ui.theme.CrimsonRed
import com.example.ui.theme.NavyDark
import com.example.ui.theme.NavyPrimary

@Composable
fun RegistrationSlipDialog(
  application: AdmissionApplication,
  onDismiss: () -> Unit
) {
  val context = LocalContext.current

  Dialog(
    onDismissRequest = onDismiss,
    properties = DialogProperties(usePlatformDefaultWidth = false)
  ) {
    Surface(
      modifier = Modifier
        .fillMaxWidth(0.95f)
        .padding(vertical = 24.dp)
        .clip(RoundedCornerShape(16.dp)),
      color = Color.White,
      shadowElevation = 16.dp
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .verticalScroll(rememberScrollState())
          .padding(20.dp)
      ) {
        // Title Bar
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "Admission Registration Slip",
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp,
            color = NavyDark
          )
          IconButton(onClick = onDismiss, modifier = Modifier.testTag("close_slip_btn")) {
            Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
          }
        }

        Divider(modifier = Modifier.padding(vertical = 6.dp), color = Color(0xFFE2E8F0))

        // Printable Slip Card
        Card(
          modifier = Modifier
            .fillMaxWidth()
            .border(BorderStroke(1.dp, Color(0xFFCBD5E1)), RoundedCornerShape(8.dp)),
          colors = CardDefaults.cardColors(containerColor = Color(0xFFFAFAFA))
        ) {
          Column(
            modifier = Modifier
              .fillMaxWidth()
              .padding(14.dp)
          ) {
            // Header
            Row(
              modifier = Modifier.fillMaxWidth(),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Image(
                painter = painterResource(id = R.drawable.app_icon_hope_1793998303235),
                contentDescription = "Hope Logo",
                modifier = Modifier
                  .size(50.dp)
                  .clip(CircleShape)
              )
              Spacer(modifier = Modifier.width(10.dp))
              Column(modifier = Modifier.weight(1f)) {
                Text(
                  text = "HOPE POLYTECHNIC",
                  fontWeight = FontWeight.Black,
                  fontSize = 15.sp,
                  color = NavyDark
                )
                Text(
                  text = "Mbak Itam III, Uyo, Akwa Ibom State",
                  fontSize = 11.sp,
                  color = Color(0xFF475569)
                )
                Text(
                  text = "2026/2027 ADMISSION APPLICATION SLIP",
                  fontSize = 11.sp,
                  fontWeight = FontWeight.Bold,
                  color = CrimsonRed
                )
              }
              // Passport photo box
              Box(
                modifier = Modifier
                  .size(54.dp, 60.dp)
                  .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
                  .background(Color(0xFFE2E8F0)),
                contentAlignment = Alignment.Center
              ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                  Icon(imageVector = Icons.Default.Person, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(24.dp))
                  Text("PASSPORT", fontSize = 7.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
                }
              }
            }

            Divider(modifier = Modifier.padding(vertical = 8.dp), color = Color(0xFFCBD5E1))

            // Application Numbers
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .background(NavyDark, RoundedCornerShape(4.dp))
                .padding(8.dp),
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Column {
                Text("APPLICATION NUMBER", color = Color.LightGray, fontSize = 9.sp)
                Text(application.applicationNumber, color = Color.White, fontWeight = FontWeight.Black, fontSize = 13.sp)
              }
              Column(horizontalAlignment = Alignment.End) {
                Text("STATUS", color = Color.LightGray, fontSize = 9.sp)
                Text(application.status.uppercase(), color = Color(0xFFFFD700), fontWeight = FontWeight.Bold, fontSize = 11.sp)
              }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Section: Personal Information
            SectionHeader("PERSONAL INFORMATION")
            SlipRow("Full Name", application.fullName)
            SlipRow("Date of Birth", application.dateOfBirth)
            SlipRow("Gender", application.gender)
            SlipRow("Nationality", application.nationality)
            SlipRow("State of Origin", application.stateOfOrigin)
            SlipRow("LGA", application.lga)
            SlipRow("Address", application.residentialAddress)
            SlipRow("Phone Number", application.phone)
            SlipRow("WhatsApp Number", application.whatsappNumber)
            SlipRow("Email Address", application.email)

            Spacer(modifier = Modifier.height(8.dp))

            // Section: Programme & Academic Info
            SectionHeader("ACADEMIC & PROGRAMME DETAILS")
            SlipRow("Programme Choice", "${application.level} ${application.programme}")
            SlipRow("Previous School", application.previousSchool)
            SlipRow("Exam Type", application.examType)
            SlipRow("Exam Number", application.examNumber)
            SlipRow("Exam Year", application.examYear)
            SlipRow("JAMB Reg No", if (application.jambRegNo.isNotBlank()) application.jambRegNo else "N/A")
            SlipRow("Qualifications", application.previousQualifications)
            SlipRow("O'Level Grades", application.subjectGrades)

            Spacer(modifier = Modifier.height(8.dp))

            // Section: Next of Kin
            SectionHeader("NEXT OF KIN")
            SlipRow("Name", application.nextOfKinName)
            SlipRow("Relationship", application.nextOfKinRelationship)
            SlipRow("Phone", application.nextOfKinPhone)
            SlipRow("Address", application.nextOfKinAddress)

            Spacer(modifier = Modifier.height(8.dp))

            // Section: Documents
            SectionHeader("SUBMITTED DOCUMENTS")
            SlipRow("Passport Photo", "VERIFIED (Uploaded)")
            SlipRow("O'Level Result Slip", "VERIFIED (Uploaded)")
            SlipRow("Birth Certificate", "VERIFIED (Uploaded)")
            SlipRow("LGA Identification", "VERIFIED (Uploaded)")

            Spacer(modifier = Modifier.height(12.dp))

            // Declaration & Candidate Signature
            Text(
              text = "Declaration: I hereby declare that all information provided above is true and accurate. Any falsification leads to automatic disqualification.",
              fontSize = 9.5.sp,
              color = Color(0xFF64748B),
              lineHeight = 13.sp
            )

            Spacer(modifier = Modifier.height(14.dp))

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.Bottom
            ) {
              Column {
                Divider(modifier = Modifier.width(100.dp), color = Color.Gray)
                Text("Candidate Signature", fontSize = 9.sp, color = Color.Gray)
              }
              Column(horizontalAlignment = Alignment.End) {
                Divider(modifier = Modifier.width(100.dp), color = Color.Gray)
                Text("Admissions Desk Stamp", fontSize = 9.sp, color = Color.Gray)
              }
            }
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Action Buttons
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          OutlinedButton(
            onClick = {
              val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(
                  Intent.EXTRA_SUBJECT,
                  "Hope Polytechnic Registration Slip - ${application.applicationNumber}"
                )
                putExtra(
                  Intent.EXTRA_TEXT,
                  "HOPE POLYTECHNIC 2026/2027 REGISTRATION SLIP\n" +
                      "Candidate: ${application.fullName}\n" +
                      "App No: ${application.applicationNumber}\n" +
                      "Programme: ${application.level} ${application.programme}\n" +
                      "State/LGA: ${application.stateOfOrigin} / ${application.lga}\n" +
                      "Status: ${application.status}\n" +
                      "Campus: Mbak Itam III, Uyo, Akwa Ibom State"
                )
              }
              context.startActivity(Intent.createChooser(shareIntent, "Share Registration Slip"))
            },
            modifier = Modifier.weight(1f)
          ) {
            Icon(imageVector = Icons.Default.Share, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("Share")
          }

          Button(
            onClick = onDismiss,
            colors = ButtonDefaults.buttonColors(containerColor = NavyDark),
            modifier = Modifier.weight(1f)
          ) {
            Icon(imageVector = Icons.Default.Print, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("Done")
          }
        }
      }
    }
  }
}

@Composable
private fun SectionHeader(title: String) {
  Text(
    text = title,
    fontSize = 10.sp,
    fontWeight = FontWeight.Bold,
    color = CrimsonRed,
    letterSpacing = 0.5.sp,
    modifier = Modifier.padding(vertical = 2.dp)
  )
}

@Composable
private fun SlipRow(label: String, value: String) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = 1.5.dp)
  ) {
    Text(
      text = label,
      fontSize = 10.5.sp,
      fontWeight = FontWeight.Medium,
      color = Color(0xFF64748B),
      modifier = Modifier.width(115.dp)
    )
    Text(
      text = if (value.isNotBlank()) value else "N/A",
      fontSize = 10.5.sp,
      fontWeight = FontWeight.SemiBold,
      color = Color(0xFF0F172A)
    )
  }
}

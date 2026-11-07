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
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Print
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Verified
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.R
import com.example.data.model.AdmissionApplication
import com.example.ui.theme.CrimsonRed
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.NavyDark
import com.example.ui.theme.NavyPrimary
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun AdmissionLetterDialog(
  application: AdmissionApplication,
  onDismiss: () -> Unit
) {
  val context = LocalContext.current
  val todayStr = SimpleDateFormat("dd MMMM, yyyy", Locale.US).format(Date())

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
        // Top action bar
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.Verified,
              contentDescription = null,
              tint = Color(0xFF16A34A),
              modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "Official Admission Letter",
              fontWeight = FontWeight.Bold,
              fontSize = 15.sp,
              color = NavyDark
            )
          }

          IconButton(
            onClick = onDismiss,
            modifier = Modifier.testTag("close_admission_letter_btn")
          ) {
            Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
          }
        }

        Divider(modifier = Modifier.padding(vertical = 8.dp), color = Color(0xFFE2E8F0))

        // Printable Letterhead Container
        Card(
          modifier = Modifier
            .fillMaxWidth()
            .border(BorderStroke(1.5.dp, NavyDark), RoundedCornerShape(8.dp)),
          colors = CardDefaults.cardColors(containerColor = Color(0xFFFCFDFE)),
          shape = RoundedCornerShape(8.dp)
        ) {
          Column(
            modifier = Modifier
              .fillMaxWidth()
              .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            // Institutional Header
            Image(
              painter = painterResource(id = R.drawable.app_icon_hope_1793998303235),
              contentDescription = "Hope Polytechnic Logo",
              modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
              text = "HOPE POLYTECHNIC",
              fontWeight = FontWeight.Black,
              fontSize = 18.sp,
              color = NavyDark,
              letterSpacing = 1.sp
            )
            Text(
              text = "Mbak Itam III, Uyo, Akwa Ibom State, Nigeria",
              fontSize = 11.sp,
              color = Color(0xFF475569)
            )
            Text(
              text = "Motto: Learning Today, Leading Tomorrow",
              fontSize = 11.sp,
              fontWeight = FontWeight.SemiBold,
              color = CrimsonRed
            )
            Text(
              text = "OFFICE OF THE REGISTRAR - ADMISSIONS UNIT",
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold,
              color = NavyPrimary,
              modifier = Modifier.padding(top = 4.dp)
            )

            Divider(
              modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
              thickness = 2.dp,
              color = NavyDark
            )

            // Letter Meta Information
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Column {
                Text(
                  text = "Ref: HP/ADM/2026/${application.applicationNumber.takeLast(5)}",
                  fontSize = 11.sp,
                  fontWeight = FontWeight.Bold,
                  color = Color(0xFF1E293B)
                )
                Text(
                  text = "App No: ${application.applicationNumber}",
                  fontSize = 11.sp,
                  color = Color(0xFF475569)
                )
                if (application.matricNumber.isNotBlank()) {
                  Text(
                    text = "Matric No: ${application.matricNumber}",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = CrimsonRed
                  )
                }
              }

              Column(horizontalAlignment = Alignment.End) {
                Text(
                  text = "Date: $todayStr",
                  fontSize = 11.sp,
                  fontWeight = FontWeight.Medium,
                  color = Color(0xFF1E293B)
                )
                Text(
                  text = "Session: 2026/2027",
                  fontSize = 11.sp,
                  fontWeight = FontWeight.Bold,
                  color = NavyPrimary
                )
              }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Candidate Name
            Text(
              text = "PROVISIONAL OFFER OF ADMISSION",
              fontWeight = FontWeight.Black,
              fontSize = 14.sp,
              color = CrimsonRed,
              textAlign = TextAlign.Center,
              modifier = Modifier
                .background(CrimsonRed.copy(alpha = 0.08f), RoundedCornerShape(4.dp))
                .padding(horizontal = 12.dp, vertical = 4.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Candidate Address
            Column(
              modifier = Modifier.fillMaxWidth(),
              horizontalAlignment = Alignment.Start
            ) {
              Text(
                text = "Dear ${application.fullName.uppercase()},",
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                color = NavyDark
              )
              Text(
                text = "${application.residentialAddress}, ${application.lga}, ${application.stateOfOrigin}.",
                fontSize = 11.sp,
                color = Color(0xFF475569)
              )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Body Paragraphs
            Text(
              text = "1. I am pleased to inform you that the Academic Board of Hope Polytechnic has offered you Provisional Admission into the ${application.academicSession} Academic Session as follows:",
              fontSize = 11.5.sp,
              color = Color(0xFF1E293B),
              lineHeight = 16.sp,
              textAlign = TextAlign.Justify
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Offer Table
            Column(
              modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF1F5F9), RoundedCornerShape(6.dp))
                .padding(10.dp)
            ) {
              OfferRow("Programme:", application.programme)
              OfferRow("Award / Level:", "${application.level} (${if (application.level == "ND") "National Diploma" else "Higher National Diploma"})")
              OfferRow("Academic Session:", application.academicSession)
              OfferRow("Study Mode:", "Full-Time (2 Academic Years)")
              OfferRow("Campus:", "Main Campus, Mbak Itam III, Uyo")
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
              text = "2. CONDITIONS OF OFFER:\n" +
                  "• This offer is subject to verification of your original credentials (WAEC/NECO/NABTEB, birth certificate, and LGA identification).\n" +
                  "• You are required to pay a non-refundable Acceptance Fee of ₦25,000 within two (2) weeks of this notification.\n" +
                  "• Failure to register within the stipulated deadline implies forfeiture of this admission.",
              fontSize = 11.sp,
              color = Color(0xFF334155),
              lineHeight = 15.sp
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Signatures & Official Stamp
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.Bottom
            ) {
              Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                  modifier = Modifier
                    .size(54.dp)
                    .border(1.5.dp, Color(0xFF16A34A), CircleShape),
                  contentAlignment = Alignment.Center
                ) {
                  Text(
                    text = "HOPE POLY\nOFFICIAL\nSEAL",
                    fontSize = 7.5.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF16A34A),
                    textAlign = TextAlign.Center
                  )
                }
                Text(
                  text = "VERIFIED",
                  fontSize = 9.sp,
                  fontWeight = FontWeight.Bold,
                  color = Color(0xFF16A34A)
                )
              }

              Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                  text = "Engr. Dr. Anietie Udoh",
                  fontWeight = FontWeight.Bold,
                  fontSize = 11.sp,
                  fontFamily = FontFamily.Cursive,
                  color = NavyDark
                )
                Divider(
                  modifier = Modifier
                    .width(130.dp)
                    .padding(vertical = 2.dp),
                  color = Color.Gray
                )
                Text(
                  text = "Registrar & Secretary to Council",
                  fontSize = 9.5.sp,
                  color = Color(0xFF64748B)
                )
                Text(
                  text = "Hope Polytechnic, Uyo",
                  fontSize = 9.sp,
                  color = Color(0xFF64748B)
                )
              }
            }
          }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Action Buttons: Share & Print
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
                  "Hope Polytechnic Admission Letter - ${application.fullName}"
                )
                putExtra(
                  Intent.EXTRA_TEXT,
                  "PROVISIONAL ADMISSION OFFER - HOPE POLYTECHNIC\n" +
                      "Candidate: ${application.fullName}\n" +
                      "Application No: ${application.applicationNumber}\n" +
                      "Matric No: ${application.matricNumber}\n" +
                      "Programme: ${application.level} ${application.programme}\n" +
                      "Session: ${application.academicSession}\n" +
                      "Campus: Mbak Itam III, Uyo, Akwa Ibom State\n" +
                      "Congratulations on your admission!"
                )
              }
              context.startActivity(Intent.createChooser(shareIntent, "Share Admission Letter"))
            },
            modifier = Modifier
              .weight(1f)
              .testTag("share_admission_letter_btn")
          ) {
            Icon(imageVector = Icons.Default.Share, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("Share")
          }

          Button(
            onClick = onDismiss,
            colors = ButtonDefaults.buttonColors(containerColor = NavyDark),
            modifier = Modifier
              .weight(1f)
              .testTag("done_admission_letter_btn")
          ) {
            Icon(imageVector = Icons.Default.Print, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("Print Slip")
          }
        }
      }
    }
  }
}

@Composable
private fun OfferRow(label: String, value: String) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = 2.dp)
  ) {
    Text(
      text = label,
      fontSize = 11.sp,
      fontWeight = FontWeight.SemiBold,
      color = Color(0xFF475569),
      modifier = Modifier.width(110.dp)
    )
    Text(
      text = value,
      fontSize = 11.sp,
      fontWeight = FontWeight.Bold,
      color = NavyDark
    )
  }
}

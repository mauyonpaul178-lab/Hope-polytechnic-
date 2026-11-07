package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.theme.CrimsonRed
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.NavyDark
import com.example.ui.theme.NavyLight

data class FaqItem(val question: String, val answer: String)

@Composable
fun SchoolInfoScreen() {
  val context = LocalContext.current

  val faqs = listOf(
    FaqItem(
      "What are the general admission requirements for ND programmes?",
      "Applicants must possess a minimum of five (5) credit passes in relevant subjects at SSCE (WAEC, NECO, or NABTEB) in not more than two sittings, including English Language and Mathematics."
    ),
    FaqItem(
      "Is JAMB required for Hope Polytechnic 2026/2027 admission?",
      "Candidates who took the 2026 UTME can provide their registration number. However, candidates seeking direct admission or remedial screening can also register directly on this portal for screening."
    ),
    FaqItem(
      "What is the duration of ND and HND programmes?",
      "Both the National Diploma (ND) and Higher National Diploma (HND) programmes run for two (2) academic years (four semesters) of intensive theoretical lectures and practical workshop sessions."
    ),
    FaqItem(
      "Are campus accommodation hostels available for new students?",
      "Yes. Hope Polytechnic provides secure, affordable, and fully serviced student hostels on campus with uninterrupted water and power supply."
    ),
    FaqItem(
      "How do I pay my application and acceptance fees?",
      "All fees are securely paid through our integrated Nigerian payment gateways (Paystack/Flutterwave) using debit cards, instant bank transfers, or bank USSD. Instant automated e-receipts are issued upon payment."
    )
  )

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .background(Color(0xFFF6F8FB))
      .padding(16.dp)
      .testTag("school_info_screen")
  ) {
    // Header
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Image(
          painter = painterResource(id = R.drawable.app_icon_hope_1793998303235),
          contentDescription = null,
          modifier = Modifier
            .size(54.dp)
            .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column {
          Text("Hope Polytechnic", fontWeight = FontWeight.Black, fontSize = 18.sp, color = NavyDark)
          Text("Mbak Itam III, Uyo, Akwa Ibom State", fontSize = 11.5.sp, color = Color(0xFF64748B))
          Text("Tagline: Learning Today, Leading Tomorrow", fontSize = 10.5.sp, fontWeight = FontWeight.Bold, color = CrimsonRed)
        }
      }
      Spacer(modifier = Modifier.height(14.dp))
    }

    // Vision and Mission
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Text("Vision & Mission", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = NavyDark)
          Divider(modifier = Modifier.padding(vertical = 8.dp), color = Color(0xFFE2E8F0))

          Text("OUR VISION", fontSize = 10.sp, fontWeight = FontWeight.Black, color = CrimsonRed, letterSpacing = 1.sp)
          Text(
            "To be a distinguished center of technological excellence, delivering world-class practical technical and entrepreneurial education that equips graduates to thrive in a dynamically changing global workforce.",
            fontSize = 11.5.sp,
            color = Color(0xFF334155),
            lineHeight = 16.sp,
            modifier = Modifier.padding(top = 2.dp, bottom = 10.dp)
          )

          Text("OUR MISSION", fontSize = 10.sp, fontWeight = FontWeight.Black, color = NavyDark, letterSpacing = 1.sp)
          Text(
            "To provide modern instructional facilities, foster innovative research, and nurture morally upright, technically competent leaders grounded in science, engineering, business, and ICT.",
            fontSize = 11.5.sp,
            color = Color(0xFF334155),
            lineHeight = 16.sp
          )
        }
      }
      Spacer(modifier = Modifier.height(12.dp))
    }

    // Academic Departments
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Text("Academic Schools & Departments", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = NavyDark)
          Divider(modifier = Modifier.padding(vertical = 8.dp), color = Color(0xFFE2E8F0))

          DeptRow("1. School of Applied Sciences & ICT", "Computer Science, Science Laboratory Technology")
          DeptRow("2. School of Engineering Technology", "Electrical/Electronics Engineering, Mechanical Engineering")
          DeptRow("3. School of Business & Management", "Business Administration, Accountancy, Public Administration")
          DeptRow("4. School of Media & Arts", "Mass Communication, Multimedia Studies")
        }
      }
      Spacer(modifier = Modifier.height(12.dp))
    }

    // Student Support & Guidance
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = NavyDark),
        shape = RoundedCornerShape(12.dp)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Default.SupportAgent, contentDescription = null, tint = GoldAccent, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text("Student Support & Welfare Services", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
          }

          Spacer(modifier = Modifier.height(8.dp))

          Text(
            "• Academic Guidance & Mentorship Unit\n" +
                "• ICT Helpdesk & Portal Support (8 AM - 6 PM Daily)\n" +
                "• Campus Medical Clinic & First-Aid Services\n" +
                "• Industrial Attachment (SIWES) Placement Assistance\n" +
                "• Entrepreneurship & Incubation Hub",
            color = Color.White.copy(alpha = 0.9f),
            fontSize = 11.5.sp,
            lineHeight = 18.sp
          )
        }
      }
      Spacer(modifier = Modifier.height(14.dp))
    }

    // FAQs Section
    item {
      Text(
        text = "Frequently Asked Questions (FAQ)",
        fontWeight = FontWeight.Bold,
        fontSize = 15.sp,
        color = NavyDark,
        modifier = Modifier.padding(bottom = 6.dp)
      )
    }

    items(faqs) { faq ->
      FaqExpandableCard(faq = faq)
    }

    // Contact Card
    item {
      Spacer(modifier = Modifier.height(12.dp))
      Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Text("Official Contact & Enquiries", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = NavyDark)
          Divider(modifier = Modifier.padding(vertical = 8.dp), color = Color(0xFFE2E8F0))

          ContactInfoLine(Icons.Default.LocationOn, "Location", "Mbak Itam III, Uyo, Akwa Ibom State, Nigeria")
          ContactInfoLine(Icons.Default.Call, "Admissions Hotline 1", "08067560799")
          ContactInfoLine(Icons.Default.Call, "Admissions Hotline 2", "08066638334")
          ContactInfoLine(Icons.Default.Email, "Email Address", "hopepolytechnic@gmail.com")

          Spacer(modifier = Modifier.height(12.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            Button(
              onClick = {
                val intent = Intent(
                  Intent.ACTION_VIEW,
                  Uri.parse("https://api.whatsapp.com/send?phone=2348067560799&text=Hello%20Hope%20Polytechnic%2C%20I%20enquire%20about%202026%2F2027%20admission")
                )
                context.startActivity(intent)
              },
              colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF25D366)),
              shape = RoundedCornerShape(8.dp),
              modifier = Modifier.weight(1f)
            ) {
              Text("WhatsApp", fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }

            Button(
              onClick = {
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:08067560799"))
                context.startActivity(intent)
              },
              colors = ButtonDefaults.buttonColors(containerColor = NavyDark),
              shape = RoundedCornerShape(8.dp),
              modifier = Modifier.weight(1f)
            ) {
              Text("Call Line 1", fontSize = 12.sp, fontWeight = FontWeight.Bold)
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

@Composable
private fun DeptRow(school: String, courses: String) {
  Column(modifier = Modifier.padding(vertical = 4.dp)) {
    Text(school, fontWeight = FontWeight.Bold, fontSize = 12.sp, color = NavyDark)
    Text(courses, fontSize = 11.sp, color = Color(0xFF64748B))
  }
}

@Composable
private fun FaqExpandableCard(faq: FaqItem) {
  var expanded by remember { mutableStateOf(false) }

  Card(
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = 4.dp)
      .clickable { expanded = !expanded },
    colors = CardDefaults.cardColors(containerColor = Color.White),
    shape = RoundedCornerShape(8.dp)
  ) {
    Column(modifier = Modifier.padding(12.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = faq.question,
          fontWeight = FontWeight.SemiBold,
          fontSize = 12.sp,
          color = NavyDark,
          modifier = Modifier.weight(1f)
        )
        Icon(
          imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
          contentDescription = null,
          tint = CrimsonRed
        )
      }

      AnimatedVisibility(visible = expanded) {
        Text(
          text = faq.answer,
          fontSize = 11.5.sp,
          color = Color(0xFF475569),
          lineHeight = 16.sp,
          modifier = Modifier.padding(top = 8.dp)
        )
      }
    }
  }
}

@Composable
private fun ContactInfoLine(
  icon: androidx.compose.ui.graphics.vector.ImageVector,
  title: String,
  value: String
) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = 3.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Icon(imageVector = icon, contentDescription = null, tint = CrimsonRed, modifier = Modifier.size(16.dp))
    Spacer(modifier = Modifier.width(8.dp))
    Column {
      Text(title, fontSize = 9.5.sp, color = Color(0xFF64748B))
      Text(value, fontSize = 11.5.sp, fontWeight = FontWeight.SemiBold, color = NavyDark)
    }
  }
}

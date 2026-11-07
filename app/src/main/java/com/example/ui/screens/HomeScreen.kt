package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.material.icons.filled.AppRegistration
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.theme.CrimsonRed
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.NavyDark
import com.example.ui.theme.NavyLight
import com.example.ui.theme.NavyPrimary
import com.example.ui.viewmodel.PolytechnicViewModel

@Composable
fun HomeScreen(
  viewModel: PolytechnicViewModel,
  onNavigate: (String) -> Unit
) {
  val context = LocalContext.current
  val announcements by viewModel.allAnnouncements.collectAsState()
  val programmes = viewModel.programmes

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .background(Color(0xFFF6F8FB))
      .testTag("home_screen_lazy_column")
  ) {
    // 1. Campus Hero Header with Image
    item {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .height(230.dp)
      ) {
        Image(
          painter = painterResource(id = R.drawable.campus_hero_1793998315946),
          contentDescription = "Hope Polytechnic Campus Entrance",
          contentScale = ContentScale.Crop,
          modifier = Modifier.fillMaxSize()
        )

        // Gradient Overlay
        Box(
          modifier = Modifier
            .fillMaxSize()
            .background(
              Brush.verticalGradient(
                colors = listOf(
                  Color.Transparent,
                  NavyDark.copy(alpha = 0.75f),
                  NavyDark
                )
              )
            )
        )

        // Hero Content
        Column(
          modifier = Modifier
            .align(Alignment.BottomStart)
            .padding(16.dp)
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
              .background(CrimsonRed, RoundedCornerShape(4.dp))
              .padding(horizontal = 8.dp, vertical = 3.dp)
          ) {
            Text(
              text = "2026/2027 ADMISSIONS OPEN",
              color = Color.White,
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold,
              letterSpacing = 0.5.sp
            )
          }

          Spacer(modifier = Modifier.height(6.dp))

          Text(
            text = "Welcome to Hope Polytechnic",
            color = Color.White,
            fontWeight = FontWeight.Black,
            fontSize = 20.sp,
            lineHeight = 24.sp
          )

          Text(
            text = "Learning Today, Leading Tomorrow • Mbak Itam III, Uyo",
            color = GoldAccent,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium
          )
        }
      }
    }

    // 2. Quick Action CTA Buttons
    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp)
          .padding(top = 12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Text(
            text = "Online Admission Exercise",
            fontWeight = FontWeight.Black,
            fontSize = 16.sp,
            color = NavyDark
          )
          Text(
            text = "Applications are formally invited from suitably qualified candidates for admission into full-time National Diploma (ND) and Higher National Diploma (HND) programmes.",
            fontSize = 12.sp,
            color = Color(0xFF475569),
            lineHeight = 17.sp,
            modifier = Modifier.padding(top = 4.dp, bottom = 12.dp)
          )

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            Button(
              onClick = { onNavigate("apply") },
              colors = ButtonDefaults.buttonColors(containerColor = CrimsonRed),
              shape = RoundedCornerShape(8.dp),
              modifier = Modifier
                .weight(1f)
                .height(44.dp)
                .testTag("home_apply_now_btn")
            ) {
              Icon(imageVector = Icons.Default.AppRegistration, contentDescription = null, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(6.dp))
              Text("Apply Now", fontWeight = FontWeight.Bold, fontSize = 13.sp)
            }

            OutlinedButton(
              onClick = { onNavigate("track") },
              shape = RoundedCornerShape(8.dp),
              modifier = Modifier
                .weight(1f)
                .height(44.dp)
                .testTag("home_track_app_btn")
            ) {
              Icon(imageVector = Icons.Default.Search, contentDescription = null, modifier = Modifier.size(16.dp), tint = NavyDark)
              Spacer(modifier = Modifier.width(6.dp))
              Text("Track Status", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = NavyDark)
            }
          }
        }
      }
    }

    // 3. Four Portal Shortcuts Grid
    item {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        PortalCard(
          title = "Courses & Programmes",
          subtitle = "8 ND/HND Options",
          icon = Icons.Default.School,
          color = NavyPrimary,
          modifier = Modifier.weight(1f),
          onClick = { onNavigate("programmes") }
        )
        PortalCard(
          title = "Student Portal",
          subtitle = "Login & Slips",
          icon = Icons.Default.Computer,
          color = CrimsonRed,
          modifier = Modifier.weight(1f),
          onClick = { onNavigate("student_portal") }
        )
      }
    }

    // 4. About Institution Section
    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp, vertical = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
              modifier = Modifier
                .size(36.dp)
                .background(NavyLight.copy(alpha = 0.15f), RoundedCornerShape(8.dp)),
              contentAlignment = Alignment.Center
            ) {
              Icon(imageVector = Icons.Default.AccountBalance, contentDescription = null, tint = NavyDark)
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column {
              Text(
                text = "About Hope Polytechnic",
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = NavyDark
              )
              Text(
                text = "Approved Technical Education Provider",
                fontSize = 11.sp,
                color = Color(0xFF64748B)
              )
            }
          }

          Spacer(modifier = Modifier.height(10.dp))

          Text(
            text = "Hope Polytechnic is a premier tertiary institution established to deliver high-quality, hands-on polytechnic education in science, technology, engineering, management, and media studies. Located in the serene academic environment of Mbak Itam III, Uyo, Akwa Ibom State, the institution is committed to producing skilled, innovative, and ethically grounded professionals capable of spearheading technological and industrial advancement in Nigeria and globally.",
            fontSize = 12.sp,
            color = Color(0xFF334155),
            lineHeight = 18.sp
          )

          Spacer(modifier = Modifier.height(12.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            BadgeInfo("Motto", "Learning Today, Leading Tomorrow", GoldAccent, NavyDark, Modifier.weight(1f))
            BadgeInfo("Session", "2026/2027 Academic Year", Color(0xFFDCFCE7), Color(0xFF166534), Modifier.weight(1f))
          }
        }
      }
    }

    // 5. Available Programmes Section Header
    item {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Text(
            text = "Accredited Programmes",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = NavyDark
          )
          Text(
            text = "National Diploma (ND) & Higher National Diploma (HND)",
            fontSize = 11.sp,
            color = Color(0xFF64748B)
          )
        }
        Text(
          text = "View All",
          fontSize = 12.sp,
          fontWeight = FontWeight.Bold,
          color = CrimsonRed,
          modifier = Modifier
            .clickable { onNavigate("programmes") }
            .padding(4.dp)
        )
      }
    }

    // Top 4 Programmes preview
    items(programmes.take(4)) { prog ->
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp, vertical = 4.dp)
          .clickable { onNavigate("programmes") },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(10.dp)
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Box(
            modifier = Modifier
              .size(42.dp)
              .background(NavyDark, RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
          ) {
            Text(
              text = prog.code,
              color = Color.White,
              fontWeight = FontWeight.Bold,
              fontSize = 13.sp
            )
          }

          Spacer(modifier = Modifier.width(12.dp))

          Column(modifier = Modifier.weight(1f)) {
            Text(
              text = prog.name,
              fontWeight = FontWeight.Bold,
              fontSize = 13.sp,
              color = NavyDark
            )
            Text(
              text = prog.faculty,
              fontSize = 11.sp,
              color = Color(0xFF64748B)
            )
            Row(
              modifier = Modifier.padding(top = 2.dp),
              horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
              prog.levels.forEach { lvl ->
                Box(
                  modifier = Modifier
                    .background(CrimsonRed.copy(alpha = 0.1f), RoundedCornerShape(4.dp))
                    .padding(horizontal = 6.dp, vertical = 1.dp)
                ) {
                  Text(text = lvl, fontSize = 9.sp, fontWeight = FontWeight.Bold, color = CrimsonRed)
                }
              }
            }
          }

          Icon(
            imageVector = Icons.Default.ArrowForward,
            contentDescription = null,
            tint = Color(0xFF94A3B8),
            modifier = Modifier.size(18.dp)
          )
        }
      }
    }

    // 6. School Facilities Section
    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp, vertical = 12.dp),
        colors = CardDefaults.cardColors(containerColor = NavyDark),
        shape = RoundedCornerShape(12.dp)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Text(
            text = "Modern Facilities & Learning Environment",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp
          )
          Text(
            text = "Engineered for 80% practical industry competence",
            color = GoldAccent,
            fontSize = 11.sp,
            modifier = Modifier.padding(bottom = 12.dp)
          )

          val facilities = listOf(
            "Ultra-Modern Computer Science & AI Tech Hub",
            "Science Laboratory Technology Research Laboratories",
            "Electrical/Electronics Simulation & Power Workshop",
            "Mechanical Engineering & Automotive Workshop",
            "Digital E-Library with High-Speed Satellite Internet",
            "Mass Communication Multimedia Broadcasting Studio",
            "Hostel Accommodation & Campus Sports Pavilions"
          )

          facilities.forEach { facility ->
            Row(
              modifier = Modifier.padding(vertical = 3.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Box(
                modifier = Modifier
                  .size(6.dp)
                  .background(CrimsonRed, CircleShape)
              )
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = facility,
                color = Color.White.copy(alpha = 0.9f),
                fontSize = 11.5.sp
              )
            }
          }
        }
      }
    }

    // 7. Latest Announcements Section
    item {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp, vertical = 6.dp)
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Icon(
            imageVector = Icons.Default.Campaign,
            contentDescription = null,
            tint = CrimsonRed,
            modifier = Modifier.size(20.dp)
          )
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = "Latest Institutional Announcements",
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp,
            color = NavyDark
          )
        }
      }
    }

    items(announcements) { notice ->
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp, vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(10.dp)
      ) {
        Column(modifier = Modifier.padding(14.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Box(
              modifier = Modifier
                .background(
                  if (notice.isImportant) CrimsonRed.copy(alpha = 0.12f) else NavyLight.copy(alpha = 0.12f),
                  RoundedCornerShape(4.dp)
                )
                .padding(horizontal = 8.dp, vertical = 2.dp)
            ) {
              Text(
                text = notice.category.uppercase(),
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                color = if (notice.isImportant) CrimsonRed else NavyLight
              )
            }

            Text(
              text = notice.date,
              fontSize = 10.sp,
              color = Color(0xFF94A3B8)
            )
          }

          Spacer(modifier = Modifier.height(6.dp))

          Text(
            text = notice.title,
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp,
            color = NavyDark
          )

          Spacer(modifier = Modifier.height(4.dp))

          Text(
            text = notice.content,
            fontSize = 11.5.sp,
            color = Color(0xFF475569),
            lineHeight = 16.sp
          )
        }
      }
    }

    // 8. Contact & Location Information Card
    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Text(
            text = "Contact & Admissions Enquiry",
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp,
            color = NavyDark
          )

          Divider(modifier = Modifier.padding(vertical = 8.dp), color = Color(0xFFE2E8F0))

          ContactLine(Icons.Default.LocationOn, "Location", "Mbak Itam III, Uyo, Akwa Ibom State, Nigeria")
          ContactLine(Icons.Default.Call, "Admissions Hotlines", "08067560799, 08066638334")
          ContactLine(Icons.Default.Email, "Official Email", "hopepolytechnic@gmail.com")

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
              Text("Chat on WhatsApp", fontSize = 11.sp, fontWeight = FontWeight.Bold)
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
              Text("Call Hotline", fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }
          }
        }
      }
    }

    // Bottom Spacer
    item {
      Spacer(modifier = Modifier.height(60.dp))
    }
  }
}

@Composable
private fun PortalCard(
  title: String,
  subtitle: String,
  icon: androidx.compose.ui.graphics.vector.ImageVector,
  color: Color,
  modifier: Modifier = Modifier,
  onClick: () -> Unit
) {
  Card(
    modifier = modifier.clickable { onClick() },
    colors = CardDefaults.cardColors(containerColor = Color.White),
    shape = RoundedCornerShape(10.dp),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
  ) {
    Column(
      modifier = Modifier.padding(14.dp)
    ) {
      Box(
        modifier = Modifier
          .size(36.dp)
          .background(color.copy(alpha = 0.12f), RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center
      ) {
        Icon(imageVector = icon, contentDescription = null, tint = color, modifier = Modifier.size(20.dp))
      }
      Spacer(modifier = Modifier.height(8.dp))
      Text(text = title, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = NavyDark)
      Text(text = subtitle, fontSize = 11.sp, color = Color(0xFF64748B))
    }
  }
}

@Composable
private fun BadgeInfo(
  label: String,
  value: String,
  bgColor: Color,
  textColor: Color,
  modifier: Modifier = Modifier
) {
  Box(
    modifier = modifier
      .background(bgColor, RoundedCornerShape(6.dp))
      .padding(8.dp)
  ) {
    Column {
      Text(text = label.uppercase(), fontSize = 9.sp, fontWeight = FontWeight.Bold, color = textColor.copy(alpha = 0.8f))
      Text(text = value, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = textColor)
    }
  }
}

@Composable
private fun ContactLine(
  icon: androidx.compose.ui.graphics.vector.ImageVector,
  title: String,
  detail: String
) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = 4.dp),
    verticalAlignment = Alignment.Top
  ) {
    Icon(imageVector = icon, contentDescription = null, tint = CrimsonRed, modifier = Modifier.size(16.dp))
    Spacer(modifier = Modifier.width(8.dp))
    Column {
      Text(text = title, fontSize = 10.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF64748B))
      Text(text = detail, fontSize = 11.5.sp, fontWeight = FontWeight.Medium, color = NavyDark)
    }
  }
}

package com.example.ui.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.theme.CrimsonRed
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.NavyDark
import com.example.ui.theme.NavyLight
import com.example.ui.theme.NavyPrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
  currentScreen: String,
  onNavigateAdmin: () -> Unit,
  onHomeClick: () -> Unit
) {
  val context = LocalContext.current

  Surface(
    color = NavyDark,
    shadowElevation = 6.dp
  ) {
    Column(modifier = Modifier.fillMaxWidth()) {
      // Top Institutional Announcement Bar
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .background(CrimsonRed)
          .padding(horizontal = 16.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "ADMISSION 2026/2027 NOW OPEN",
          color = Color.White,
          fontSize = 11.sp,
          fontWeight = FontWeight.Bold,
          letterSpacing = 1.sp,
          modifier = Modifier.weight(1f)
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
          Text(
            text = "Mbak Itam III, Uyo",
            color = Color.White.copy(alpha = 0.9f),
            fontSize = 11.sp
          )
        }
      }

      // Main Top Bar
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        // Logo Crest & School Info
        Row(
          modifier = Modifier
            .weight(1f)
            .clickable { onHomeClick() },
          verticalAlignment = Alignment.CenterVertically
        ) {
          Image(
            painter = painterResource(id = R.drawable.app_icon_hope_1793998303235),
            contentDescription = "Hope Polytechnic Official Crest",
            modifier = Modifier
              .size(46.dp)
              .clip(CircleShape)
              .background(Color.White)
              .padding(2.dp)
          )

          Spacer(modifier = Modifier.width(10.dp))

          Column {
            Text(
              text = "HOPE POLYTECHNIC",
              color = Color.White,
              fontWeight = FontWeight.Black,
              fontSize = 16.sp,
              letterSpacing = 0.5.sp
            )
            Text(
              text = "Learning Today, Leading Tomorrow",
              color = GoldAccent,
              fontSize = 11.sp,
              fontWeight = FontWeight.Medium,
              maxLines = 1,
              overflow = TextOverflow.Ellipsis
            )
          }
        }

        // Action Buttons: WhatsApp, Call, Admin
        Row(verticalAlignment = Alignment.CenterVertically) {
          // WhatsApp Quick Button
          IconButton(
            onClick = {
              try {
                val intent = Intent(
                  Intent.ACTION_VIEW,
                  Uri.parse("https://api.whatsapp.com/send?phone=2348067560799&text=Hello%20Hope%20Polytechnic%2C%20I%20enquire%20about%202026%2F2027%20admission")
                )
                context.startActivity(intent)
              } catch (e: Exception) {
                val callIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:08067560799"))
                context.startActivity(callIntent)
              }
            },
            modifier = Modifier
              .size(38.dp)
              .testTag("whatsapp_header_btn")
          ) {
            Box(
              modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(Color(0xFF25D366)),
              contentAlignment = Alignment.Center
            ) {
              Text(
                text = "WA",
                color = Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
              )
            }
          }

          Spacer(modifier = Modifier.width(4.dp))

          // Phone Call Button
          IconButton(
            onClick = {
              val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:08067560799"))
              context.startActivity(intent)
            },
            modifier = Modifier
              .size(38.dp)
              .testTag("call_header_btn")
          ) {
            Box(
              modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(NavyLight),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.Call,
                contentDescription = "Call Admissions Hotline",
                tint = Color.White,
                modifier = Modifier.size(16.dp)
              )
            }
          }

          Spacer(modifier = Modifier.width(4.dp))

          // Admin Portal Button
          IconButton(
            onClick = onNavigateAdmin,
            modifier = Modifier
              .size(38.dp)
              .testTag("admin_header_btn")
          ) {
            Box(
              modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(if (currentScreen == "admin_dashboard") CrimsonRed else NavyLight),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.AdminPanelSettings,
                contentDescription = "Admin Portal",
                tint = Color.White,
                modifier = Modifier.size(18.dp)
              )
            }
          }
        }
      }
    }
  }
}

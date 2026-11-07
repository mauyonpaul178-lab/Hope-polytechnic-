package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AppRegistration
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.School
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.CrimsonRed
import com.example.ui.theme.NavyDark
import com.example.ui.theme.NavyLight

data class NavItem(
  val route: String,
  val label: String,
  val icon: ImageVector
)

@Composable
fun AppBottomNav(
  currentScreen: String,
  onNavigate: (String) -> Unit
) {
  val items = listOf(
    NavItem("home", "Home", Icons.Default.Home),
    NavItem("programmes", "Courses", Icons.Outlined.School),
    NavItem("apply", "Apply", Icons.Default.AppRegistration),
    NavItem("track", "Track", Icons.Default.Search),
    NavItem("student_portal", "Portal", Icons.Default.AccountCircle),
    NavItem("about", "About", Icons.Default.Info)
  )

  Surface(
    color = NavyDark,
    shadowElevation = 12.dp
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 6.dp, horizontal = 4.dp),
      horizontalArrangement = Arrangement.SpaceAround,
      verticalAlignment = Alignment.CenterVertically
    ) {
      items.forEach { item ->
        val selected = currentScreen == item.route

        Column(
          horizontalAlignment = Alignment.CenterHorizontally,
          modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable { onNavigate(item.route) }
            .padding(horizontal = 6.dp, vertical = 4.dp)
            .testTag("nav_item_${item.route}")
        ) {
          Box(
            modifier = Modifier
              .size(34.dp)
              .clip(RoundedCornerShape(8.dp))
              .background(if (selected) CrimsonRed else Color.Transparent),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = item.icon,
              contentDescription = item.label,
              tint = if (selected) Color.White else Color.White.copy(alpha = 0.7f),
              modifier = Modifier.size(20.dp)
            )
          }

          Text(
            text = item.label,
            color = if (selected) Color.White else Color.White.copy(alpha = 0.65f),
            fontSize = 10.sp,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
            modifier = Modifier.padding(top = 2.dp)
          )
        }
      }
    }
  }
}

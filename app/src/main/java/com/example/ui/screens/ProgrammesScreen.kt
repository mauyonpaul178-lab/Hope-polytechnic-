package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AppRegistration
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AcademicProgramme
import com.example.ui.theme.CrimsonRed
import com.example.ui.theme.NavyDark
import com.example.ui.theme.NavyLight
import com.example.ui.viewmodel.PolytechnicViewModel

@Composable
fun ProgrammesScreen(
  viewModel: PolytechnicViewModel,
  onApplyForProgramme: (String, String) -> Unit
) {
  var selectedFacultyFilter by remember { mutableStateOf("All") }
  var searchQuery by remember { mutableStateOf("") }
  var expandedProgrammeId by remember { mutableStateOf<Int?>(1) }

  val faculties = listOf("All", "Engineering", "Applied Sciences", "Business", "Communication")

  val filteredProgrammes = viewModel.programmes.filter { prog ->
    val matchesFaculty = when (selectedFacultyFilter) {
      "All" -> true
      "Engineering" -> prog.faculty.contains("Engineering", ignoreCase = true)
      "Applied Sciences" -> prog.faculty.contains("Applied Sciences", ignoreCase = true)
      "Business" -> prog.faculty.contains("Business", ignoreCase = true)
      "Communication" -> prog.faculty.contains("Communication", ignoreCase = true)
      else -> true
    }
    val matchesSearch = prog.name.contains(searchQuery, ignoreCase = true) ||
        prog.code.contains(searchQuery, ignoreCase = true) ||
        prog.description.contains(searchQuery, ignoreCase = true)

    matchesFaculty && matchesSearch
  }

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .background(Color(0xFFF6F8FB))
      .padding(16.dp)
      .testTag("programmes_screen_list")
  ) {
    // Header
    item {
      Column(modifier = Modifier.padding(bottom = 12.dp)) {
        Text(
          text = "Available Programmes (ND & HND)",
          fontWeight = FontWeight.Black,
          fontSize = 20.sp,
          color = NavyDark
        )
        Text(
          text = "Choose your field of study for the 2026/2027 Academic Session at Hope Polytechnic.",
          fontSize = 12.sp,
          color = Color(0xFF64748B),
          modifier = Modifier.padding(top = 4.dp)
        )
      }
    }

    // Faculty filter chips
    item {
      LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(bottom = 12.dp)
      ) {
        items(faculties) { faculty ->
          val isSelected = selectedFacultyFilter == faculty
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(20.dp))
              .background(if (isSelected) NavyDark else Color.White)
              .clickable { selectedFacultyFilter = faculty }
              .padding(horizontal = 14.dp, vertical = 7.dp)
          ) {
            Text(
              text = faculty,
              color = if (isSelected) Color.White else NavyDark,
              fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
              fontSize = 12.sp
            )
          }
        }
      }
    }

    // Programme Cards
    items(filteredProgrammes) { prog ->
      val isExpanded = expandedProgrammeId == prog.id

      Card(
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 6.dp)
          .testTag("programme_card_${prog.code}"),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          // Card Title Row
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .clickable {
                expandedProgrammeId = if (isExpanded) null else prog.id
              },
            verticalAlignment = Alignment.CenterVertically
          ) {
            Box(
              modifier = Modifier
                .size(46.dp)
                .background(NavyDark, RoundedCornerShape(10.dp)),
              contentAlignment = Alignment.Center
            ) {
              Text(
                text = prog.code,
                color = Color.White,
                fontWeight = FontWeight.Black,
                fontSize = 14.sp
              )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = prog.name,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = NavyDark
              )
              Text(
                text = prog.faculty,
                fontSize = 11.sp,
                color = Color(0xFF64748B)
              )
              Row(
                modifier = Modifier.padding(top = 3.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
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
                Text(
                  text = prog.duration,
                  fontSize = 9.sp,
                  color = Color(0xFF94A3B8),
                  modifier = Modifier.align(Alignment.CenterVertically)
                )
              }
            }

            Icon(
              imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
              contentDescription = null,
              tint = NavyDark
            )
          }

          // Expanded Details
          AnimatedVisibility(visible = isExpanded) {
            Column(modifier = Modifier.padding(top = 12.dp)) {
              Divider(color = Color(0xFFE2E8F0))

              Spacer(modifier = Modifier.height(10.dp))

              Text(
                text = "Programme Overview",
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                color = NavyDark
              )
              Text(
                text = prog.description,
                fontSize = 11.5.sp,
                color = Color(0xFF334155),
                lineHeight = 16.sp,
                modifier = Modifier.padding(top = 2.dp)
              )

              Spacer(modifier = Modifier.height(10.dp))

              Text(
                text = "Admission & O'Level Requirements",
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                color = CrimsonRed
              )
              Text(
                text = prog.requirements,
                fontSize = 11.5.sp,
                color = Color(0xFF334155),
                lineHeight = 16.sp,
                modifier = Modifier.padding(top = 2.dp)
              )

              Spacer(modifier = Modifier.height(10.dp))

              Text(
                text = "Career & Industry Opportunities",
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                color = NavyDark
              )
              Text(
                text = prog.careerProspects,
                fontSize = 11.5.sp,
                color = Color(0xFF475569),
                lineHeight = 16.sp,
                modifier = Modifier.padding(top = 2.dp)
              )

              Spacer(modifier = Modifier.height(14.dp))

              // Apply Buttons (ND & HND)
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
              ) {
                Button(
                  onClick = { onApplyForProgramme(prog.name, "ND") },
                  colors = ButtonDefaults.buttonColors(containerColor = NavyDark),
                  shape = RoundedCornerShape(8.dp),
                  modifier = Modifier
                    .weight(1f)
                    .testTag("apply_nd_${prog.code}")
                ) {
                  Icon(imageVector = Icons.Default.AppRegistration, contentDescription = null, modifier = Modifier.size(16.dp))
                  Spacer(modifier = Modifier.width(4.dp))
                  Text("Apply for ND", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }

                Button(
                  onClick = { onApplyForProgramme(prog.name, "HND") },
                  colors = ButtonDefaults.buttonColors(containerColor = CrimsonRed),
                  shape = RoundedCornerShape(8.dp),
                  modifier = Modifier
                    .weight(1f)
                    .testTag("apply_hnd_${prog.code}")
                ) {
                  Icon(imageVector = Icons.Default.School, contentDescription = null, modifier = Modifier.size(16.dp))
                  Spacer(modifier = Modifier.width(4.dp))
                  Text("Apply for HND", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
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
}

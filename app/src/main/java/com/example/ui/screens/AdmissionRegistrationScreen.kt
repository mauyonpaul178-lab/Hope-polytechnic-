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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Print
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.CrimsonRed
import com.example.ui.theme.NavyDark
import com.example.ui.theme.NavyLight
import com.example.ui.viewmodel.PolytechnicViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdmissionRegistrationScreen(
  viewModel: PolytechnicViewModel,
  onNavigate: (String) -> Unit
) {
  val form by viewModel.formState.collectAsState()

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .background(Color(0xFFF6F8FB))
      .padding(16.dp)
      .testTag("admission_reg_screen")
  ) {
    // Header Title
    item {
      Column(modifier = Modifier.padding(bottom = 12.dp)) {
        Text(
          text = "Online Student Admission Portal",
          fontWeight = FontWeight.Black,
          fontSize = 20.sp,
          color = NavyDark
        )
        Text(
          text = "2026/2027 Academic Session Application Form",
          fontSize = 12.sp,
          fontWeight = FontWeight.SemiBold,
          color = CrimsonRed
        )
      }
    }

    // Step Progress Indicator
    item {
      StepProgressBar(currentStep = form.currentStep)
      Spacer(modifier = Modifier.height(14.dp))
    }

    // Render Steps
    when (form.currentStep) {
      1 -> item { Step1PersonalInfo(viewModel = viewModel) }
      2 -> item { Step2AcademicInfo(viewModel = viewModel) }
      3 -> item { Step3NextOfKin(viewModel = viewModel) }
      4 -> item { Step4Documents(viewModel = viewModel) }
      5 -> item {
        Step5ReviewAndSubmit(
          viewModel = viewModel,
          onNavigate = onNavigate
        )
      }
    }

    // Error banner if any
    form.errorMessage?.let { errorMsg ->
      item {
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(Color(0xFFFEE2E2), RoundedCornerShape(8.dp))
            .border(1.dp, Color(0xFFFCA5A5), RoundedCornerShape(8.dp))
            .padding(12.dp)
        ) {
          Text(text = errorMsg, color = Color(0xFF991B1B), fontSize = 12.sp)
        }
      }
    }

    // Step Navigation Buttons (Prev / Next)
    if (form.currentStep < 5) {
      item {
        Spacer(modifier = Modifier.height(16.dp))
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          if (form.currentStep > 1) {
            OutlinedButton(
              onClick = { viewModel.prevStep() },
              shape = RoundedCornerShape(8.dp),
              modifier = Modifier
                .weight(1f)
                .height(46.dp)
                .testTag("reg_prev_step_btn")
            ) {
              Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(6.dp))
              Text("Previous")
            }
          }

          Button(
            onClick = {
              if (form.currentStep == 4) {
                viewModel.submitApplication()
              } else {
                viewModel.nextStep()
              }
            },
            enabled = !form.isSubmitting,
            colors = ButtonDefaults.buttonColors(containerColor = NavyDark),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
              .weight(1f)
              .height(46.dp)
              .testTag("reg_next_step_btn")
          ) {
            if (form.isSubmitting) {
              CircularProgressIndicator(color = Color.White, modifier = Modifier.size(20.dp))
            } else {
              Text(
                if (form.currentStep == 4) "Submit Application" else "Continue",
                fontWeight = FontWeight.Bold
              )
              Spacer(modifier = Modifier.width(6.dp))
              Icon(imageVector = Icons.Default.ArrowForward, contentDescription = null, modifier = Modifier.size(16.dp))
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
private fun StepProgressBar(currentStep: Int) {
  val stepTitles = listOf("Personal", "Academic", "Next of Kin", "Documents", "Status")

  Row(
    modifier = Modifier
      .fillMaxWidth()
      .background(Color.White, RoundedCornerShape(10.dp))
      .padding(10.dp),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    stepTitles.forEachIndexed { index, title ->
      val stepNum = index + 1
      val isDone = stepNum < currentStep
      val isCurrent = stepNum == currentStep

      Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
          modifier = Modifier
            .size(26.dp)
            .clip(CircleShape)
            .background(
              when {
                isDone -> Color(0xFF16A34A)
                isCurrent -> CrimsonRed
                else -> Color(0xFFE2E8F0)
              }
            ),
          contentAlignment = Alignment.Center
        ) {
          if (isDone) {
            Icon(imageVector = Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(14.dp))
          } else {
            Text(
              text = "$stepNum",
              color = if (isCurrent) Color.White else Color(0xFF64748B),
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold
            )
          }
        }

        Text(
          text = title,
          fontSize = 9.sp,
          fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Normal,
          color = if (isCurrent) NavyDark else Color(0xFF94A3B8),
          modifier = Modifier.padding(top = 2.dp)
        )
      }
    }
  }
}

@Composable
private fun Step1PersonalInfo(viewModel: PolytechnicViewModel) {
  val form by viewModel.formState.collectAsState()

  Card(
    modifier = Modifier.fillMaxWidth(),
    colors = CardDefaults.cardColors(containerColor = Color.White),
    shape = RoundedCornerShape(12.dp)
  ) {
    Column(modifier = Modifier.padding(16.dp)) {
      Text("Step 1: Personal Information", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = NavyDark)
      Text("Enter your bio-data as it appears on your official credentials.", fontSize = 11.sp, color = Color(0xFF64748B))

      Divider(modifier = Modifier.padding(vertical = 10.dp), color = Color(0xFFE2E8F0))

      OutlinedTextField(
        value = form.fullName,
        onValueChange = { viewModel.updateForm { f -> f.copy(fullName = it) } },
        label = { Text("Full Name (Surname First)") },
        placeholder = { Text("e.g. Bassey Emmanuel Edet") },
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 4.dp)
          .testTag("reg_input_full_name")
      )

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        OutlinedTextField(
          value = form.dateOfBirth,
          onValueChange = { viewModel.updateForm { f -> f.copy(dateOfBirth = it) } },
          label = { Text("Date of Birth (YYYY-MM-DD)") },
          modifier = Modifier
            .weight(1f)
            .padding(vertical = 4.dp)
        )

        // Gender Selector
        Column(modifier = Modifier.weight(1f)) {
          Text("Gender", fontSize = 12.sp, color = Color(0xFF64748B))
          Row(verticalAlignment = Alignment.CenterVertically) {
            listOf("Male", "Female").forEach { g ->
              Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                  .clickable { viewModel.updateForm { f -> f.copy(gender = g) } }
                  .padding(end = 6.dp)
              ) {
                RadioButton(
                  selected = form.gender == g,
                  onClick = { viewModel.updateForm { f -> f.copy(gender = g) } }
                )
                Text(g, fontSize = 11.sp)
              }
            }
          }
        }
      }

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        OutlinedTextField(
          value = form.stateOfOrigin,
          onValueChange = { viewModel.updateForm { f -> f.copy(stateOfOrigin = it) } },
          label = { Text("State of Origin") },
          modifier = Modifier
            .weight(1f)
            .padding(vertical = 4.dp)
        )
        OutlinedTextField(
          value = form.lga,
          onValueChange = { viewModel.updateForm { f -> f.copy(lga = it) } },
          label = { Text("LGA") },
          modifier = Modifier
            .weight(1f)
            .padding(vertical = 4.dp)
        )
      }

      OutlinedTextField(
        value = form.residentialAddress,
        onValueChange = { viewModel.updateForm { f -> f.copy(residentialAddress = it) } },
        label = { Text("Residential Address") },
        placeholder = { Text("House number, street, city") },
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 4.dp)
      )

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        OutlinedTextField(
          value = form.phone,
          onValueChange = { viewModel.updateForm { f -> f.copy(phone = it) } },
          label = { Text("Phone Number") },
          placeholder = { Text("08012345678") },
          modifier = Modifier
            .weight(1f)
            .padding(vertical = 4.dp)
            .testTag("reg_input_phone")
        )
        OutlinedTextField(
          value = form.whatsappNumber,
          onValueChange = { viewModel.updateForm { f -> f.copy(whatsappNumber = it) } },
          label = { Text("WhatsApp Number") },
          placeholder = { Text("08012345678") },
          modifier = Modifier
            .weight(1f)
            .padding(vertical = 4.dp)
        )
      }

      OutlinedTextField(
        value = form.email,
        onValueChange = { viewModel.updateForm { f -> f.copy(email = it) } },
        label = { Text("Email Address") },
        placeholder = { Text("applicant@gmail.com") },
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 4.dp)
          .testTag("reg_input_email")
      )
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Step2AcademicInfo(viewModel: PolytechnicViewModel) {
  val form by viewModel.formState.collectAsState()
  val programmes = viewModel.programmes

  var progExpanded by remember { mutableStateOf(false) }

  Card(
    modifier = Modifier.fillMaxWidth(),
    colors = CardDefaults.cardColors(containerColor = Color.White),
    shape = RoundedCornerShape(12.dp)
  ) {
    Column(modifier = Modifier.padding(16.dp)) {
      Text("Step 2: Academic Information & Choice of Course", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = NavyDark)
      Text("Provide examination results and select desired programme.", fontSize = 11.sp, color = Color(0xFF64748B))

      Divider(modifier = Modifier.padding(vertical = 10.dp), color = Color(0xFFE2E8F0))

      // Level Selection (ND / HND)
      Text("Admission Level", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = NavyDark)
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        listOf("ND", "HND").forEach { lvl ->
          val isSelected = form.level == lvl
          Box(
            modifier = Modifier
              .weight(1f)
              .clip(RoundedCornerShape(8.dp))
              .background(if (isSelected) NavyDark else Color(0xFFF1F5F9))
              .clickable { viewModel.updateForm { f -> f.copy(level = lvl) } }
              .padding(vertical = 8.dp),
            contentAlignment = Alignment.Center
          ) {
            Text(
              text = if (lvl == "ND") "National Diploma (ND)" else "Higher National Diploma (HND)",
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold,
              color = if (isSelected) Color.White else NavyDark
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(6.dp))

      // Programme Selector Dropdown
      ExposedDropdownMenuBox(
        expanded = progExpanded,
        onExpandedChange = { progExpanded = !progExpanded },
        modifier = Modifier.fillMaxWidth()
      ) {
        OutlinedTextField(
          value = form.programme,
          onValueChange = {},
          readOnly = true,
          label = { Text("Select Programme") },
          trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = progExpanded) },
          modifier = Modifier
            .menuAnchor()
            .fillMaxWidth()
        )
        ExposedDropdownMenu(
          expanded = progExpanded,
          onDismissRequest = { progExpanded = false }
        ) {
          programmes.forEach { p ->
            DropdownMenuItem(
              text = { Text(p.name, fontSize = 12.sp) },
              onClick = {
                viewModel.updateForm { f -> f.copy(programme = p.name) }
                progExpanded = false
              }
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(8.dp))

      OutlinedTextField(
        value = form.previousSchool,
        onValueChange = { viewModel.updateForm { f -> f.copy(previousSchool = it) } },
        label = { Text("Previous School Attended") },
        placeholder = { Text("Secondary school or previous polytechnic") },
        modifier = Modifier.fillMaxWidth()
      )

      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(top = 6.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        // Exam Type
        Column(modifier = Modifier.weight(1f)) {
          Text("Exam Type", fontSize = 11.sp, color = Color(0xFF64748B))
          Row {
            listOf("WAEC", "NECO", "NABTEB").forEach { ex ->
              Box(
                modifier = Modifier
                  .clip(RoundedCornerShape(4.dp))
                  .background(if (form.examType == ex) CrimsonRed else Color(0xFFE2E8F0))
                  .clickable { viewModel.updateForm { f -> f.copy(examType = ex) } }
                  .padding(horizontal = 6.dp, vertical = 4.dp)
                  .padding(end = 2.dp)
              ) {
                Text(ex, color = if (form.examType == ex) Color.White else Color.Black, fontSize = 10.sp, fontWeight = FontWeight.Bold)
              }
              Spacer(modifier = Modifier.width(3.dp))
            }
          }
        }

        OutlinedTextField(
          value = form.examYear,
          onValueChange = { viewModel.updateForm { f -> f.copy(examYear = it) } },
          label = { Text("Exam Year") },
          modifier = Modifier.weight(1f)
        )
      }

      OutlinedTextField(
        value = form.examNumber,
        onValueChange = { viewModel.updateForm { f -> f.copy(examNumber = it) } },
        label = { Text("Exam Registration Number") },
        placeholder = { Text("e.g. 4031982741") },
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 4.dp)
      )

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        OutlinedTextField(
          value = form.jambRegNo,
          onValueChange = { viewModel.updateForm { f -> f.copy(jambRegNo = it) } },
          label = { Text("JAMB Reg No (If any)") },
          modifier = Modifier.weight(1f)
        )
        OutlinedTextField(
          value = form.previousQualifications,
          onValueChange = { viewModel.updateForm { f -> f.copy(previousQualifications = it) } },
          label = { Text("Qualifications") },
          modifier = Modifier.weight(1f)
        )
      }

      Spacer(modifier = Modifier.height(10.dp))

      Text("O'Level Subject Grades", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = NavyDark)

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
      ) {
        OutlinedTextField(
          value = form.englishGrade,
          onValueChange = { viewModel.updateForm { f -> f.copy(englishGrade = it) } },
          label = { Text("English") },
          modifier = Modifier.weight(1f)
        )
        OutlinedTextField(
          value = form.mathsGrade,
          onValueChange = { viewModel.updateForm { f -> f.copy(mathsGrade = it) } },
          label = { Text("Maths") },
          modifier = Modifier.weight(1f)
        )
      }

      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(top = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
      ) {
        OutlinedTextField(
          value = form.subject3Name,
          onValueChange = { viewModel.updateForm { f -> f.copy(subject3Name = it) } },
          label = { Text("Subj 3 Name") },
          modifier = Modifier.weight(1.5f)
        )
        OutlinedTextField(
          value = form.subject3Grade,
          onValueChange = { viewModel.updateForm { f -> f.copy(subject3Grade = it) } },
          label = { Text("Grade") },
          modifier = Modifier.weight(1f)
        )
      }

      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(top = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
      ) {
        OutlinedTextField(
          value = form.subject4Name,
          onValueChange = { viewModel.updateForm { f -> f.copy(subject4Name = it) } },
          label = { Text("Subj 4 Name") },
          modifier = Modifier.weight(1.5f)
        )
        OutlinedTextField(
          value = form.subject4Grade,
          onValueChange = { viewModel.updateForm { f -> f.copy(subject4Grade = it) } },
          label = { Text("Grade") },
          modifier = Modifier.weight(1f)
        )
      }

      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(top = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
      ) {
        OutlinedTextField(
          value = form.subject5Name,
          onValueChange = { viewModel.updateForm { f -> f.copy(subject5Name = it) } },
          label = { Text("Subj 5 Name") },
          modifier = Modifier.weight(1.5f)
        )
        OutlinedTextField(
          value = form.subject5Grade,
          onValueChange = { viewModel.updateForm { f -> f.copy(subject5Grade = it) } },
          label = { Text("Grade") },
          modifier = Modifier.weight(1f)
        )
      }
    }
  }
}

@Composable
private fun Step3NextOfKin(viewModel: PolytechnicViewModel) {
  val form by viewModel.formState.collectAsState()

  Card(
    modifier = Modifier.fillMaxWidth(),
    colors = CardDefaults.cardColors(containerColor = Color.White),
    shape = RoundedCornerShape(12.dp)
  ) {
    Column(modifier = Modifier.padding(16.dp)) {
      Text("Step 3: Next of Kin Information", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = NavyDark)
      Text("Emergency and sponsorship contact details.", fontSize = 11.sp, color = Color(0xFF64748B))

      Divider(modifier = Modifier.padding(vertical = 10.dp), color = Color(0xFFE2E8F0))

      OutlinedTextField(
        value = form.nextOfKinName,
        onValueChange = { viewModel.updateForm { f -> f.copy(nextOfKinName = it) } },
        label = { Text("Next of Kin Full Name") },
        placeholder = { Text("e.g. Mr. Okon Bassey") },
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 4.dp)
      )

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        OutlinedTextField(
          value = form.nextOfKinRelationship,
          onValueChange = { viewModel.updateForm { f -> f.copy(nextOfKinRelationship = it) } },
          label = { Text("Relationship") },
          placeholder = { Text("Father / Mother / Guardian") },
          modifier = Modifier.weight(1f)
        )
        OutlinedTextField(
          value = form.nextOfKinPhone,
          onValueChange = { viewModel.updateForm { f -> f.copy(nextOfKinPhone = it) } },
          label = { Text("Phone Number") },
          modifier = Modifier.weight(1f)
        )
      }

      OutlinedTextField(
        value = form.nextOfKinAddress,
        onValueChange = { viewModel.updateForm { f -> f.copy(nextOfKinAddress = it) } },
        label = { Text("Residential Address") },
        placeholder = { Text("Address of next of kin") },
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 4.dp)
      )
    }
  }
}

@Composable
private fun Step4Documents(viewModel: PolytechnicViewModel) {
  val form by viewModel.formState.collectAsState()

  Card(
    modifier = Modifier.fillMaxWidth(),
    colors = CardDefaults.cardColors(containerColor = Color.White),
    shape = RoundedCornerShape(12.dp)
  ) {
    Column(modifier = Modifier.padding(16.dp)) {
      Text("Step 4: Upload Required Documents", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = NavyDark)
      Text("Attach scanned copies of all required credentials for verification.", fontSize = 11.sp, color = Color(0xFF64748B))

      Divider(modifier = Modifier.padding(vertical = 10.dp), color = Color(0xFFE2E8F0))

      DocUploadRow(
        title = "Recent Passport Photograph",
        sub = "Clear white background (Max 2MB)",
        uploaded = form.passportUploaded,
        onToggle = { viewModel.updateForm { f -> f.copy(passportUploaded = !f.passportUploaded) } }
      )

      DocUploadRow(
        title = "O'Level Result Slip (WAEC/NECO/NABTEB)",
        sub = "Original printout or certificate",
        uploaded = form.resultSlipUploaded,
        onToggle = { viewModel.updateForm { f -> f.copy(resultSlipUploaded = !f.resultSlipUploaded) } }
      )

      DocUploadRow(
        title = "Birth Certificate / Age Declaration",
        sub = "National Population Commission (NPC)",
        uploaded = form.birthCertUploaded,
        onToggle = { viewModel.updateForm { f -> f.copy(birthCertUploaded = !f.birthCertUploaded) } }
      )

      DocUploadRow(
        title = "Certificate of Origin / LGA Identification",
        sub = "Issued by candidate's Local Government",
        uploaded = form.lgaIdUploaded,
        onToggle = { viewModel.updateForm { f -> f.copy(lgaIdUploaded = !f.lgaIdUploaded) } }
      )

      DocUploadRow(
        title = "Previous Academic Certificates (for HND)",
        sub = "National Diploma result & IT completion letter",
        uploaded = form.prevCertUploaded,
        onToggle = { viewModel.updateForm { f -> f.copy(prevCertUploaded = !f.prevCertUploaded) } }
      )
    }
  }
}

@Composable
private fun DocUploadRow(
  title: String,
  sub: String,
  uploaded: Boolean,
  onToggle: () -> Unit
) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = 6.dp)
      .background(Color(0xFFF8FAFC), RoundedCornerShape(8.dp))
      .clickable { onToggle() }
      .padding(10.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Icon(
      imageVector = if (uploaded) Icons.Default.CheckCircle else Icons.Default.CloudUpload,
      contentDescription = null,
      tint = if (uploaded) Color(0xFF16A34A) else CrimsonRed,
      modifier = Modifier.size(24.dp)
    )
    Spacer(modifier = Modifier.width(10.dp))
    Column(modifier = Modifier.weight(1f)) {
      Text(title, fontWeight = FontWeight.SemiBold, fontSize = 12.sp, color = NavyDark)
      Text(sub, fontSize = 10.sp, color = Color(0xFF64748B))
    }
    Box(
      modifier = Modifier
        .background(if (uploaded) Color(0xFFDCFCE7) else Color(0xFFF1F5F9), RoundedCornerShape(4.dp))
        .padding(horizontal = 8.dp, vertical = 3.dp)
    ) {
      Text(
        text = if (uploaded) "Attached" else "Click to Upload",
        fontSize = 10.sp,
        fontWeight = FontWeight.Bold,
        color = if (uploaded) Color(0xFF166534) else NavyDark
      )
    }
  }
}

@Composable
private fun Step5ReviewAndSubmit(
  viewModel: PolytechnicViewModel,
  onNavigate: (String) -> Unit
) {
  val form by viewModel.formState.collectAsState()

  Card(
    modifier = Modifier.fillMaxWidth(),
    colors = CardDefaults.cardColors(containerColor = Color.White),
    shape = RoundedCornerShape(12.dp)
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      if (form.submissionSuccessAppNo != null) {
        // Success View
        Icon(
          imageVector = Icons.Default.CheckCircle,
          contentDescription = null,
          tint = Color(0xFF16A34A),
          modifier = Modifier.size(56.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
          text = "Application Submitted Successfully!",
          fontWeight = FontWeight.Black,
          fontSize = 17.sp,
          color = NavyDark
        )

        Text(
          text = "Your admission dossier has been logged into the Hope Polytechnic registry.",
          fontSize = 11.5.sp,
          color = Color(0xFF64748B),
          modifier = Modifier.padding(top = 2.dp, bottom = 12.dp)
        )

        // Unique Application Number Highlight
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .background(NavyDark, RoundedCornerShape(8.dp))
            .padding(14.dp),
          contentAlignment = Alignment.Center
        ) {
          Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("YOUR UNIQUE APPLICATION NUMBER", color = Color(0xFF94A3B8), fontSize = 10.sp, fontWeight = FontWeight.Bold)
            Text(
              text = form.submissionSuccessAppNo ?: "",
              color = Color.White,
              fontWeight = FontWeight.Black,
              fontSize = 20.sp,
              modifier = Modifier.padding(vertical = 4.dp)
            )
            Text("Please record this number to track your admission status.", color = Color(0xFFFFD700), fontSize = 10.sp)
          }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Action Buttons: Pay Application Fee, Track Application, Print Registration Slip
        Button(
          onClick = {
            viewModel.openPayment(
              applicationNumber = form.submissionSuccessAppNo ?: "HP/2026/00145",
              studentName = form.fullName,
              paymentTitle = "Admission Application Fee",
              amount = 10000.0
            )
          },
          colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF16A34A)),
          shape = RoundedCornerShape(8.dp),
          modifier = Modifier
            .fillMaxWidth()
            .height(46.dp)
            .testTag("pay_app_fee_btn")
        ) {
          Icon(imageVector = Icons.Default.CreditCard, contentDescription = null, modifier = Modifier.size(16.dp))
          Spacer(modifier = Modifier.width(6.dp))
          Text("Pay Application Fee (₦10,000)", fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          OutlinedButton(
            onClick = {
              viewModel.updateTrackingQuery(form.submissionSuccessAppNo ?: "")
              viewModel.trackApplication()
              onNavigate("track")
            },
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.weight(1f)
          ) {
            Icon(imageVector = Icons.Default.Search, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text("Track Status", fontSize = 12.sp)
          }

          Button(
            onClick = {
              viewModel.resetApplicationForm()
              onNavigate("home")
            },
            colors = ButtonDefaults.buttonColors(containerColor = NavyDark),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.weight(1f)
          ) {
            Text("Back to Home", fontSize = 12.sp)
          }
        }
      } else {
        // Review before submit
        Text("Application Dossier Preview", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = NavyDark)
        Text("Verify all entered information before final submission.", fontSize = 11.sp, color = Color(0xFF64748B))

        Divider(modifier = Modifier.padding(vertical = 8.dp), color = Color(0xFFE2E8F0))

        Column(modifier = Modifier.fillMaxWidth()) {
          ReviewRow("Candidate Name", form.fullName)
          ReviewRow("Programme Chosen", "${form.level} ${form.programme}")
          ReviewRow("Phone / WhatsApp", "${form.phone} / ${form.whatsappNumber}")
          ReviewRow("Email Address", form.email)
          ReviewRow("State / LGA", "${form.stateOfOrigin} / ${form.lga}")
          ReviewRow("Exam Details", "${form.examType} (${form.examYear}) - ${form.examNumber}")
          ReviewRow("Next of Kin", "${form.nextOfKinName} (${form.nextOfKinRelationship})")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
          onClick = { viewModel.submitApplication() },
          enabled = !form.isSubmitting,
          colors = ButtonDefaults.buttonColors(containerColor = CrimsonRed),
          shape = RoundedCornerShape(8.dp),
          modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .testTag("submit_admission_form_btn")
        ) {
          if (form.isSubmitting) {
            CircularProgressIndicator(color = Color.White, modifier = Modifier.size(20.dp))
          } else {
            Text("Confirm & Submit Admission Dossier", fontWeight = FontWeight.Bold, fontSize = 14.sp)
          }
        }
      }
    }
  }
}

@Composable
private fun ReviewRow(label: String, value: String) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = 3.dp)
  ) {
    Text(label, fontSize = 11.sp, fontWeight = FontWeight.Medium, color = Color(0xFF64748B), modifier = Modifier.width(120.dp))
    Text(if (value.isNotBlank()) value else "Not specified", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = NavyDark)
  }
}

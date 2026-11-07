package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "admission_applications")
data class AdmissionApplication(
  @PrimaryKey(autoGenerate = true)
  val id: Long = 0,
  val applicationNumber: String, // e.g. HP/2026/00101
  val userId: Long = 0,
  val academicSession: String = "2026/2027",

  // Personal Information
  val fullName: String,
  val dateOfBirth: String,
  val gender: String,
  val nationality: String = "Nigerian",
  val stateOfOrigin: String,
  val lga: String,
  val residentialAddress: String,
  val phone: String,
  val whatsappNumber: String,
  val email: String,

  // Academic Information
  val previousSchool: String,
  val examType: String, // WAEC, NECO, NABTEB
  val examNumber: String,
  val examYear: String,
  val subjectGrades: String, // JSON or formatted text e.g. "English: B2; Maths: A1; ..."
  val jambRegNo: String = "",
  val previousQualifications: String = "",
  val programme: String,
  val level: String, // ND or HND

  // Next of Kin
  val nextOfKinName: String,
  val nextOfKinRelationship: String,
  val nextOfKinPhone: String,
  val nextOfKinAddress: String,

  // Uploaded Document references/descriptions
  val passportPhotoName: String = "passport_photo.jpg",
  val resultSlipName: String = "olevel_result.pdf",
  val birthCertificateName: String = "birth_certificate.pdf",
  val lgaIdName: String = "lga_identification.pdf",
  val previousCertificateName: String = "",

  // Status & Tracking
  // Values: "Application Submitted", "Under Review", "Documents Required", "Approved", "Rejected"
  val status: String = "Application Submitted",
  val adminNotes: String = "Your application has been received and queued for academic verification.",
  val matricNumber: String = "",
  val isFeePaid: Boolean = false,
  val submittedAt: Long = System.currentTimeMillis(),
  val updatedAt: Long = System.currentTimeMillis()
)

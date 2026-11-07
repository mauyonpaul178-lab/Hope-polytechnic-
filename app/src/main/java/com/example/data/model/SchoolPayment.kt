package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "school_payments")
data class SchoolPayment(
  @PrimaryKey(autoGenerate = true)
  val id: Long = 0,
  val receiptNumber: String, // e.g. REC-HP-2026-4029
  val applicationNumber: String,
  val studentName: String,
  val paymentTitle: String, // Application Fee, Acceptance Fee, School Fees, ICT Levy
  val amount: Double,
  val academicSession: String = "2026/2027",
  val gateway: String, // "Paystack" or "Flutterwave"
  val channel: String = "Debit Card", // Debit Card, Bank Transfer, USSD
  val status: String = "Paid", // "Paid", "Pending", "Failed"
  val transactionReference: String,
  val timestamp: Long = System.currentTimeMillis()
)

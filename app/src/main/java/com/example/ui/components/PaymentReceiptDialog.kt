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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
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
import com.example.R
import com.example.data.model.SchoolPayment
import com.example.ui.theme.CrimsonRed
import com.example.ui.theme.NavyDark
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun PaymentReceiptDialog(
  payment: SchoolPayment,
  onDismiss: () -> Unit
) {
  val context = LocalContext.current
  val dateFormatted = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.US).format(Date(payment.timestamp))

  Dialog(onDismissRequest = onDismiss) {
    Surface(
      modifier = Modifier
        .fillMaxWidth(0.95f)
        .padding(vertical = 16.dp)
        .clip(RoundedCornerShape(16.dp)),
      color = Color.White,
      shadowElevation = 16.dp
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(20.dp)
      ) {
        // Top Bar
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.CheckCircle,
              contentDescription = null,
              tint = Color(0xFF16A34A),
              modifier = Modifier.size(22.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "Official e-Payment Receipt",
              fontWeight = FontWeight.Bold,
              fontSize = 15.sp,
              color = NavyDark
            )
          }
          IconButton(onClick = onDismiss, modifier = Modifier.testTag("close_receipt_btn")) {
            Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
          }
        }

        Divider(modifier = Modifier.padding(vertical = 6.dp), color = Color(0xFFE2E8F0))

        // Receipt Content
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
            // Receipt Header
            Row(
              modifier = Modifier.fillMaxWidth(),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Image(
                painter = painterResource(id = R.drawable.app_icon_hope_1793998303235),
                contentDescription = null,
                modifier = Modifier
                  .size(44.dp)
                  .clip(CircleShape)
              )
              Spacer(modifier = Modifier.width(8.dp))
              Column {
                Text(
                  text = "HOPE POLYTECHNIC, UYO",
                  fontWeight = FontWeight.Black,
                  fontSize = 13.sp,
                  color = NavyDark
                )
                Text(
                  text = "BURSARY & FINANCIAL SERVICES DEPARTMENT",
                  fontSize = 9.sp,
                  fontWeight = FontWeight.Bold,
                  color = CrimsonRed
                )
                Text(
                  text = "Mbak Itam III, Uyo, Akwa Ibom State",
                  fontSize = 9.5.sp,
                  color = Color(0xFF64748B)
                )
              }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Amount Highlight Box
            Box(
              modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFDCFCE7), RoundedCornerShape(8.dp))
                .border(1.dp, Color(0xFF86EFAC), RoundedCornerShape(8.dp))
                .padding(vertical = 10.dp, horizontal = 12.dp),
              contentAlignment = Alignment.Center
            ) {
              Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                  text = "AMOUNT PAID",
                  fontSize = 10.sp,
                  fontWeight = FontWeight.Bold,
                  color = Color(0xFF166534)
                )
                Text(
                  text = "₦${String.format(Locale.US, "%,.2f", payment.amount)}",
                  fontSize = 20.sp,
                  fontWeight = FontWeight.Black,
                  color = Color(0xFF14532D)
                )
                Text(
                  text = "PAYMENT SUCCESSFUL",
                  fontSize = 9.5.sp,
                  fontWeight = FontWeight.Bold,
                  color = Color(0xFF15803D)
                )
              }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Receipt Breakdown Rows
            ReceiptRow("Receipt No:", payment.receiptNumber)
            ReceiptRow("Payment Item:", payment.paymentTitle)
            ReceiptRow("Payer Name:", payment.studentName)
            ReceiptRow("Application No:", payment.applicationNumber)
            ReceiptRow("Session:", payment.academicSession)
            ReceiptRow("Gateway:", payment.gateway)
            ReceiptRow("Payment Channel:", payment.channel)
            ReceiptRow("Transaction Ref:", payment.transactionReference)
            ReceiptRow("Date & Time:", dateFormatted)

            Spacer(modifier = Modifier.height(12.dp))

            // Verified Badge
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text(
                text = "Automated e-Receipt generated by Hope Polytechnic Portal.",
                fontSize = 8.5.sp,
                color = Color(0xFF64748B),
                modifier = Modifier.width(180.dp)
              )
              Box(
                modifier = Modifier
                  .border(1.dp, Color(0xFF16A34A), RoundedCornerShape(4.dp))
                  .padding(horizontal = 6.dp, vertical = 2.dp)
              ) {
                Text(
                  text = "BURSARY PAID",
                  color = Color(0xFF16A34A),
                  fontWeight = FontWeight.Black,
                  fontSize = 8.sp
                )
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
                  "Hope Polytechnic Payment Receipt - ${payment.receiptNumber}"
                )
                putExtra(
                  Intent.EXTRA_TEXT,
                  "HOPE POLYTECHNIC PAYMENT RECEIPT\n" +
                      "Receipt No: ${payment.receiptNumber}\n" +
                      "Payer: ${payment.studentName}\n" +
                      "App No: ${payment.applicationNumber}\n" +
                      "Purpose: ${payment.paymentTitle}\n" +
                      "Amount: ₦${String.format(Locale.US, "%,.2f", payment.amount)}\n" +
                      "Reference: ${payment.transactionReference}\n" +
                      "Status: PAID (Success)\n" +
                      "Hope Polytechnic, Mbak Itam III, Uyo."
                )
              }
              context.startActivity(Intent.createChooser(shareIntent, "Share Payment Receipt"))
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
            Text("Done")
          }
        }
      }
    }
  }
}

@Composable
private fun ReceiptRow(label: String, value: String) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = 2.dp)
  ) {
    Text(
      text = label,
      fontSize = 11.sp,
      fontWeight = FontWeight.Medium,
      color = Color(0xFF64748B),
      modifier = Modifier.width(110.dp)
    )
    Text(
      text = value,
      fontSize = 11.sp,
      fontWeight = FontWeight.Bold,
      color = Color(0xFF1E293B)
    )
  }
}

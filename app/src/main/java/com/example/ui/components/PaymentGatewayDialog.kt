package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.ui.theme.CrimsonRed
import com.example.ui.theme.NavyDark
import com.example.ui.theme.NavyLight
import com.example.ui.viewmodel.PaymentCheckoutState
import java.util.Locale

@Composable
fun PaymentGatewayDialog(
  state: PaymentCheckoutState,
  onGatewaySelected: (String) -> Unit,
  onChannelSelected: (String) -> Unit,
  onPayClicked: () -> Unit,
  onDismiss: () -> Unit
) {
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
        // Modal Header
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Default.Lock, contentDescription = null, tint = NavyDark, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "Secured Payment Checkout",
              fontWeight = FontWeight.Bold,
              fontSize = 15.sp,
              color = NavyDark
            )
          }
          IconButton(onClick = onDismiss, modifier = Modifier.testTag("dismiss_payment_dialog")) {
            Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
          }
        }

        Divider(modifier = Modifier.padding(vertical = 6.dp), color = Color(0xFFE2E8F0))

        // Official Gateway Notice per instructions
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFFEF3C7), RoundedCornerShape(8.dp))
            .border(1.dp, Color(0xFFFCD34D), RoundedCornerShape(8.dp))
            .padding(10.dp)
        ) {
          Row(verticalAlignment = Alignment.Top) {
            Icon(
              imageVector = Icons.Default.Info,
              contentDescription = null,
              tint = Color(0xFFD97706),
              modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "Institutional Notice: Gateway is running in verified pre-live mode. Live commercial payments will route directly to Hope Polytechnic's official verified bank account.",
              fontSize = 10.5.sp,
              color = Color(0xFF92400E),
              lineHeight = 14.sp
            )
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Order Summary
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF8FAFC), RoundedCornerShape(8.dp))
            .padding(12.dp)
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text("Payment Item:", fontSize = 12.sp, color = Color(0xFF64748B))
            Text(state.paymentTitle, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = NavyDark)
          }
          Spacer(modifier = Modifier.height(4.dp))
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text("Applicant:", fontSize = 12.sp, color = Color(0xFF64748B))
            Text(state.studentName, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = NavyDark)
          }
          Spacer(modifier = Modifier.height(4.dp))
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text("Application No:", fontSize = 12.sp, color = Color(0xFF64748B))
            Text(state.applicationNumber, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = CrimsonRed)
          }
          Divider(modifier = Modifier.padding(vertical = 8.dp), color = Color(0xFFE2E8F0))
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text("Total Amount:", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = NavyDark)
            Text(
              "₦${String.format(Locale.US, "%,.2f", state.amount)}",
              fontSize = 18.sp,
              fontWeight = FontWeight.Black,
              color = Color(0xFF16A34A)
            )
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Select Payment Gateway
        Text(
          text = "Select Payment Gateway",
          fontSize = 12.sp,
          fontWeight = FontWeight.Bold,
          color = NavyDark
        )
        Spacer(modifier = Modifier.height(6.dp))
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          listOf("Paystack", "Flutterwave").forEach { gw ->
            val isSelected = state.selectedGateway == gw
            Box(
              modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(8.dp))
                .border(
                  BorderStroke(if (isSelected) 2.dp else 1.dp, if (isSelected) NavyDark else Color(0xFFCBD5E1)),
                  RoundedCornerShape(8.dp)
                )
                .background(if (isSelected) NavyDark.copy(alpha = 0.05f) else Color.White)
                .clickable { onGatewaySelected(gw) }
                .padding(vertical = 10.dp, horizontal = 12.dp)
                .testTag("gateway_option_$gw"),
              contentAlignment = Alignment.Center
            ) {
              Text(
                text = gw,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                color = if (isSelected) NavyDark else Color(0xFF64748B),
                fontSize = 13.sp
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Payment Channel
        Text(
          text = "Payment Method",
          fontSize = 12.sp,
          fontWeight = FontWeight.Bold,
          color = NavyDark
        )
        Spacer(modifier = Modifier.height(6.dp))

        listOf(
          Triple("Debit Card", Icons.Default.CreditCard, "Mastercard, Visa, Verve"),
          Triple("Bank Transfer", Icons.Default.AccountBalance, "Instant Dynamic Virtual Account"),
          Triple("USSD", Icons.Default.PhoneAndroid, "Direct Bank USSD Code (*737#, *894# etc.)")
        ).forEach { (channel, icon, desc) ->
          val isSelected = state.channel == channel
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(8.dp))
              .clickable { onChannelSelected(channel) }
              .padding(vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            RadioButton(
              selected = isSelected,
              onClick = { onChannelSelected(channel) }
            )
            Icon(imageVector = icon, contentDescription = null, tint = NavyDark, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Column {
              Text(channel, fontWeight = FontWeight.SemiBold, fontSize = 12.sp, color = NavyDark)
              Text(desc, fontSize = 10.sp, color = Color(0xFF64748B))
            }
          }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Pay Button
        Button(
          onClick = onPayClicked,
          enabled = !state.isProcessing,
          colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF16A34A)),
          shape = RoundedCornerShape(8.dp),
          modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .testTag("confirm_pay_now_btn")
        ) {
          if (state.isProcessing) {
            CircularProgressIndicator(color = Color.White, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text("Connecting to ${state.selectedGateway}...")
          } else {
            Icon(imageVector = Icons.Default.Lock, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              "Pay ₦${String.format(Locale.US, "%,.2f", state.amount)} via ${state.selectedGateway}",
              fontWeight = FontWeight.Bold
            )
          }
        }
      }
    }
  }
}

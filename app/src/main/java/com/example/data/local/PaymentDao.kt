package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.model.SchoolPayment
import kotlinx.coroutines.flow.Flow

@Dao
interface PaymentDao {
  @Query("SELECT * FROM school_payments ORDER BY timestamp DESC")
  fun getAllPayments(): Flow<List<SchoolPayment>>

  @Query("SELECT * FROM school_payments WHERE applicationNumber = :appNo ORDER BY timestamp DESC")
  fun getPaymentsByApplication(appNo: String): Flow<List<SchoolPayment>>

  @Query("SELECT * FROM school_payments WHERE receiptNumber = :receiptNo LIMIT 1")
  suspend fun getPaymentByReceipt(receiptNo: String): SchoolPayment?

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertPayment(payment: SchoolPayment): Long

  @Query("SELECT SUM(amount) FROM school_payments WHERE status = 'Paid'")
  suspend fun getTotalRevenue(): Double?
}

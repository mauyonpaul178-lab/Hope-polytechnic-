package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.AdmissionApplication
import kotlinx.coroutines.flow.Flow

@Dao
interface ApplicationDao {
  @Query("SELECT * FROM admission_applications ORDER BY submittedAt DESC")
  fun getAllApplications(): Flow<List<AdmissionApplication>>

  @Query("SELECT * FROM admission_applications WHERE applicationNumber = :appNo LIMIT 1")
  suspend fun getByApplicationNumber(appNo: String): AdmissionApplication?

  @Query("SELECT * FROM admission_applications WHERE applicationNumber = :appNo LIMIT 1")
  fun observeByApplicationNumber(appNo: String): Flow<AdmissionApplication?>

  @Query("SELECT * FROM admission_applications WHERE email = :email ORDER BY submittedAt DESC LIMIT 1")
  suspend fun getByEmail(email: String): AdmissionApplication?

  @Query("SELECT * FROM admission_applications WHERE email = :email ORDER BY submittedAt DESC LIMIT 1")
  fun observeByEmail(email: String): Flow<AdmissionApplication?>

  @Query("SELECT * FROM admission_applications WHERE userId = :userId ORDER BY submittedAt DESC LIMIT 1")
  suspend fun getByUserId(userId: Long): AdmissionApplication?

  @Query("SELECT * FROM admission_applications WHERE userId = :userId ORDER BY submittedAt DESC LIMIT 1")
  fun observeByUserId(userId: Long): Flow<AdmissionApplication?>

  @Query("SELECT * FROM admission_applications WHERE fullName LIKE '%' || :query || '%' OR applicationNumber LIKE '%' || :query || '%' ORDER BY submittedAt DESC")
  fun searchApplications(query: String): Flow<List<AdmissionApplication>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertApplication(application: AdmissionApplication): Long

  @Update
  suspend fun updateApplication(application: AdmissionApplication)

  @Query("UPDATE admission_applications SET status = :status, adminNotes = :notes, matricNumber = :matricNo, updatedAt = :timestamp WHERE id = :id")
  suspend fun updateStatus(id: Long, status: String, notes: String, matricNo: String, timestamp: Long = System.currentTimeMillis())

  @Query("UPDATE admission_applications SET isFeePaid = 1 WHERE applicationNumber = :appNo")
  suspend fun markFeePaid(appNo: String)

  @Query("SELECT COUNT(*) FROM admission_applications")
  suspend fun getCount(): Int

  @Query("SELECT COUNT(*) FROM admission_applications WHERE status = 'Approved'")
  suspend fun getApprovedCount(): Int

  @Query("SELECT COUNT(*) FROM admission_applications WHERE status = 'Under Review' OR status = 'Application Submitted'")
  suspend fun getPendingCount(): Int
}

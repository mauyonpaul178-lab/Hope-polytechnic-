package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.model.SchoolAnnouncement
import kotlinx.coroutines.flow.Flow

@Dao
interface AnnouncementDao {
  @Query("SELECT * FROM school_announcements ORDER BY isImportant DESC, timestamp DESC")
  fun getAllAnnouncements(): Flow<List<SchoolAnnouncement>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertAnnouncement(announcement: SchoolAnnouncement): Long

  @Query("DELETE FROM school_announcements WHERE id = :id")
  suspend fun deleteAnnouncement(id: Long)
}

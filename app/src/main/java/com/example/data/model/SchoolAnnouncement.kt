package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "school_announcements")
data class SchoolAnnouncement(
  @PrimaryKey(autoGenerate = true)
  val id: Long = 0,
  val title: String,
  val category: String, // Admissions, Academic Calendar, Facilities, General
  val content: String,
  val date: String,
  val isImportant: Boolean = false,
  val timestamp: Long = System.currentTimeMillis()
)

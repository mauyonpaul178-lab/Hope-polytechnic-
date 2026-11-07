package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.data.model.AdmissionApplication
import com.example.data.model.SchoolAnnouncement
import com.example.data.model.SchoolPayment
import com.example.data.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
  entities = [
    User::class,
    AdmissionApplication::class,
    SchoolPayment::class,
    SchoolAnnouncement::class
  ],
  version = 1,
  exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
  abstract fun userDao(): UserDao
  abstract fun applicationDao(): ApplicationDao
  abstract fun paymentDao(): PaymentDao
  abstract fun announcementDao(): AnnouncementDao

  companion object {
    @Volatile
    private var INSTANCE: AppDatabase? = null

    fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
      return INSTANCE ?: synchronized(this) {
        val instance = Room.databaseBuilder(
          context.applicationContext,
          AppDatabase::class.java,
          "hope_polytechnic_db"
        )
          .addCallback(DatabaseCallback(scope))
          .build()
        INSTANCE = instance
        instance
      }
    }

    private class DatabaseCallback(
      private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
      override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        INSTANCE?.let { database ->
          scope.launch(Dispatchers.IO) {
            seedDatabase(database)
          }
        }
      }

      suspend fun seedDatabase(db: AppDatabase) {
        val userDao = db.userDao()
        val applicationDao = db.applicationDao()
        val paymentDao = db.paymentDao()
        val announcementDao = db.announcementDao()

        // 1. Seed Admin User
        userDao.insertUser(
          User(
            email = "admin@hopepolytechnic.edu.ng",
            passwordHash = "admin123",
            fullName = "Engr. Dr. Anietie Udoh (Registrar)",
            phone = "08067560799",
            role = "ADMIN"
          )
        )

        // 2. Seed Sample Student User
        val studentUserId = userDao.insertUser(
          User(
            email = "emmanuel.bassey@gmail.com",
            passwordHash = "student123",
            fullName = "Bassey Emmanuel Edet",
            phone = "08023456789",
            role = "STUDENT",
            applicationNumber = "HP/2026/00142"
          )
        )

        // 3. Seed Approved Application
        applicationDao.insertApplication(
          AdmissionApplication(
            applicationNumber = "HP/2026/00142",
            userId = studentUserId,
            academicSession = "2026/2027",
            fullName = "Bassey Emmanuel Edet",
            dateOfBirth = "2005-04-12",
            gender = "Male",
            nationality = "Nigerian",
            stateOfOrigin = "Akwa Ibom State",
            lga = "Uyo",
            residentialAddress = "14 Wellington Bassey Way, Uyo",
            phone = "08023456789",
            whatsappNumber = "08023456789",
            email = "emmanuel.bassey@gmail.com",
            previousSchool = "Hope Polytechnic Academy Secondary, Uyo",
            examType = "WAEC",
            examNumber = "4031982741",
            examYear = "2024",
            subjectGrades = "English Language: B2, Mathematics: A1, Physics: B3, Chemistry: B2, Biology: C4",
            jambRegNo = "202640192837BA",
            previousQualifications = "SSCE Certificate",
            programme = "Computer Science",
            level = "ND",
            nextOfKinName = "Elder Edet Bassey",
            nextOfKinRelationship = "Father",
            nextOfKinPhone = "08033221100",
            nextOfKinAddress = "14 Wellington Bassey Way, Uyo, Akwa Ibom State",
            status = "Approved",
            adminNotes = "Congratulations! You have been granted provisional admission into National Diploma (ND) in Computer Science for the 2026/2027 session. Please proceed to pay acceptance fees.",
            matricNumber = "HP/ND/CSC/26/014",
            isFeePaid = true,
            submittedAt = System.currentTimeMillis() - 86400000 * 3
          )
        )

        // 4. Seed Pending Application for testing Admin review
        applicationDao.insertApplication(
          AdmissionApplication(
            applicationNumber = "HP/2026/00143",
            userId = 0,
            academicSession = "2026/2027",
            fullName = "Blessing Effiong Okon",
            dateOfBirth = "2003-09-18",
            gender = "Female",
            nationality = "Nigerian",
            stateOfOrigin = "Akwa Ibom State",
            lga = "Ikot Ekpene",
            residentialAddress = "22 Abak Road, Ikot Ekpene",
            phone = "08066638334",
            whatsappNumber = "08066638334",
            email = "blessing.effiong@yahoo.com",
            previousSchool = "Akwa Ibom State Polytechnic, Ikot Osurua",
            examType = "NECO",
            examNumber = "5092834192",
            examYear = "2022",
            subjectGrades = "English Language: A1, Mathematics: B2, Chemistry: A1, Biology: A1, Physics: B3",
            jambRegNo = "202611993428EF",
            previousQualifications = "National Diploma in SLT (Upper Credit)",
            programme = "Science Laboratory Technology",
            level = "HND",
            nextOfKinName = "Mrs. Sarah Okon",
            nextOfKinRelationship = "Mother",
            nextOfKinPhone = "08088899911",
            nextOfKinAddress = "22 Abak Road, Ikot Ekpene",
            status = "Under Review",
            adminNotes = "Academic committee currently evaluating National Diploma transcripts for HND admission eligibility.",
            isFeePaid = true,
            submittedAt = System.currentTimeMillis() - 86400000 * 1
          )
        )

        // 5. Seed Another Sample Application: Documents Required
        applicationDao.insertApplication(
          AdmissionApplication(
            applicationNumber = "HP/2026/00144",
            userId = 0,
            academicSession = "2026/2027",
            fullName = "Chibuike Franklin Nnamdi",
            dateOfBirth = "2004-11-05",
            gender = "Male",
            nationality = "Nigerian",
            stateOfOrigin = "Abia State",
            lga = "Aba South",
            residentialAddress = "50 Factory Road, Aba",
            phone = "08077712345",
            whatsappNumber = "08077712345",
            email = "chibuike.franklin@gmail.com",
            previousSchool = "Federal Government College, Ikot Ekpene",
            examType = "WAEC",
            examNumber = "4028371902",
            examYear = "2024",
            subjectGrades = "English: C4, Mathematics: B3, Physics: C5, Chemistry: C4, Tech Drawing: B2",
            programme = "Electrical/Electronics Engineering",
            level = "ND",
            nextOfKinName = "Mr. Kenneth Nnamdi",
            nextOfKinRelationship = "Brother",
            nextOfKinPhone = "08055566677",
            nextOfKinAddress = "50 Factory Road, Aba",
            status = "Documents Required",
            adminNotes = "Clear scanned copy of your Local Government Identification Letter is required. Please upload to complete verification.",
            isFeePaid = false,
            submittedAt = System.currentTimeMillis() - 86400000 * 2
          )
        )

        // 6. Seed Sample Payment Receipts
        paymentDao.insertPayment(
          SchoolPayment(
            receiptNumber = "REC-HP-2026-0041",
            applicationNumber = "HP/2026/00142",
            studentName = "Bassey Emmanuel Edet",
            paymentTitle = "Admission Application Fee",
            amount = 10000.0,
            academicSession = "2026/2027",
            gateway = "Paystack",
            channel = "Debit Card (Mastercard)",
            status = "Paid",
            transactionReference = "PSTK_HP_89374019283",
            timestamp = System.currentTimeMillis() - 86400000 * 3
          )
        )

        paymentDao.insertPayment(
          SchoolPayment(
            receiptNumber = "REC-HP-2026-0098",
            applicationNumber = "HP/2026/00142",
            studentName = "Bassey Emmanuel Edet",
            paymentTitle = "Provisional Acceptance Fee",
            amount = 25000.0,
            academicSession = "2026/2027",
            gateway = "Paystack",
            channel = "Bank Transfer",
            status = "Paid",
            transactionReference = "PSTK_HP_99182374112",
            timestamp = System.currentTimeMillis() - 86400000 * 1
          )
        )

        // 7. Seed Official Announcements
        announcementDao.insertAnnouncement(
          SchoolAnnouncement(
            title = "2026/2027 Admission Exercise Officially Commences",
            category = "Admissions",
            content = "The management of Hope Polytechnic, Mbak Itam III, Uyo, Akwa Ibom State wishes to inform the general public and prospective candidates that online admission applications for the 2026/2027 Academic Session for all National Diploma (ND) and Higher National Diploma (HND) programmes have formally commenced. Candidates are urged to apply early through this official portal.",
            date = "04 Nov 2026",
            isImportant = true
          )
        )

        announcementDao.insertAnnouncement(
          SchoolAnnouncement(
            title = "NBTE Resource Verification & Modern Workshop Commissioning",
            category = "Facilities",
            content = "The National Board for Technical Education (NBTE) has commended Hope Polytechnic on the newly commissioned ultra-modern Electrical/Electronics laboratories, high-speed ICT center, and Mechanical fabrication workshops at the Mbak Itam III campus, ensuring world-class practical technical training.",
            date = "01 Nov 2026",
            isImportant = false
          )
        )

        announcementDao.insertAnnouncement(
          SchoolAnnouncement(
            title = "Physical Screening and Credential Verification Schedule",
            category = "Academic Calendar",
            content = "All approved candidates are advised to prepare original copies of their O'Level results, birth certificates, Local Government Origin certificates, and 4 recent passport photographs for verification upon acceptance fee clearance.",
            date = "28 Oct 2026",
            isImportant = false
          )
        )
      }
    }
  }
}

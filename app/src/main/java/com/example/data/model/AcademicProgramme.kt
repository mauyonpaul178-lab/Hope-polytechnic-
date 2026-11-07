package com.example.data.model

data class AcademicProgramme(
  val id: Int,
  val name: String,
  val code: String,
  val faculty: String,
  val duration: String,
  val levels: List<String>, // "ND", "HND"
  val description: String,
  val requirements: String,
  val careerProspects: String
)

object ProgrammeData {
  val defaultProgrammes = listOf(
    AcademicProgramme(
      id = 1,
      name = "Computer Science",
      code = "CSC",
      faculty = "School of Applied Sciences & ICT",
      duration = "2 Years per level (Full-time)",
      levels = listOf("ND", "HND"),
      description = "Equipping students with modern software engineering, database design, cybersecurity, artificial intelligence, and network computing skills required in the tech ecosystem.",
      requirements = "5 O'Level Credit passes in WAEC/NECO/NABTEB including English Language, Mathematics, Physics, and any other two relevant science subjects (Chemistry, Biology/Agricultural Science, Further Maths, or Computer Studies).",
      careerProspects = "Software Developer, Systems Analyst, Cloud Engineer, Cybersecurity Specialist, Database Administrator, Tech Entrepreneur."
    ),
    AcademicProgramme(
      id = 2,
      name = "Science Laboratory Technology",
      code = "SLT",
      faculty = "School of Applied Sciences & ICT",
      duration = "2 Years per level (Full-time)",
      levels = listOf("ND", "HND"),
      description = "Comprehensive practical and theoretical training in chemical analysis, biological techniques, instrumentation, microbiological testing, and scientific industrial research.",
      requirements = "5 O'Level Credit passes in English Language, Mathematics, Chemistry, Biology/Agricultural Science, and Physics.",
      careerProspects = "Laboratory Technologist, Quality Assurance Officer, Research Scientist, Forensic Analyst, Industrial Chemist."
    ),
    AcademicProgramme(
      id = 3,
      name = "Electrical/Electronics Engineering",
      code = "EEE",
      faculty = "School of Engineering Technology",
      duration = "2 Years per level (Full-time)",
      levels = listOf("ND", "HND"),
      description = "Practical engineering education focusing on power systems, telecommunications, instrumentation and control, embedded electronics, and industrial automation.",
      requirements = "5 O'Level Credit passes in Mathematics, English Language, Physics, Chemistry, and one other technical/science subject (Technical Drawing, Further Maths, Metal Work).",
      careerProspects = "Electrical Engineer, Telecoms Specialist, Power Distribution Technician, Automation Specialist, Electronics Repair & Design Engineer."
    ),
    AcademicProgramme(
      id = 4,
      name = "Business Administration and Management",
      code = "BAM",
      faculty = "School of Business & Management Studies",
      duration = "2 Years per level (Full-time)",
      levels = listOf("ND", "HND"),
      description = "Cultivating future corporate leaders, managers, and innovators grounded in modern management theories, business analytics, organizational strategy, and entrepreneurship.",
      requirements = "5 O'Level Credit passes in English Language, Mathematics, Economics/Commerce, and any other two subjects from Principles of Accounts, Government, Business Methods, or Geography.",
      careerProspects = "Business Operations Manager, Human Resources Executive, Project Manager, Management Consultant, Corporate Administrator."
    ),
    AcademicProgramme(
      id = 5,
      name = "Accountancy",
      code = "ACC",
      faculty = "School of Business & Management Studies",
      duration = "2 Years per level (Full-time)",
      levels = listOf("ND", "HND"),
      description = "Professional accounting framework delivering competencies in financial reporting, corporate auditing, taxation, cost accounting, and digital financial management.",
      requirements = "5 O'Level Credit passes including English Language, Mathematics, Economics, Financial Accounting/Principles of Accounts, and one other social science subject.",
      careerProspects = "Chartered Accountant, Auditor, Tax Consultant, Financial Controller, Investment Analyst, Treasury Manager."
    ),
    AcademicProgramme(
      id = 6,
      name = "Public Administration",
      code = "PAD",
      faculty = "School of Business & Management Studies",
      duration = "2 Years per level (Full-time)",
      levels = listOf("ND", "HND"),
      description = "Training ethical, visionary administrators for public institutions, ministries, local government authorities, civil society organizations, and non-profits.",
      requirements = "5 O'Level Credit passes in English Language, Mathematics, Government/History, and two other relevant arts or social science subjects.",
      careerProspects = "Public Policy Analyst, Civil Service Administrator, Local Government Officer, Diplomatic Officer, Administrative Officer."
    ),
    AcademicProgramme(
      id = 7,
      name = "Mass Communication",
      code = "MAC",
      faculty = "School of Communication & Media Arts",
      duration = "2 Years per level (Full-time)",
      levels = listOf("ND", "HND"),
      description = "Modern media training spanning digital journalism, broadcasting, public relations, advertising, photojournalism, and multimedia content production.",
      requirements = "5 O'Level Credit passes in English Language, Mathematics, Literature in English, and any other two subjects from Government, Economics, History, or CRS/IRS.",
      careerProspects = "Journalist/Editor, Broadcast Presenter, PR Officer, Content Creator, Corporate Communications Manager, Media Producer."
    ),
    AcademicProgramme(
      id = 8,
      name = "Mechanical Engineering Technology",
      code = "MEC",
      faculty = "School of Engineering Technology",
      duration = "2 Years per level (Full-time)",
      levels = listOf("ND", "HND"),
      description = "Hands-on engineering training covering thermodynamics, machine design, computer-aided manufacturing (CAD/CAM), automotive technology, and fabrication.",
      requirements = "5 O'Level Credit passes in Mathematics, English Language, Physics, Chemistry, and any other technical or science subject.",
      careerProspects = "Mechanical Systems Engineer, Plant Maintenance Technician, CAD Designer, Automotive Engineer, Manufacturing Specialist."
    )
  )
}

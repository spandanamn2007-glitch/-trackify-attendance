# Trackify Attendance

An Android-based Student Attendance Management System built using modern Android development practices.

## 🚀 Features
- **User Authentication**: Secure login for Teachers and Students.
- **Teacher Dashboard**: 
  - Manage students (Add/Remove).
  - Mark daily attendance.
  - View attendance history.
- **Student Dashboard**: 
  - View personal attendance records.
- **Local Persistence**: Uses Room Database for offline-first data management.

## 🛠 Tech Stack
- **Language**: [Kotlin](https://kotlinlang.org/)
- **Architecture**: MVVM (Model-View-ViewModel)
- **Database**: [Room Persistence Library](https://developer.android.com/training/data-storage/room)
- **UI**: Material Design 3, ViewBinding, ConstraintLayout
- **Concurrency**: Kotlin Coroutines
- **Lifecycle Management**: ViewModel & LiveData

## 📸 UI Screenshots
*(Add your screenshots here)*
![Login Screen](app/src/main/res/mipmap-xxxhdpi/ic_launcher.webp)

## 📁 Project Structure
- `ui.activity`: Contains all the screen logic.
- `data`: Handles database entities, DAOs, and database configuration.
- `repository`: Mediates data flow between ViewModel and Data Sources.
- `viewmodel`: Manages UI-related data in a lifecycle-aware manner.

## ⚙️ Setup Instructions
1. Clone the repository:
   ```bash
   git clone https://github.com/spandanamn2007-glitch/-trackify-attendance.git
   ```
2. Open the project in **Android Studio**.
3. Build the project and run it on an emulator or physical device.

---
Developed as a robust solution for tracking student attendance efficiently.

# Akshara-Deepa Tutor - Android App

A self-study companion for 10th-grade (SSLC) students that transforms the syllabus into an engaging "Mission Map" with progress tracking, quizzes, and strength analysis.

## Project Structure

```
AksharaDeepaTutor/
├── app/
│   ├── src/main/
│   │   ├── java/com/aksharadeeptutor/
│   │   │   ├── data/
│   │   │   │   ├── model/          # Data entities (Subject, Chapter, Question, QuizAttempt)
│   │   │   │   ├── local/          # Room Database, DAOs
│   │   │   │   └── repository/     # Repository layer
│   │   │   ├── ui/
│   │   │   │   ├── syllabus/       # Syllabus Tracker screens
│   │   │   │   ├── quiz/           # Quiz Mode screens
│   │   │   │   ├── strengthmap/    # Strength Map (Radar Chart)
│   │   │   │   └── dailygoal/      # Daily Goal screens
│   │   │   ├── viewmodel/          # ViewModels
│   │   │   ├── utils/              # Utility classes
│   │   │   ├── receiver/           # Broadcast receivers
│   │   │   └── MainActivity.kt
│   │   ├── res/
│   │   │   ├── layout/             # XML layouts
│   │   │   ├── values/             # Strings, themes, colors
│   │   │   ├── drawable/           # Icons and drawables
│   │   │   ├── menu/               # Bottom navigation menu
│   │   │   ├── navigation/         # Navigation graph
│   │   │   └── xml/                # Backup rules
│   │   └── AndroidManifest.xml
│   └── build.gradle
├── build.gradle
├── settings.gradle
└── gradle.properties
```

## Features

### 1. Syllabus Tracker
- Hierarchical view of all chapters across Science, Mathematics, and Social Studies
- Mark chapters as Not Started, In Progress, or Completed
- Real-time progress bars that update instantly

### 2. Quiz Mode
- 5-question daily mock quizzes per chapter
- Countdown timer (5 minutes per quiz)
- Review answers section with explanations
- 50+ pre-loaded mock questions

### 3. Strength Map
- Radar/Spider Web chart showing subject-wise mastery
- Color-coded performance indicators
- Dynamic updates after every quiz

### 4. Daily Goal
- Daily reminder to complete at least one topic
- Streak counter for motivation
- Overall progress tracking

## Technical Stack

- **Language:** Kotlin
- **Architecture:** MVVM (Model-View-ViewModel)
- **Database:** Room (SQLite)
- **Navigation:** Jetpack Navigation Component
- **UI:** View Binding, Material Components
- **Charts:** MPAndroidChart
- **Async:** Kotlin Coroutines & Flow
- **Minimum SDK:** 24 (Android 7.0)
- **Target SDK:** 34 (Android 14)

## Offline Capability

The app works 100% offline once installed. All questions, syllabus data, and progress are stored locally using Room Database.

## Building the App

1. Open the project in Android Studio
2. Sync Gradle files
3. Build and run on an emulator or physical device

```bash
./gradlew assembleDebug
```

## Future Enhancements

- Regional language support (Kannada, Hindi, etc.)
- Cloud sync for progress backup
- Peer comparison (anonymous, opt-in)
- Parent/teacher dashboard
- Adaptive quiz difficulty
- Video explanations
- Gamification (badges, levels, rewards)

# TeaJournal

TeaJournal is a minimalist Android application built with Jetpack Compose, designed for tea enthusiasts to easily log, track, and review their tea tasting experiences. Keep a personal record of your favorite teas, their brewing details, and your ratings.

## Features

* **Log Tea Entries:** Easily add new tea entries with comprehensive details:
    * Tea Name
    * Tea Type (e.g., Green, Black, Oolong, Herbal)
    * Brewing Temperature (°C)
    * Steep Time (minutes)
    * Personal Rating (1-5 scale)
    * Additional Notes
* **View Tea List:** Browse all your logged tea entries in a scrollable list.
* **Edit Existing Entries:** Tap on any tea in the list to modify its details.
* **Delete Tea Entries:** Remove tea entries you no longer wish to keep.
* **Intuitive User Interface:** A clean and responsive UI built with Jetpack Compose.
* **Local Data Persistence:** All your tea data is securely stored on your device using Room Database.

## Screenshots

* **Add New Tea:**
<img width="270" height="600" alt="Add New Tea" src="https://github.com/user-attachments/assets/efaf7a0c-ef10-4747-b566-d9a228f945d1" />

* **List:**
<img width="270" height="600" alt="Catalog" src="https://github.com/user-attachments/assets/d77efcd5-c8fd-4865-a7de-311e5e27440e" />

* **Delete:**
<img width="270" height="600" alt="Delete" src="https://github.com/user-attachments/assets/7252ea26-4fa1-470f-a809-514a0a21bccc" />

* **Deleted:**
<img width="270" height="600" alt="Deleted" src="https://github.com/user-attachments/assets/cef3e1fd-e901-4ead-81e5-45a5c3001281" />

* **Update:**
<img width="270" height="600" alt="Edit Tea" src="https://github.com/user-attachments/assets/5b969f1d-aaae-4b5f-91ec-d036e257681f" />

* **Updated:**
<img width="270" height="600" alt="Updated" src="https://github.com/user-attachments/assets/2044e7d8-92f0-4e65-a3a4-64c71a3b29b1" />

---

## Technologies Used

* **Kotlin:** The primary programming language for Android development.
* **Jetpack Compose:** Android's modern toolkit for building native UI.
* **Jetpack Navigation Compose:** For managing in-app navigation.
* **Room Persistence Library:** For robust local database storage.
* **Kotlin Coroutines & Flow:** For asynchronous operations and reactive data streams.
* **Material Design 3:** For a modern and consistent look and feel.

## Architecture

TeaJournal follows a clean architecture pattern, primarily leveraging the Android Jetpack components to ensure maintainability, testability, and scalability.

* **UI Layer (Compose):** Composable functions handle the presentation and user interaction (e.g., `MainActivity`, `TeaListScreen`, `AddEditTeaScreen`).
* **ViewModel Layer:** `TeaViewModel` acts as a bridge between the UI and the data layer, managing UI-related data and logic, and exposing `Flow` streams for reactive updates.
* **Data Layer:**
    * **Repository (`TeaRepository`):** Abstracts the data sources and provides a clean API for the ViewModel. It's responsible for fetching data and resolving data conflicts (e.g., from network or local database).
    * **DAO (`TeaDao`):** Defines the methods for interacting with the Room database, providing operations like insert, update, delete, and query.
    * **Database (`AppDatabase`):** The Room database implementation, responsible for creating and managing the SQLite database, defining entities (`Tea`), and providing access to DAOs.

## Setup and Installation

To run this project locally, ensure you have Android Studio Hedgehog | 2023.2.1 or newer (or a compatible version that supports Kotlin and Jetpack Compose) installed.

1.  **Clone the repository:**
    ```bash
    git clone [https://github.com/your-username/TeaJournal.git](https://github.com/your-username/TeaJournal.git) # Replace with your actual repository URL
    cd TeaJournal
    ```
2.  **Open in Android Studio:**
    Open the `TeaJournal` project in Android Studio.
3.  **Sync Gradle:**
    Android Studio will automatically sync the Gradle files. If not, manually sync by clicking `File > Sync Project with Gradle Files`.
4.  **Build and Run:**
    Select an Android emulator or connect a physical device and click the 'Run' button (green triangle icon) in Android Studio.

**Minimum SDK:** API 21 (Android 5.0 Lollipop)
**Target SDK:** API 35
**Compile SDK:** API 35

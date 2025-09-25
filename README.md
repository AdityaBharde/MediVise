# MediVise

## Description
MediVise is an Android application designed to be a comprehensive health and wellness companion. It provides users with tools to monitor their mental health, analyze medical reports, and receive support through an AI-powered chatbot. The app is built using modern Android development technologies, including Jetpack Compose for the user interface, Firebase for authentication, and Room for local data storage.

---

## Key Features
* **Authentication:** Secure user login and registration using Google Sign-In.
* **Dashboard:** A central hub providing a quick health summary and access to all of the app's features.
* **AI Chatbot:** An intelligent chatbot, powered by the Gemini API, provides users with a conversational interface to ask health-related questions and receive support.

---

## Technologies Used
* **Kotlin:** The primary programming language for building the application.
* **Jetpack Compose:** Used for building the entire user interface of the app.
* **Firebase Authentication:** For handling user authentication, including Google Sign-In.
* **Room:** For creating and managing the local database to store chat history.
* **Google's Gemini API:** Powers the AI chatbot, providing intelligent and context-aware responses.
* **Coil:** For efficient image loading.
* **Material Design 3:** The app follows the latest Material Design guidelines for a modern and intuitive user experience.

---

## Setup Instructions
To set up and run this project locally, you will need to do the following:

1.  **Clone the repository:**
    ```bash
    git clone [https://github.com/adityabharde/medivise.git](https://github.com/adityabharde/medivise.git)
    ```
2.  **Open in Android Studio:** Open the cloned project in Android Studio.
3.  **Set up Firebase:**
    * Create a new Firebase project.
    * Add an Android app to your Firebase project with the package name `com.example.medivise`.
    * Download the `google-services.json` file and place it in the `app` directory.
4.  **Get a Gemini API Key:**
    * Obtain a Gemini API key from the Google AI Studio.
    * Create a `local.properties` file in the root of the project.
    * Add your Gemini API key to the `local.properties` file:
        ```
        GEMINI_API_KEY="YOUR_API_KEY"
        ```
5.  **Build and run the app:** Build and run the application on an Android emulator or a physical device.

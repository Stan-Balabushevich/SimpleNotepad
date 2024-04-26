# Simple Notepad App

Simple Notepad is a lightweight Android application that allows users to create, edit, and manage text notes. This app is built using Jetpack Compose for the user interface, Koin for dependency injection, 
the MVVM architectural pattern, and Room for local database storage. The codebase is designed following clean architecture principles.

## Features
- Create, edit, and delete text notes.
- Organize notes by category or tag.
- Search and filter notes for easy retrieval.
- Offline support with Room database.

## Technologies and Frameworks
- **Jetpack Compose**: A modern toolkit for building native UI on Android.
- **Koin DI**: A lightweight dependency injection framework for Kotlin.
- **MVVM**: A design pattern for separating concerns in the application.
- **Clean Architecture**: A structure promoting separation of concerns and maintainability.
- **Room**: A persistence library for SQLite databases in Android.

## Project Structure
- `app`: Contains the main app code, including the UI components and ViewModels.
- `data`: Includes data sources and the Room database setup.
- `domain`: Contains the business logic and use cases.
- `di`: Handles the Koin dependency injection setup.

## Setup and Installation
1. Clone the repository to your local machine:
   ```bash
   git clone https://github.com/your-username/simple-notepad.git
2. Open the project in Android Studio.
3. Build and run the app on your Android device or emulator.

## Contributing

We welcome contributions to the project! If you'd like to contribute, please follow these steps:

1. Fork the repository.
2. Create a new branch with a descriptive name.
3. Make your changes and commit them.
4. Submit a pull request with a clear description of your changes.

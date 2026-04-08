# Personal Event PLanner
A mobile Android application that allows users to create, manage, and track personal events. The app uses a ROOM database for persistent storage and Jetpack Navigation Component for seamless screen navigation.

# Features
- Add, edit, and delete events
- View a list of events sorted by date
- Persistent local storage using Room Database
- Navigation between screens using Jetpack Navigation Component
- Clean and responsive UI

# Tech Stack
- Language: Java
- Software: Android Studio
- Database: Room (SQLite abstraction)
- UI: XML Layouts
- Navigation: Jetpack Navigation Component
- Lifecycle-aware components: LiveData, ViewModel

# Architecture
The app follows an Model-View-ViewModel architecture:

- Model: Room Database (Entity, DAO, Database)
- ViewModel: Handles UI-related data and survives configuration changes
- View: Activities/Fragments observing LiveData

Navigation between screens is handled using the Jetpack Navigation Component, which simplifies fragment transactions and back stack management.

# Database
The app uses Room for local data persistence.

## Entity
- Event
  - id (Primary Key)
  - title
  - location
  - category
  - date
  - time

## DAO
- Insert event
- Update event
- Delete event
- Retrieve all events (sorted by date)

### Example Query
- @Query("SELECT * FROM events ORDER BY date DESC"
- LiveData<List<Event>> getAllEvents();

# Navigation Flow
Mention your navigation graph.

# Navigation
The app uses Jetpack Navigation Component with a single-activity, multiple fragment architecture

### Screens:
- Main Activity (Host fragments)
- Event List Fragment (Home)
- Add/Edit Event Fragment

Navigation is managed using a NavController and navigation graph XML.

## Installation
1. Clone the repository:
   git clone https://github.com/vhudson04/SIT305_PersonalEventPlannerApp

2. Open the project in Android Studio

3. Build and run the app on an emulator or physical device
- Event List Fragment (Home)
- Add/Edit Event Fragment
- Event Detail Fragment (if applicable)

# Author
Vincent Hudson
GitHub: https://github.com/vhudson04

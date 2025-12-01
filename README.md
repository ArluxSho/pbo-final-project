# PomoCat - Productivity Timer with Gamification

PomoCat is a JavaFX-based desktop application that combines the Pomodoro time-management technique with virtual pet gamification. The application is designed to increase productivity and user focus by providing motivation through rewards and visual interaction.

## Key Features

### 1. Pomodoro Timer System

* **Flexible Focus Modes:** Users can choose their preferred focus duration (e.g., 25 minutes for deep focus, 50 minutes for extended sessions, or a 10-second trial mode).
* **Intuitive Controls:** Includes Start, Pause, and Reset buttons for managing the session.
* **Visual Indicators:** A large and clear timer display with a thematic background.

### 2. Task Management (To-Do List)

* **Task Recording:** Users can add a list of tasks they want to complete before starting a focus session.
* **Data Persistence:** Tasks are automatically saved to a local file (`tasks.txt`) to ensure that data remains available even after the application is closed.
* **Focus Selection:** Users can select a specific task to be shown as the primary focus target on the timer screen.

### 3. Gamification & Virtual Pet

* **Pomo Cat Character:** A virtual cat that accompanies the user during focus sessions.
* **Coins & Happiness System:** Completing a timer session awards coins and increases the pet's happiness level.
* **Shop Interaction:** Coins can be exchanged to feed or play with Pomo Cat.
* **Interaction Logic:** Interaction buttons (Feed/Play) are disabled automatically while the timer is running to avoid distractions.

## Project Structure

This project is built using Object-Oriented Programming (OOP) principles with a Model-View-Controller (MVC) architecture.

* **Model:** Handles data logic (Timer, Pet, Task).
* **View:** Manages the user interface using FXML.
* **Controller:** Connects business logic with the user interface.

## Installation Requirements

* Java Development Kit (JDK) version 17 or newer.
* Maven for dependency management.
* An IDE with JavaFX support (IntelliJ IDEA, Eclipse, or NetBeans).

## How to Run the Application

1. Clone this repository to your local machine.
2. Open the project using your preferred IDE.
3. Ensure all Maven dependencies are fully downloaded.
4. Run the `Main.java` file located at `src/main/java/com/pomodoro/Main.java`.

## Usage

### Home Page

* Click the start button to proceed to the preparation page.

### Task Page

* Enter a task name and press the add button.
* Choose a focus duration from the dropdown menu.
* Click next to proceed to the timer page.

### Timer Page

* Press **Start** to begin the countdown. Pet interaction buttons will be locked.
* Stay focused until the timer finishes.
* After completion, receive coin rewards.
* Use coins to interact with Pomo Cat during break sessions.

## Contributors

* **Aprilia Wulandari   L0124040** 
* **Aisyah Nurul S      L0124085**
* **Calista Salsabila   L0124092** 

## License

This project was created as part of the Object-Oriented Programming course requirements.

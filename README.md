# Mentor Application

The Mentor Application is a JavaFX-based application designed to facilitate the interaction between mentors and mentees. This application allows users to manage their profiles, including username, password, and additional role-specific details, and enables mentors to set their availability for mentoring sessions. The system provides a structured approach to view management, allowing easy navigation between different screens.

## Features

- **Profile Management**: Both mentors and mentees can manage their personal profile, including username, password, and specific details relevant to their roles.
- **Dynamic View Management**: The application uses a `ViewManager` class to handle switching between different views seamlessly.
- **Role-based Views**: The application displays different information based on the user's role, whether they are a mentee or a mentor.
- **Global Styling**: The application uses a global CSS file to maintain a consistent look and feel across all views.

## Technologies

- **JavaFX**: A framework for building graphical user interfaces in Java.
- **Event-Driven Architecture**: The application uses event handling to trigger view changes when necessary.
- **Singleton Pattern**: The application uses the Singleton pattern to manage the current user throughout the application lifecycle.

## Key Components

### `ViewManager`

The `ViewManager` class is responsible for managing different views in the application. It allows scenes to be added and displayed on the main stage of the application. The class also listens for view change events and updates the stage accordingly.

### `ManagedView Interface`

The `ManagedView` interface is implemented by views that can be managed by the `ViewManager`. It ensures that views can trigger view change events and implement an `onShowHook()` method, which is called when the view is shown.

### `ProfilePageView`

The `ProfilePageView` is a central component where users can manage their profiles. Depending on the user's role (mentor or mentee), the page displays different sections for updating the username, password, and additional details such as the mentee's CV or the mentor's availability.

### `ViewChangeEvent`

The `ViewChangeEvent` class is used to represent a request to change the current view. It is triggered whenever a view needs to be updated.

## Core Concepts

### `updateByRole` Method

The `updateByRole` method in the `ProfilePageView` class is responsible for dynamically adjusting the content of the profile page based on the user's role. When a user logs in, the method checks if the current user is a **mentor**, **mentee**, or **admin**, and updates the profile page accordingly.

- **Mentees**: The mentee's profile includes details like CV text and username. Mentees can update their profile information such as their CV and password.
  
- **Mentors**: The mentor's profile displays fields such as username, password, and their availability for mentoring sessions. Mentors can modify their profile details and set their availability times.
  
- **Admins**: The admin profile includes additional management features for overseeing the application. Admins can view the user roles and manage user accounts, overseeing mentee and mentor profiles, as well as controlling access and permissions within the system.

This method ensures that only the relevant fields are displayed for each role, offering a personalized experience based on the user’s profile. The inclusion of the admin role provides extra functionality for managing the overall user base, supporting administrative actions within the platform.

### Singleton Pattern: `CurrentUserSingleton`

The application uses the Singleton pattern to manage the **current user** throughout the application's lifecycle. The `CurrentUserSingleton` class ensures that the application always has a single instance of the current user, and it provides easy access to the user’s information (e.g., username, role, password, etc.) across different parts of the application.

This approach avoids having to pass the user information explicitly across multiple views and controllers, simplifying the management of the user's state.

The `CurrentUserSingleton` class ensures that there is only one instance of the current user at any given time:

```java
public class CurrentUserSingleton {
    private static CurrentUserSingleton instance;
    public User user;

    private CurrentUserSingleton() {}

    public static CurrentUserSingleton getInstance() {
        if (instance == null) {
            instance = new CurrentUserSingleton();
        }
        return instance;
    }
}

### Singleton Pattern: `CurrentSessionsSingleton`

The `CurrentSessionsSingleton` class implements the Singleton design pattern, ensuring that there is only a single instance of the current session in the application. This class is responsible for managing the active sessions for the users, tracking which user is logged in, and maintaining session-specific data.

By using the Singleton pattern, the `CurrentSessionsSingleton` ensures that the session data is accessible globally throughout the application, eliminating the need to repeatedly pass session information between components. This allows for centralized management of session data, improving code readability and maintainability.

The `CurrentSessionsSingleton` can be used to track multiple user sessions or the current session of the application, depending on the use case. It ensures consistency and helps manage data such as the user's login state or session-related activities across different views.


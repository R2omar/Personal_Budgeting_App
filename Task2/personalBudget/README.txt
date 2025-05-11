# Personal Finance Management System - README

## Overview
This is a Java-based Personal Finance Management System that helps users track their income, expenses, budgets, and set financial reminders. The system provides a console-based interface for managing personal finances with persistent data storage.

## Files Included

### Core Classes
1. **Main.java** - The entry point of the application, handling user authentication and main menu navigation
2. **AuthController.java** - Handles user signup and login processes
3. **User.java & UserDatabase.java** - User model and database operations

### Financial Modules
4. **Budget-related Files**:
   - Budget.java - Budget model
   - BudgetController.java - Budget business logic
   - BudgetDatabase.java - Budget data persistence
   - BudgetMenuView.java - Budget user interface

5. **Expense-related Files**:
   - Expense.java - Expense model
   - ExpenseController.java - Expense business logic
   - ExpenseDataBase.java - Expense data persistence
   - ExpenseMenuView.java - Expense user interface

6. **Income-related Files**:
   - Income.java - Income model
   - IncomeController.java - Income business logic
   - IncomeRepository.java - Income data persistence
   - IncomeMenueView.java - Income user interface

7. **Reminder-related Files**:
   - Reminder.java - Reminder model
   - ReminderController.java - Reminder business logic
   - ReminderDataBase.java - Reminder data persistence
   - ReminderMenuView.java - Reminder user interface
   - ReminderNotification.java - Notification implementation

### Supporting Interfaces
8. **INotification.java & IReminderNotification.java** - Notification interfaces
9. **NotificationController.java** - Simple notification handler

## Development Tools Used

### Programming Language
- Java 

### Build Tools
- Can be built with any Java IDE (Eclipse, IntelliJ IDEA, etc.).

### Data Storage
- Flat file storage (text files) for all persistent data:
  - Users.txt - Stores user accounts
  - Budget.txt - Stores budget data
  - expenses.txt - Stores expense records
  - income.txt - Stores income records
  - reminders.txt - Stores reminder data
  - users_notifications/ - Folder for user notification logs

### Key Java Features Used
- Object-Oriented Programming principles
- File I/O operations
- Date/time handling
- Interfaces and implementations
- Console-based user interface

## How to Run
1. Compile all Java files: `javac *.java`
2. Run the main class: `java Main`
3. Follow the on-screen instructions to register or login
4. Use the menu system to access different financial management features


## Design Patterns
- MVC (Model-View-Controller) pattern separation
- Repository pattern for data access
- Observer pattern for notifications

The application provides a comprehensive solution for personal finance management with persistent storage and reminder capabilities, all through an easy-to-use console interface.
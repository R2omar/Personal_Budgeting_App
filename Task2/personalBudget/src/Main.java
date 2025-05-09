import java.util.*;
import java.io.*;

/**
 * The main application class for the Personal Finance Management System.
 * Handles user authentication and provides the main menu navigation
 * to various financial management features.
 * @author Nada Samir,Omar Sayed,Kholod Ahmed
 */
public class Main {
    /**
     * The main entry point for the application.
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        Scanner input13 = new Scanner(System.in);
        List<User> Users = new ArrayList<>();

        // Load existing users from file
        try {
            File file = new File("Users.txt");
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                String[] parts = line.split(", ");
                if (parts.length == 4) {
                    String username = parts[0].trim();
                    String password = parts[1].trim();
                    String email = parts[2].trim();
                    int id = Integer.parseInt(parts[3].trim());
                    Users.add(new User(username, password, email, id));
                }
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("User data file not found. A new one will be created upon registration.");
        }

        User loggedInUser = null;

        // Main application loop
        while (true) {
            System.out.println("\n1- Sign Up");
            System.out.println("2- Login");
            System.out.print("Enter your choice (1 or 2): ");
            int number = input13.nextInt();
            input13.nextLine();

            if (number == 1) {
                AuthController.handleSignup(Users, input13);
            } else if (number == 2) {
                loggedInUser = AuthController.handleLogin(Users, input13);
            }

            // Main menu for authenticated users
            while (loggedInUser != null) {
                System.out.println("\n====== Main Menu ======");
                System.out.println("1. Tracking Income");
                System.out.println("2. Budgeting & Analysis");
                System.out.println("3. Set Reminders");
                System.out.println("4. Expense Tracking");
                System.out.println("5. Log Out");
                System.out.print("Choose an option (1-5): ");

                int choice = Integer.parseInt(input13.nextLine());

                switch (choice) {
                    case 1: {
                        // Income management submenu
                        boolean income = true;
                        IncomeRepository db = new IncomeRepository("income.txt");
                        IncomeController controller = new IncomeController(db);
                        IncomeMenueView menu = new IncomeMenueView(controller, loggedInUser);
                        while (income) {
                            System.out.println("\n------- Income Manager Menu ----");
                            System.out.println("1. Add Income");
                            System.out.println("2. View Incomes");
                            System.out.println("3. Update Income");
                            System.out.println("4. Delete Income");
                            System.out.println("5. Exit");
                            System.out.print("Choose an option (1-5): ");

                            choice = Integer.parseInt(input13.nextLine());

                            switch (choice) {
                                case 1: { menu.addIncome(); break; }
                                case 2: { menu.viewIncomes(); break; }
                                case 3: { menu.updateIncome(); break; }
                                case 4: { menu.deleteIncome(); break; }
                                case 5: {
                                    income = false;
                                    System.out.println("Exiting...");
                                    break;
                                }
                                default: System.out.println("Invalid option. Please try again.");
                            }
                        }
                        break;
                    }
                    case 2: {
                        // Budget management submenu
                        boolean budget = true;
                        NotificationController notify = new NotificationController();
                        BudgetDatabase db = new BudgetDatabase("Budget.txt");
                        BudgetController controller = new BudgetController(db, notify);
                        BudgetMenuView menu = new BudgetMenuView(controller, loggedInUser);
                        while (budget) {
                            System.out.println("\n------- Budget Manager Menu ----");
                            System.out.println("1. Add budget");
                            System.out.println("2. View budget");
                            System.out.println("3. Update budget");
                            System.out.println("4. Delete budget");
                            System.out.println("5. updateRemaining");
                            System.out.println("6. Exit");
                            System.out.print("Choose an option (1-6): ");

                            choice = Integer.parseInt(input13.nextLine());

                            switch (choice) {
                                case 1: { menu.addBudget(); break; }
                                case 2: { menu.viewBudgets(); break; }
                                case 3: { menu.updateBudget(); break; }
                                case 4: { menu.deleteBudget(); break; }
                                case 5: { menu.updateremainingBudgets(); break; }
                                case 6: {
                                    budget = false;
                                    System.out.println("Exiting...");
                                    break;
                                }
                                default: System.out.println("Invalid option. Please try again.");
                            }
                        }
                        break;
                    }
                    case 3: {
                        // Reminder management submenu
                        boolean reminder = true;
                        ReminderDataBase db = new ReminderDataBase("reminders.txt");
                        ReminderController controller = new ReminderController(db);
                        ReminderMenuView menu = new ReminderMenuView(controller, loggedInUser);
                        while (reminder) {
                            System.out.println("\n====== Reminder Manager Menu ======");
                            System.out.println("1. Set Reminder");
                            System.out.println("2. View Reminders");
                            System.out.println("3. Update Reminder");
                            System.out.println("4. Delete Reminder");
                            System.out.println("5. Exit");
                            System.out.print("Choose an option (1-5): ");

                            choice = Integer.parseInt(input13.nextLine());

                            switch (choice) {
                                case 1: { menu.addReminder(); break; }
                                case 2: { menu.viewReminders(); break; }
                                case 3: { menu.updateReminder(); break; }
                                case 4: { menu.deleteReminder(); break; }
                                case 5: {
                                    reminder = false;
                                    System.out.println("Exiting...");
                                    break;
                                }
                                default: System.out.println("Invalid option. Please try again.");
                            }
                        }
                        break;
                    }
                    case 4: {
                        // Expense management submenu
                        boolean expense = true;
                        ExpenseDataBase db = new ExpenseDataBase("expenses.txt");
                        ExpenseController controller = new ExpenseController(db);
                        ExpenseMenuView menu = new ExpenseMenuView(controller, loggedInUser);
                        while (expense) {
                            System.out.println("\n====== Expense Manager Menu ======");
                            System.out.println("1. Add Expense");
                            System.out.println("2. View Expenses");
                            System.out.println("3. Update Expense");
                            System.out.println("4. Delete Expense");
                            System.out.println("5. Exit");
                            System.out.print("Choose an option (1-5): ");

                            choice = Integer.parseInt(input13.nextLine());

                            switch (choice) {
                                case 1: { menu.addExpense(); break; }
                                case 2: { menu.viewExpenses(); break; }
                                case 3: { menu.updateExpense(); break; }
                                case 4: { menu.deleteExpense(); break; }
                                case 5: {
                                    expense = false;
                                    System.out.println("Exiting...");
                                    break;
                                }
                                default: System.out.println("Invalid option. Please try again.");
                            }
                        }
                        break;
                    }
                    case 5: {
                        loggedInUser = null;
                        System.out.println("Exiting...");
                        break;
                    }
                    default: {
                        System.out.println("Invalid option. Please try again.");
                    }
                }
            }
        }
    }
}
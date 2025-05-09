import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 * A class responsible for providing a menu-based user interface
 * to manage expenses for a logged-in user. It supports adding,
 * viewing, updating, and deleting expenses through console input.
 * @author Omar Sayed
 */
public class ExpenseMenuView {
    private final ExpenseController controller;
    private final User loggedInUser;
    private final Scanner scanner;
    private final SimpleDateFormat dateFormat;

    /**
     * Constructs the menu view with an associated controller and logged-in user.
     *
     * @param controller    the controller handling business logic for expenses
     * @param loggedInUser  the currently logged-in user
     */
    public ExpenseMenuView(ExpenseController controller, User loggedInUser) {
        this.controller = controller;
        this.loggedInUser = loggedInUser;
        this.scanner = new Scanner(System.in);
        this.dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }

    /**
     * Prompts the user to input new expense details and adds it to the system.
     */
    public void addExpense() {
        try {
            System.out.print("Enter Amount: ");
            double amount = Double.parseDouble(scanner.nextLine());

            System.out.print("Enter Category: ");
            String category = scanner.nextLine();

            System.out.print("Enter Date (yyyy-MM-dd): ");
            Date date = dateFormat.parse(scanner.nextLine());

            System.out.print("Enter Payment Method: ");
            String paymentMethod = scanner.nextLine();

            System.out.print("Enter Description: ");
            String description = scanner.nextLine();

            Expense expense = new Expense(loggedInUser.getUserID(), amount, category, date, paymentMethod, description);
            boolean success = controller.addExpense(expense);

            System.out.println(success ? "Expense added successfully." : "Failed to add expense.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Displays all expenses for the currently logged-in user.
     */
    public void viewExpenses() {
        try {
            List<Expense> expenses = controller.listExpenses(loggedInUser.getUserID());
            if (expenses.isEmpty()) {
                System.out.println("No expenses found for user.");
            } else {
                System.out.println("\nExpenses:");
                for (int i = 0; i < expenses.toArray().length; i++) {
                    String eString = "Expense{" +
                            "expenseId=" + (i + 1) +
                            ", amount=" + expenses.get(i).getAmount() +
                            ", category='" + expenses.get(i).getCategory() + '\'' +
                            ", date=" + dateFormat.format(expenses.get(i).getDate()) +
                            ", paymentMethod='" + expenses.get(i).getPaymentMethod() + '\'' +
                            ", description='" + expenses.get(i).getDescription() + '\'' +
                            '}';
                    System.out.println(eString);
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Prompts the user to select and update an existing expense.
     */
    public void updateExpense() {
        try {
            List<Expense> expenses = controller.listExpenses(loggedInUser.getUserID());
            if (expenses.isEmpty()) {
                System.out.println("No expenses found for this user.");
                return;
            }

            viewExpenses();

            System.out.print("Enter Expense ID to update: ");
            int expenseId = Integer.parseInt(scanner.nextLine());

            Expense found = null;
            for (Expense e : expenses) {
                if (e.getExpenseId() == expenses.get(expenseId - 1).getExpenseId()) {
                    found = e;
                    break;
                }
            }

            if (found == null) {
                System.out.println("Expense ID not found.");
                return;
            }

            boolean updating = true;
            while (updating) {
                System.out.println("\nWhat would you like to update?");
                System.out.println("1. Amount");
                System.out.println("2. Category");
                System.out.println("3. Date");
                System.out.println("4. Payment Method");
                System.out.println("5. Description");
                System.out.println("6. Finish Updating");
                System.out.print("Choose an option (1-6): ");

                String choice = scanner.nextLine();

                switch (choice) {
                    case "1": {
                        System.out.print("Enter New Amount: ");
                        double amount = Double.parseDouble(scanner.nextLine());
                        found.setAmount(amount);
                        break;
                    }
                    case "2": {
                        System.out.print("Enter New Category: ");
                        String category = scanner.nextLine();
                        found.setCategory(category);
                        break;
                    }
                    case "3": {
                        System.out.print("Enter New Date (yyyy-MM-dd): ");
                        Date date = dateFormat.parse(scanner.nextLine());
                        found.setDate(date);
                        break;
                    }
                    case "4": {
                        System.out.print("Enter New Payment Method: ");
                        String paymentMethod = scanner.nextLine();
                        found.setPaymentMethod(paymentMethod);
                        break;
                    }
                    case "5": {
                        System.out.print("Enter New Description: ");
                        String description = scanner.nextLine();
                        found.setDescription(description);
                        break;
                    }
                    case "6": {
                        updating = false;
                        break;
                    }
                    default:
                        System.out.println("Invalid option. Try again.");
                }
            }

            boolean updated = controller.updateExpense(found);
            System.out.println(updated ? "Expense updated successfully." : "Failed to update expense.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Prompts the user to select an expense to delete.
     */
    public void deleteExpense() {
        try {
            List<Expense> expenses = controller.listExpenses(loggedInUser.getUserID());
            if (expenses.isEmpty()) {
                System.out.println("No expenses found for this user.");
                return;
            }

            viewExpenses();

            System.out.print("Enter Expense ID to delete: ");
            int expenseId = Integer.parseInt(scanner.nextLine());

            boolean deleted = controller.deleteExpense(
                    loggedInUser.getUserID(),
                    (expenseId <= expenses.toArray().length) ? expenses.get(expenseId - 1).getExpenseId() : -1
            );

            System.out.println(deleted ? "Expense deleted." : "Failed to delete expense.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

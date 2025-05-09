import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class BudgetMenuView {

    private final BudgetController controller;
    private final User loggedInUser;
    private final Scanner scanner;
    private final SimpleDateFormat dateFormat;

    public BudgetMenuView(BudgetController controller, User loggedInUser) {
        this.controller = controller;
        this.loggedInUser = loggedInUser;
        this.scanner = new Scanner(System.in);
        this.dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }

    public void addBudget() {
        try {
            System.out.print("Enter Category: ");
            String category = scanner.nextLine();

            System.out.print("Enter Amount: ");
            double amount = Double.parseDouble(scanner.nextLine());

            System.out.print("Enter Start Date (yyyy-MM-dd): ");
            Date startDate = dateFormat.parse(scanner.nextLine());

            System.out.print("Enter End Date (yyyy-MM-dd): ");
            Date endDate = dateFormat.parse(scanner.nextLine());

            Budget budget = new Budget(loggedInUser.getUserID(), category, startDate, endDate, amount);
            boolean success = controller.setBudget(budget);

            System.out.println(success ? "Budget added successfully." : "Failed to add budget.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void viewBudgets() {
        try {
            List<Budget> budgets = controller.getBudgetSummary(loggedInUser.getUserID());
            if (budgets.isEmpty()) {
                System.out.println("No budgets found.");
                return;
            }

            System.out.println("\nBudgets:");
            for (Budget b : budgets) {
                String bString = "Budget{" +
                        "budgetId=" + b.getBudgetId() +  // Use actual budgetId
                        ", category='" + b.getCategory() + '\'' +
                        ", amount=" + b.getAmount() +
                        ", remaining=" + b.getRemaining() +
                        ", startDate=" + dateFormat.format(b.getStartDate()) +
                        ", endDate=" + dateFormat.format(b.getEndDate()) +
                        '}';
                System.out.println(bString);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void updateBudget() {
        try {
            List<Budget> budgets = controller.getBudgetSummary(loggedInUser.getUserID());
            if (budgets.isEmpty()) {
                System.out.println("No budgets to update.");
                return;
            }

            viewBudgets();

            System.out.print("Enter Budget ID to update: ");
            int id = Integer.parseInt(scanner.nextLine());

            if (id < 1 || id > budgets.size()) {
                System.out.println("Invalid ID.");
                return;
            }

            Budget found = budgets.get(id - 1);
            boolean updating = true;

            while (updating) {
                System.out.println("\nChoose field to update:");

                System.out.println("1. Category");
                System.out.println("2. Amount");
                System.out.println("3. Start Date");
                System.out.println("4. End Date");
                System.out.println("5. Finish");
                System.out.print("Option: ");
                String option = scanner.nextLine();

                switch (option) {

                    case "1":
                        System.out.print("New Category: ");
                        found.setCategory(scanner.nextLine());
                        break;
                    case "2":
                        System.out.print("New Amount: ");
                        double amount = Double.parseDouble(scanner.nextLine());
                        found.setAmount(amount);
                        break;
                    case "3":
                        System.out.print("New Start Date (yyyy-MM-dd): ");
                        found.setStartDate(dateFormat.parse(scanner.nextLine()));
                        break;
                    case "4":
                        System.out.print("New End Date (yyyy-MM-dd): ");
                        found.setEndDate(dateFormat.parse(scanner.nextLine()));
                        break;
                    case "5":
                        updating = false;
                        break;
                    default:
                        System.out.println("Invalid choice.");
                }
            }

            boolean updated = controller.updateBudget(found);
            System.out.println(updated ? "Budget updated." : "Update failed.");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void deleteBudget() {
        try {
            List<Budget> budgets = controller.getBudgetSummary(loggedInUser.getUserID());
            if (budgets.isEmpty()) {
                System.out.println("No budgets found for this user.");
                return;
            }

            viewBudgets();

            System.out.print("Enter Budget ID to delete: ");
            int budgetId = Integer.parseInt(scanner.nextLine());


            boolean deleted = controller.deleteBudget(loggedInUser.getUserID(),
                    (budgetId >= 1 && budgetId <= budgets.size()) ? budgets.get(budgetId - 1).getBudgetId() : -1);

            System.out.println(deleted ? "Budget deleted." : "Failed to delete budget.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

















    public void updateremainingBudgets() {
        try {
            List<Budget> budgets = controller.getBudgetSummary(loggedInUser.getUserID());
            if (budgets.isEmpty()) {
                System.out.println("No budgets to update.");
                return;
            }

            viewBudgets();

            System.out.print("Enter Budget ID to update: ");
            int id = Integer.parseInt(scanner.nextLine());

            if (id < 1 || id > budgets.size()) {
                System.out.println("Invalid ID.");
                return;
            }

            Budget found = budgets.get(id - 1);

            System.out.print("Enter amount to deduct: ");
            double amount = Double.parseDouble(scanner.nextLine());

            double newRemaining = found.getRemaining() - amount;
            if (newRemaining < 0) {
                System.out.println("Error: Insufficient remaining balance.");
                return;
            }

            found.setRemaining(newRemaining);

            boolean updated = controller.updateremainingBudgets(found);
            System.out.println(updated ? "Remaining updated." : "Update failed.");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

}





















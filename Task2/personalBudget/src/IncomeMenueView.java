import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;


public class IncomeMenueView {

    private final IncomeController controller;
    private final User loggedInUser;
    private final Scanner scanner;
    private final SimpleDateFormat dateFormat;

    public IncomeMenueView(IncomeController controller,User loggedInUser) {
        this.controller = controller;
        this.loggedInUser = loggedInUser;
        this.scanner = new Scanner(System.in);
        this.dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }

    public void addIncome() {
        try {
            System.out.print("Enter Amount: ");
            double amount = Double.parseDouble(scanner.nextLine());

            System.out.print("Enter Source: ");
            String Source = scanner.nextLine();

            System.out.print("Enter Date (yyyy-MM-dd): ");
            Date dateReceived = dateFormat.parse(scanner.nextLine());



            System.out.print("Enter Description: ");
            String description = scanner.nextLine();

            Income income = new Income(loggedInUser.getUserID(), amount,Source, dateReceived,  description);
            boolean success = controller.addIncome(income);

            if (success) {
                System.out.println("Income added successfully.");
            } else {
                System.out.println("Failed to add Income.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void viewIncomes() {
        try {
            List<Income> Incomes = controller.listIncome(loggedInUser.getUserID());
            if (Incomes.isEmpty()) {
                System.out.println("No incomes found for user.");
            } else {
                System.out.println("\nIncomes:");
                for (int i = 0;i < Incomes.toArray().length;i++) {
                    String eString = "Income{" +
                            "incomeId=" + (i + 1) +
                            ", amount=" + Incomes.get(i).getAmount() +
                            ", Source='" + Incomes.get(i).getSource() + '\'' +
                            ", date=" + dateFormat.format(Incomes.get(i).getdateReceived()) +
                            ", description='" + Incomes.get(i).getDescription() + '\'' +
                            '}';
                    System.out.println(eString);
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void updateIncome() {
        try {
            List<Income> Incomes = controller.listIncome(loggedInUser.getUserID());
            if (Incomes.isEmpty()) {
                System.out.println("No Incomes found for this user.");
                return;
            }

            viewIncomes(); // Display all expenses for reference

            System.out.print("Enter Income ID to update: ");
            int IncomeId = Integer.parseInt(scanner.nextLine());

            Income found = null;
            for (Income e : Incomes) {
                if (e.getIncomeId() == Incomes.get(IncomeId - 1).getIncomeId()) {
                    found = e;
                    break;
                }
            }

            if (found == null) {
                System.out.println("Income ID not found.");
                return;
            }

            boolean updating = true;
            while (updating) {
                System.out.println("\nWhat would you like to update?");
                System.out.println("1. Amount");
                System.out.println("2. Source");
                System.out.println("3. dateReceived");
                System.out.println("4. Description");
                System.out.println("5. Finish Updating");
                System.out.print("Choose an option (1-5): ");

                String choice = scanner.nextLine();

                switch (choice) {
                    case "1" : {
                        System.out.print("Enter New Amount: ");
                        double amount = Double.parseDouble(scanner.nextLine());
                        found.setAmount(amount);
                        break;
                    }
                    case "2" : {
                        System.out.print("Enter New Source: ");
                        String Source = scanner.nextLine();
                        found.setSource(Source);
                        break;
                    }
                    case "3" : {
                        System.out.print("Enter New Date (yyyy-MM-dd): ");
                        Date dateReceived = dateFormat.parse(scanner.nextLine());
                        found.setdateReceived(dateReceived);
                        break;
                    }

                    case "4" : {
                        System.out.print("Enter New Description: ");
                        String description = scanner.nextLine();
                        found.setDescription(description);
                        break;
                    }
                    case "5" : {updating = false;break;}
                    default : System.out.println("Invalid option. Try again.");
                }
            }

            boolean updated = controller.updateIncome(found);
            System.out.println(updated ? "Income updated successfully." : "Failed to update Income.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    public void deleteIncome() {
        try {
            List<Income> Incomes = controller.listIncome(loggedInUser.getUserID());
            if (Incomes.isEmpty()) {
                System.out.println("No Incomes found for this user.");
                return;
            }

            viewIncomes();

            System.out.print("Enter Incomes ID to delete: ");
            int IncomeId = Integer.parseInt(scanner.nextLine());

            boolean deleted = controller.deleteIncome(loggedInUser.getUserID(), (IncomeId <= Incomes.toArray().length)?Incomes.get(IncomeId - 1).getIncomeId():-1);
            System.out.println(deleted ? "Income deleted." : "Failed to delete Income.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

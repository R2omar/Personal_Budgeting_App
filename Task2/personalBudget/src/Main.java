import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) {
        Scanner input13 = new Scanner(System.in);
        List<User> NS = new ArrayList<>();
        try {
            File file = new File("NS.txt");
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                String[] parts = line.split(", ");
                if (parts.length == 4) {
                    String username = parts[0].trim();
                    String password = parts[1].trim();
                    String email = parts[2].trim();
                    int id = Integer.parseInt(parts[3].trim());
                    NS.add(new User(username, password, email, id));
                }
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("User data file not found. A new one will be created upon registration.");
        }

        User loggedInUser = null;

        while (true) {
            System.out.println("\n1- Sign Up");
            System.out.println("2- Login");
            System.out.print("Enter your choice (1 or 2): ");
            int number = input13.nextInt();
            input13.nextLine();

            if (number == 1) {
                AuthController.signup(NS, input13);
            } else if (number == 2) {
                loggedInUser = AuthController.login(NS, input13);
            }

            while (loggedInUser != null) {
                System.out.println("\n====== Main Menu ======");
                System.out.println("1. Tracking Income");
                System.out.println("2. Budgeting & Analysis ");
                System.out.println("3. Set Reminders ");
                System.out.println("4. Expense Tracking ");
                System.out.println("5. Log Out");
                System.out.print("Choose an option (1-5): ");

                int choice = Integer.parseInt(input13.nextLine());

                switch (choice) {
                    case 1 : {break;}
                    case 2 : {break;}
                    case 3 : {break;}
                    case 4 : {
                        boolean expense = true;
                        ExpenseDataBase db = new ExpenseDataBase("expenses.txt");
                        ExpenseController controller = new ExpenseController(db);
                        ExpenseMenuView menu = new ExpenseMenuView(controller,loggedInUser);
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
                                case 1  : {menu.addExpense();break;}
                                case 2  : {menu.viewExpenses();break;}
                                case 3  : {menu.updateExpense();break;}
                                case 4  : {menu.deleteExpense();break;}
                                case 5  : {
                                    expense = false;
                                    System.out.println("Exiting...");
                                    break;
                                }
                                default : System.out.println("Invalid option. Please try again.");
                            }
                        }
                        break;
                    }
                    case 5 :{
                        loggedInUser = null;
                        System.out.println("Exiting...");
                        break;
                    }
                    default :{ System.out.println("Invalid option. Please try again.");}
                }

            }
        }
    }
}

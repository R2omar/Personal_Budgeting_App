import java.util.List;
import java.util.Scanner;

class AuthController {
    public static boolean handleSignup(List<User> Users, Scanner input13) {
        System.out.print("Enter your username: ");
        String name1 = input13.nextLine();

        System.out.print("Enter your password (at least 8 characters): ");
        String pass = input13.nextLine();
        if (pass.length() < 8) {
            System.out.println("Password must be at least 8 characters.");
            return false;
        }

        System.out.print("Enter your email: ");
        String email = input13.nextLine();

        for (User user : Users) {
            if (user.getUsername().equals(name1)) {
                System.out.println("This username is already taken.");
                return false;
            }
            if (user.getPassword().equals(pass)) {
                System.out.println("This password is already in use. Choose another.");
                return false;
            }
            if (user.getEmail().equals(email)) {
                System.out.println("This email is already registered.");
                return false;
            }
        }

        int LID = 0;
        for (User user : Users) {
            if (user.getUserID() > LID) {
                LID = user.getUserID();
            }
        }

        int id = LID + 1;
        User newUser = new User(name1, pass, email, id);
        Users.add(newUser);
        UserDatabase.Save1(newUser);
        System.out.println("Registration successful! Your ID is: " + id);
        return true;
    }

    public static User handleLogin(List<User> Users, Scanner input13) {
        System.out.print("Enter your username: ");
        String name2 = input13.nextLine();
        System.out.print("Enter your password: ");
        String pass2 = input13.nextLine();

        User cur = null;
        for (User user : Users) {
            if (user.getUsername().equals(name2)) {
                cur = user;
                break;
            }
        }

        if (cur == null) {
            System.out.println("Username not found.");
            return null;
        }

        if (cur.getPassword().equals(pass2)) {
            System.out.println("Welcome back, " + name2 + "!");
            return cur;
        } else {
            System.out.println("Incorrect password.");
            System.out.println("1- Try again");
            System.out.println("2- Reset your password");
            int choice = input13.nextInt();
            input13.nextLine();

            if (choice == 1) {
                System.out.print("Re-enter your password: ");
                String pass3 = input13.nextLine();
                if (cur.getPassword().equals(pass3)) {
                    System.out.println("Welcome back, " + name2 + "!");
                    return cur;
                } else {
                    System.out.println("Still incorrect.");
                }
            } else if (choice == 2) {
                System.out.print("Enter your registered email: ");
                String em = input13.nextLine();
                User userToUpdate = UserDatabase.findByEmail(em, Users);
                if (userToUpdate != null) {
                    System.out.print("Enter new password (at least 8 characters): ");
                    String newPass = input13.nextLine();
                    if (newPass.length() < 8) {
                        System.out.println("Password too short. Update failed.");
                        return null;
                    }
                    UserDatabase.updatePassword(userToUpdate, newPass);
                } else {
                    System.out.println("Email not found.");
                }
            } else {
                System.out.println("Invalid choice. Please select 1 or 2.");
            }
        }
        return null;
    }
}

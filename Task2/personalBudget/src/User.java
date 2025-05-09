/**
 * Represents a user in the system with a username, password, email, and user ID.
 * @author Nada Samir
 */
class User {

    /** The user's username used for login. */
    private String username;

    /** The user's password. */
    private String password;

    /** The user's email address. */
    private String email;

    /** The unique identifier for the user. */
    private int userID;

    /**
     * Constructs a new User with the specified details.
     *
     * @param username the user's username
     * @param password the user's password
     * @param email the user's email address
     * @param userID the unique ID assigned to the user
     */
    public User(String username, String password, String email, int userID) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.userID = userID;
    }

    /**
     * Gets the user's username.
     *
     * @return the username
     */
    public String getUsername() { return username; }

    /**
     * Gets the user's password.
     *
     * @return the password
     */
    public String getPassword() { return password; }

    /**
     * Gets the user's email address.
     *
     * @return the email
     */
    public String getEmail() { return email; }

    /**
     * Gets the user's unique ID.
     *
     * @return the user ID
     */
    public int getUserID() { return userID; }

    /**
     * Sets a new password for the user.
     *
     * @param pass the new password
     */
    public void setPassword(String pass) { this.password = pass; }
}

class User {
    private String username;
    private String password;
    private String email;
    private int userID;

    public User(String username, String password, String email, int userID) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.userID = userID;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getEmail() { return email; }
    public int getUserID() { return userID; }
    public void setPassword(String pass) { this.password = pass; }
}
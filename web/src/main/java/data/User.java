package data;

/**
 * Data user. Contains useful information
 * such as the username and the token
 *
 * @author GriffinBabe
 */
public class User {

    private String username;
    private String token;

    public User(String username, String token) {
        this.username = username;
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public String getToken() {
        return token;
    }
}

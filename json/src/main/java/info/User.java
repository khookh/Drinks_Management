package info;

/**
 * Data user. Contains useful information
 * such as the username and the token
 *
 * @author GriffinBabe
 */
public class User {

    private int id;
    private String username;
    private String token;

    public User(int id, String username, String token) {
        this.id = id;
        this.username = username;
        this.token = token;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getToken() {
        return token;
    }
}

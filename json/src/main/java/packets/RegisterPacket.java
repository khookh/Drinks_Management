package packets;

import info.Gender;

/**
 * Register packet is sent by the user to the server
 * to register a new account. This packets awaits
 * for a {@link ResponseRegisterPacket}.
 *
 * @author GriffinBabe
 */
public class RegisterPacket extends JSONPacket {

    private String username;
    private String email;
    private String password;

    private int age;

    // Expressed in kgs
    private int weight;

    private Gender gender;

    public RegisterPacket(String username, String email, String password, int age, int weight, Gender gender) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.age = age;
        this.weight = weight;
        this.gender = gender;
        super.packetName = this.getClass().getSimpleName();
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public int getAge() {
        return age;
    }

    public int getWeight() {
        return weight;
    }

    public Gender getGender() {
        return gender;
    }
}

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class User implements Serializable {

    private String firstName;
    private String lastName;
    private String email;
    private Set<String> roles = new HashSet<>();
    private Set<String> tels = new HashSet<>();

    User() {
    }

    public User(String firstName, String lastName, String email, Set<String> role, Set<String> tel) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.roles = role;
        this.tels = tel;
    }

    String getFirstName() {
        return firstName;
    }

    void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    private String getLastName() {
        return lastName;
    }

    void setLastName(String lastName) {
        this.lastName = lastName;
    }

    private String getEmail() {
        return email;
    }

    void setEmail(String email) {
        this.email = email;
    }

    public Set<String> getRole() {
        return roles;
    }

    void setRole(String role) {

        if (roles.size() < 3) {
            roles.add(role);
        }

    }

    public Set<String> getTel() {
        return tels;
    }

    public void rolTelClean() {
        this.roles.clear();
        this.tels.clear();
    }

    void setTel(String tel) {

        if (tels.size() < 3) {
            tels.add(tel);
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getFirstName().equals(user.getFirstName()) &&
                getLastName().equals(user.getLastName()) &&
                getEmail().equals(user.getEmail()) &&
                roles.equals(user.roles) &&
                tels.equals(user.tels);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirstName(), getLastName(), getEmail(), roles, tels);
    }

    @Override
    public String toString() {
        return "Пользователь: " +
                "Имя-" + firstName +
                ", Фамилия-" + lastName +
                ", Еmail-" + email +
                ", Роль-" + roles +
                ", Телефон-" + tels ;
    }
}

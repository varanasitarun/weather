package studentteacher.model;

//import jakarta.persistence.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    @Version  // Add this for optimistic locking
    private Integer version;


    // Getters & Setters
    public Integer getVersion() { return version; }
    public void setVersion(Integer version) { this.version = version; }

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> roles;

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

//    public Set<String> getRoles() { return roles; }
//    public void setRoles(Set<String> roles) { this.roles = roles; }
}

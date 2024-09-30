package www.com.ksm_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User implements UserDetails, Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id")
  private int user_id;
  @Column(name = "first_name", nullable = false, length = 100)
  private String first_name;
  @Column(name = "last_name", nullable = false, length = 100)
  private String last_name;
  @Column(name = "username", nullable = false, unique = true, length = 200)
  private String username;
  @Column(name = "password", nullable = false)
  private String password;
  @Column(name = "phone_number", nullable = false, length = 100)
  private String phone_number;

  @ManyToMany(cascade = CascadeType.PERSIST)
  @JoinTable(
          name = "user_role",
          joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"),
          inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id"))
  private Set<Role> roles;

//  @OneToMany(mappedBy = "user")
//  private List<Token> tokens;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.getRoles().stream().map(r -> new SimpleGrantedAuthority(r.getName())).toList();
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}

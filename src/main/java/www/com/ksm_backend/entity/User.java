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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_")
public class User implements UserDetails {

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

  @Enumerated(EnumType.STRING)
  private Role role;

  @OneToMany(mappedBy = "user")
  private List<Token> tokens;


  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
//    return Collections.singletonList(
//            new SimpleGrantedAuthority(role.name())
//    );
    return role.getAuthorities();
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
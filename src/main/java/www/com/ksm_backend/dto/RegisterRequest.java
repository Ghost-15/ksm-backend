package www.com.ksm_backend.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import www.com.ksm_backend.entity.Role;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RegisterRequest {
  private String first_name;
  private String last_name;
  private String username;
  //    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private String password;
  private String phone_number;
  private Role role;
}

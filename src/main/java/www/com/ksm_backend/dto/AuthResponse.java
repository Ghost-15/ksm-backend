package www.com.ksm_backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import www.com.ksm_backend.entity.Role;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
  @JsonProperty("accessToken")
  private String access_token;
  @JsonProperty("role")
  private Role role;
}

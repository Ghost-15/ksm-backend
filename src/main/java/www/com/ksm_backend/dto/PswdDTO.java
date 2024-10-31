package www.com.ksm_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PswdDTO {
    private int userId;
    private String username;
    private String currentPswd;
    private String newPswd;
    private String comfirmPswd;
}

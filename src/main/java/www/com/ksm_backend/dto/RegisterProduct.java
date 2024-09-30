package www.com.ksm_backend.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RegisterProduct {
    private String name;
    private String category;
    private String description;
    private int conditionnement;
    private String coloris;
    private int prix;
}

package www.com.ksm_backend.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RegisterProduct {
    @NotBlank
    @NotNull
    private String name;
    @NotBlank
    @NotNull
    private String category;
    @NotBlank
    @NotNull
    private String sousCategory;
    @NotBlank
    @NotNull
    private String description;
    @NotBlank
    @NotNull
    private int conditionnement;
    @NotBlank
    @NotNull
    private String coloris;
    @NotBlank
    @NotNull
    @Min(value = 1)
    private int prix;
}

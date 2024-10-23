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
    @NotNull
    @NotBlank
    private String name;
    @NotNull
    @NotBlank
    private String category;
//    @NotNull
//    @NotBlank
//    private String sousCategory;
    @NotNull
    @NotBlank
    private int conditionnement;
    @NotNull
    @NotBlank
    private String coloris;
    @NotNull
    @NotBlank
    @Min(value = 1)
    private int prix;
    @NotNull
    @NotBlank
    private String picture_url;
    @NotNull
    @NotBlank
    private String pdf_url;
    @NotNull
    @NotBlank
    private String description;
}

package sanchez.bankingapi.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Request to create user")
public record CreateUserRequestDto (

        @NotNull
        @NotBlank
        @Schema(description = "User first name", example = "Ivan")
        String firstName,

        @NotNull
        @NotBlank
        @Schema(description = "User second name", example = "Ivanov")
        String secondName,

        @NotBlank
        @Schema(description = "User third name", example = "Ivanovich")
        String thirdName,

        @NotBlank
        @NotNull
        @Email
        @Schema(description = "User email", example = "IvanPetrov@gmail.com")
        String email,

        @NotBlank
        @NotNull
        @Schema(description = "User first name", example = "IvanIvan1212!")
        String password ){
}

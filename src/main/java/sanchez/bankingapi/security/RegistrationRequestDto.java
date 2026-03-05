package sanchez.bankingapi.security;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Registration request")
public record RegistrationRequestDto(

        @NotNull
        @NotBlank
        @Schema(description = "User`s first name",
                example = "Sanchez")
        String firstName,

        @NotNull
        @NotBlank
        @Schema(description = "User`s second name",
                example = "Pops")
        String secondName,

        @NotBlank
        @Schema(description = "User`s third name",
                example = "Sergeevich")
        String thirdName,

        @NotNull
        @Email
        @Schema(description = "User`s email",
                example = "boss@gmail.com")
        String email,

        @NotNull
        @Schema(description = "User`s password",
                example = "!pisyapopa228")
        String password
) {
}

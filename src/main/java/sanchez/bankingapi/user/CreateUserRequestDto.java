package sanchez.bankingapi.user;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateUserRequestDto (
        @NotNull
        @NotBlank
        String firstName,
        @NotNull
        @NotBlank
        String secondName,

        @NotBlank
        String thirdName,
        @NotBlank
        @NotNull
        @Email
        String email,
        @NotBlank
        @NotNull
        String password ){
}

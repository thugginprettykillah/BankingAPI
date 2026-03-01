package sanchez.bankingapi.user;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record CreateUserRequestDto (
        @NotNull
        String firstName,
        @NotNull
        String secondName,

        String thirdName,
        @NotNull
        @Email
        String email,
        @NotNull
        String password ){
}

package sanchez.bankingapi.user;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;

public record UserResponseDto (
    @NotNull
    Long id,
    @NotNull
    String firstName,
    @NotNull
    String secondName,
    @Null
    String thirdName,
    @NotNull
    String email
) {
}

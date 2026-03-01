package sanchez.bankingapi.user;


public record UserResponseDto (

    Long id,

    String firstName,

    String secondName,

    String thirdName,

    String email
) {
}

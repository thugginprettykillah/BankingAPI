package sanchez.bankingapi.dto;


import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "User response")
public record UserResponseDto (

        @Schema(description = "User ID", example = "1")
        Long id,

        @Schema(description = "User first name", example = "Ivan")
        String firstName,

        @Schema(description = "User second name", example = "Ivanov")
        String secondName,

        @Schema(description = "User third name", example = "Ivanovich")
        String thirdName,

        @Schema(description = "User first name", example = "IvanIvanov@gmail.com")
        String email
) {
}

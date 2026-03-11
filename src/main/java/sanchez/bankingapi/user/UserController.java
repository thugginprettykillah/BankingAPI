package sanchez.bankingapi.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sanchez.bankingapi.dto.user.CreateUserRequestDto;
import sanchez.bankingapi.dto.exception.ErrorResponseDto;
import sanchez.bankingapi.dto.user.UserResponseDto;


@Tag(name = "Users",
        description = "Operations with users")
@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }


    @Operation(summary = "Get all users",
            description = "Returns all users")
    @GetMapping

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users list returned"),
            @ApiResponse(responseCode = "403", description = "Access denied",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))

    })
    public ResponseEntity<Page<UserResponseDto>> getAllUsers(
            @PageableDefault(size = 20, sort = "id") Pageable pageable,
            @RequestParam(required = false) Long roleId,
            @RequestParam(required = false) String emailLike
    )
    {
        log.info("Called getAllUsers from UserController");

        return ResponseEntity
                .ok(service.getAllUsers(pageable, roleId, emailLike));
    }


    @Operation(summary = "Get user by id",
            description = "Returns user by id")
    @GetMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User returned by id"),
            @ApiResponse(responseCode = "403", description = "Access denied",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            ),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable("id") Long id)
    {
        log.info("Called getUserById from UserController id={}", id);

        return ResponseEntity
                .ok(service.getUserById(id));
    }


    @Operation(summary = "Create user",
            description = "Creates user and returns it")
    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "", description = ""),
            @ApiResponse(responseCode = "201", description = "User created",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Validation error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "409", description = "Email already exists",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "403", description = "Access denied",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))

    })
    public ResponseEntity<UserResponseDto> createUser(@RequestBody @Valid CreateUserRequestDto request)
    {
        log.info("Called createUser from UserController request={}", request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.createUser(request));
    }


    @Operation(summary = "Delete user",
            description = "Delete user from database")
    @DeleteMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted"),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "403", description = "Access denied",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))

    })
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        log.info("Called deleteUser from UserController id={}", id);
        service.deleteUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

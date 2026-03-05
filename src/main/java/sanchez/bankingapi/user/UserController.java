package sanchez.bankingapi.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "Users",
        description = "Operations with users")
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final static Logger log = LoggerFactory.getLogger(UserController.class);

    private UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }


    @Operation(summary = "Get all users",
            description = "Returns all users")
    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "", description = "")
    })
    public ResponseEntity<List<UserResponseDto>> getAllUsers()
    {
        log.info("Called getAllUsers from UserController");

        return ResponseEntity
                .ok(service.getAllUsers());
    }


    @Operation(summary = "Get user by id",
            description = "Returns user by id")
    @GetMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "", description = "")
    })
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable("id") Long id)
    {
        log.info("Called getUserById from UserController id={}", id);

        return ResponseEntity
                .ok(service.getUserById(id));
    }


    @Operation(summary = "Create",
            description = "Returns info about created user")
    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "", description = "")
    })
    public ResponseEntity<UserResponseDto> createUser(@RequestBody @Valid CreateUserRequestDto request)
    {
        log.info("Called createUser from UserController request={}", request);

        return ResponseEntity
                .ok(service.createUser(request));
    }


    @Operation(summary = "Delete user",
            description = "User deleting from database")
    @DeleteMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "", description = "")
    })
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        log.info("Called deleteUser from UserController id={}", id);
        service.deleteUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

package sanchez.bankingapi.user;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usersapi")
public class UserController {

    private final static Logger log = LoggerFactory.getLogger(UserController.class);

    private UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDto>> getAllUsers()
    {
        log.info("Called getAllUsers from UserController");

        return ResponseEntity
                .ok(service.getAllUsers());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable("id") Long id)
    {
        log.info("Called getUserById from UserController id={}", id);

        return ResponseEntity
                .ok(service.getUserById(id));
    }

    @PostMapping("/users")
    public ResponseEntity<UserResponseDto> createUser(@RequestBody @Valid CreateUserRequestDto request)
    {
        log.info("Called createUser from UserController request={}", request);

        return ResponseEntity
                .ok(service.createUser(request));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        log.info("Called deleteUser from UserController id={}", id);
        service.deleteUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }




}

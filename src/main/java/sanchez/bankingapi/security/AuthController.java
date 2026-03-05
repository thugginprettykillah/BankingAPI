package sanchez.bankingapi.security;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sanchez.bankingapi.user.UserService;

@Tag(name = "Authentication",
        description = "Login and register")
@RestController
@RequestMapping("/api")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserService userService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtUtils jwtUtils, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.userService = userService;
    }

    @Operation(summary = "Login",
            description = "Login request")
    @PostMapping("/login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "", description = ""),
            @ApiResponse(responseCode = "", description = "")
    })
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Valid LoginRequestDto request) throws Exception
    {
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(request.email(), request.password());

        Authentication authentication = authenticationManager.authenticate(authToken);
        String email = authentication.getName();
        String token = jwtUtils.generateJwtToken(email);

        return ResponseEntity.ok(new LoginResponseDto(token));
    }

    @Operation(summary = "Register",
            description = "Registration request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "", description = ""),
            @ApiResponse(responseCode = "", description = "")
    })
    @PostMapping("/signup")
    public ResponseEntity<RegistrationResponseDto> register(@RequestBody @Valid RegistrationRequestDto request)
    {
        RegistrationResponseDto responseDto = userService.createUser(request);
        return ResponseEntity.ok(responseDto);
    }
}

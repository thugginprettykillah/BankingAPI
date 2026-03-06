package sanchez.bankingapi.account;

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
import sanchez.bankingapi.dto.AccountResponseDto;
import sanchez.bankingapi.dto.CreateAccountRequestDto;
import sanchez.bankingapi.dto.TransferRequestDto;
import sanchez.bankingapi.dto.TransferResponseDto;
import sanchez.bankingapi.transaction.TransferService;

import java.net.URI;
import java.util.List;


@Tag(name = "Accounts", description = "Operations with bank accounts")
@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private static final Logger log = LoggerFactory.getLogger(AccountController.class);

    private final AccountService accountService;

    private final TransferService transferService;

    @Autowired
    public AccountController(AccountService accountService,  TransferService transferService)
    {
        this.accountService = accountService;
        this.transferService = transferService;
    }



    @GetMapping
    @Operation(summary = "Get all accounts",
            description = "Returns all accounts")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of accounts"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    public ResponseEntity<List<AccountResponseDto>> getAllAccounts()
    {
        log.info("Called method getAccounts from accountController");

        return ResponseEntity.ok(accountService.getAllAccounts());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get account by id",
            description = "Returns a single account")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Returns account by id"),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "404", description = "Account not found")
    })
    public ResponseEntity<AccountResponseDto> getAccountById(@PathVariable("id") Long id)
    {
        log.info("Called method getAccountById from accountController, id={}", id);

        return ResponseEntity.ok(accountService.getAccountById(id));
    }

    @PostMapping
    @Operation(summary = "Add account",
            description = "Create account and returns info")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Account created"),
            @ApiResponse(responseCode = "400", description = "Validation error"),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "409", description = "Email already exists")
    })
    public ResponseEntity<AccountResponseDto> addAccount(@RequestBody @Valid CreateAccountRequestDto request)
    {
        log.info("Called method addAccount from accountController, request={}", request);

        AccountResponseDto dto = accountService.addAccount(request);
        return ResponseEntity
                .created(URI.create("/api/accounts/" + dto.id()))
                .body(dto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete account by id",
            description = "Delete a single account")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Account deleted"),
            @ApiResponse(responseCode = "404", description = "Account not found"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    public ResponseEntity<Void> deleteAccount(@PathVariable("id") Long id) {
        log.info("Called method deleteAccount from accountController, id={}", id);
        accountService.deleteAccount(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/transfer")
    @Operation(summary = "Transfer request",
            description = "Send transfer request and return info about transaction")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Transfer successful"),
            @ApiResponse(responseCode = "400", description = "Invalid transfer"),
            @ApiResponse(responseCode = "404", description = "Account not found")
    })
    public ResponseEntity<TransferResponseDto> transferResponse(@RequestBody @Valid TransferRequestDto transferRequestDto) {
        log.info("Called method transferResponse from accountController, transferRequestDto={}", transferRequestDto);
        TransferResponseDto response = transferService.transfer(transferRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}

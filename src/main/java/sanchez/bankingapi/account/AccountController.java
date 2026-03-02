package sanchez.bankingapi.account;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sanchez.bankingapi.transaction.TransferRequestDto;
import sanchez.bankingapi.transaction.TransferResponseDto;
import sanchez.bankingapi.transaction.TransferService;
import sanchez.bankingapi.user.UserService;

import java.net.URI;
import java.util.List;

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

    @GetMapping("/health")
    public String getHealth()
    {
        return "OK";
    }

    @GetMapping("")
    public ResponseEntity<List<AccountResponseDto>> getAllAccounts()
    {
        log.info("Called method getAccounts from accountController");

        return ResponseEntity.ok(accountService.getAllAccounts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponseDto> getAccountById(@PathVariable("id") Long id)
    {
        log.info("Called method getAccountById from accountController, id={}", id);

        return ResponseEntity.ok(accountService.getAccountById(id));
    }

    @PostMapping
    public ResponseEntity<AccountResponseDto> addAccount(@RequestBody @Valid CreateAccountRequestDto request)
    {
        log.info("Called method addAccount from accountController, request={}", request);

        AccountResponseDto dto = accountService.addAccount(request);
        return ResponseEntity
                .created(URI.create("/api/accounts/" + dto.id()))
                .body(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable("id") Long id) {
        log.info("Called method deleteAccount from accountController, id={}", id);
        accountService.deleteAccount(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/transfer")
    public ResponseEntity<TransferResponseDto> transferResponse(@RequestBody @Valid TransferRequestDto transferRequestDto) {
        log.info("Called method transferResponse from accountController, transferRequestDto={}", transferRequestDto);
        TransferResponseDto response = transferService.transfer(transferRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}

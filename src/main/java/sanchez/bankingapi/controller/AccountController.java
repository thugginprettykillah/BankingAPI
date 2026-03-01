package sanchez.bankingapi.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sanchez.bankingapi.dto.AccountResponseDto;
import sanchez.bankingapi.dto.CreateAccountRequestDto;
import sanchez.bankingapi.service.AccountService;

import java.util.List;

@RestController
@RequestMapping("/accountapi")
public class AccountController {

    private static final Logger log = LoggerFactory.getLogger(AccountController.class);

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService)
    {
        this.accountService = accountService;
    }

    @GetMapping("/health")
    public String getHealth()
    {
        return "OK";
    }

    @GetMapping("/accounts")
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

    @PostMapping("/create_account")
    public ResponseEntity<AccountResponseDto> addAccount(@RequestBody @Valid CreateAccountRequestDto request)
    {
        log.info("Called method addAccount from accountController, request={}", request);

        return ResponseEntity.status(201).body(accountService.addAccount(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable("id") Long id) {
        log.info("Called method deleteAccount from accountController, id={}", id);
        accountService.deleteAccount(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}

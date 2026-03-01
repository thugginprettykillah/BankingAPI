package sanchez.bankingapi.account;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AccountService {

    private static final Logger log = LoggerFactory.getLogger(AccountService.class);

    private final AccountRepository repository;

    @Autowired
    public AccountService(AccountRepository repository)
    {
        this.repository = repository;
    }

    public List<AccountResponseDto> getAllAccounts()
    {
        log.info("Called getAllAccounts from AccountService");
        List<AccountEntity> accountEntities = repository.findAll();
        return accountEntities.stream().map(this::toDto).toList();
    }

    public AccountResponseDto getAccountById(Long id)
    {
        log.info("Called getAccountById from AccountService id={}", id);
        AccountEntity find = repository.findById(id)
                        .orElseThrow(() ->
                        new EntityNotFoundException("Account with id=" + id + " not found"));
        return toDto(find);
    }

    @Transactional
    public AccountResponseDto addAccount(CreateAccountRequestDto request)
    {
        log.info("Called addAccount from AccountService request={}", request);
        AccountEntity accountEntity = toEntity(request);
        AccountEntity savedAccount = repository.save(accountEntity);
        return toDto(savedAccount);
    }

    @Transactional
    public void deleteAccount(Long id) {
        log.info("Called deleteAccount from AccountService id={}", id);
        if (!repository.existsById(id)) throw new EntityNotFoundException("Account with id=" + id + " not found");
        repository.deleteById(id);
    }




    private AccountResponseDto toDto(AccountEntity accountEntity)
    {
        return new AccountResponseDto(accountEntity.getId(),
                accountEntity.getAccountNumber(),
                accountEntity.getBalance(),
                accountEntity.getCurrency(),
                accountEntity.getCreatedAt());
    }

    private AccountEntity toEntity(CreateAccountRequestDto request)
    {
        AccountEntity accountEntity = new AccountEntity();

        accountEntity.setCurrency(request.currency());
        accountEntity.setBalance(new BigDecimal(0));
        accountEntity.setCreatedAt(LocalDateTime.now());
        accountEntity.setAccountNumber(getAccountNumber(request));

        return accountEntity;
    }


    private String getAccountNumber(CreateAccountRequestDto request)
    {
        StringBuilder accountNumber = new StringBuilder();
        accountNumber.append(request.currency());
        accountNumber.append("-");
        accountNumber.append(repository.getNextAccountNumber());
        return accountNumber.toString();
    }



}





package sanchez.bankingapi.account;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import sanchez.bankingapi.user.UserEntity;
import sanchez.bankingapi.user.UserRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    private static final Logger log = LoggerFactory.getLogger(AccountService.class);

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    @Autowired
    public AccountService(AccountRepository repository, UserRepository userRepository)
    {
        this.accountRepository = repository;
        this.userRepository = userRepository;
    }



    public List<AccountResponseDto> getAllAccounts()
    {
        log.info("Called getAllAccounts from AccountService");
        List<AccountEntity> accountEntities = accountRepository.findAll();
        return accountEntities.stream().map(this::toDto).toList();
    }

    public AccountResponseDto getAccountById(Long id)
    {
        log.info("Called getAccountById from AccountService id={}", id);
        AccountEntity find = accountRepository.findById(id)
                        .orElseThrow(() ->
                        new EntityNotFoundException("Account with id=" + id + " not found"));

        if (!find.getUser().getId().equals(getUserByAuth().getId())) {
            throw new AuthorizationDeniedException("You are not allowed to access this account");
        }
        return toDto(find);
    }

    @Transactional
    public AccountResponseDto addAccount(CreateAccountRequestDto request)
    {
        log.info("Called addAccount from AccountService request={}", request);
        AccountEntity accountEntity = toEntity(request);
        AccountEntity savedAccount = accountRepository.save(accountEntity);
        return toDto(savedAccount);
    }

    @Transactional
    public void deleteAccount(Long id) {
        log.info("Called deleteAccount from AccountService id={}", id);
        if (!accountRepository.existsById(id)) throw new EntityNotFoundException("Account with id=" + id + " not found");
        accountRepository.deleteById(id);
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
        accountEntity.setUser(getUserByAuth());

        return accountEntity;
    }


    private String getAccountNumber(CreateAccountRequestDto request)
    {
        StringBuilder accountNumber = new StringBuilder();
        accountNumber.append(request.currency());
        accountNumber.append("-");
        accountNumber.append(accountRepository.getNextAccountNumber());
        return accountNumber.toString();
    }

    private UserEntity getUserByAuth()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AuthorizationDeniedException("User is not authenticated");
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        return userRepository
                .findByEmail(userDetails.getUsername())
                .orElseThrow(
                        () -> new EntityNotFoundException("User with email="
                                + authentication.getName()
                                + " not found")
                );
    };



}





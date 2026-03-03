package sanchez.bankingapi.transaction;

import jakarta.persistence.EntityNotFoundException;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sanchez.bankingapi.account.AccountEntity;
import sanchez.bankingapi.account.AccountRepository;
import sanchez.bankingapi.exception.TransferAmountException;
import sanchez.bankingapi.exception.TransferDirectionException;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class TransferService {

    private static final Logger log = LoggerFactory.getLogger(TransferService.class);

    private final MoneyTransactionRepository moneyTransactionRepository;

    private final AccountRepository accountRepository;

    @Autowired
    public TransferService(MoneyTransactionRepository moneyTransactionRepository, AccountRepository accountRepository)
    {
        this.moneyTransactionRepository = moneyTransactionRepository;
        this.accountRepository = accountRepository;
    }

    @Transactional
    public TransferResponseDto transfer(@Valid TransferRequestDto request)
    {
        log.info("Called transfer method from TransferService....");

        MoneyTransactionEntity moneyTransactionEntity = new MoneyTransactionEntity();

        if (request.description() != null &&  !request.description().trim().isEmpty()) {
            moneyTransactionEntity.setDescription(request.description());
            log.info("Request description: " + request.description());
        }

        moneyTransactionEntity.setCreationDate(LocalDateTime.now());

        AccountEntity fromAccount = accountRepository
                .findById(request.fromAccountId())
                .orElseThrow( () -> new EntityNotFoundException("From account not found"));
        moneyTransactionEntity.setFromAccount(fromAccount);

        log.info("Checking user details....");
        if (!checkUserDetails(fromAccount)) {
            throw new AuthorizationDeniedException("User details are invalid to perform this operation");
        }


        var fromAccountNumber = fromAccount.getAccountNumber();

        AccountEntity toAccount = accountRepository
                .findById(request.toAccountId())
                .orElseThrow( () -> new EntityNotFoundException("To account not found"));
        moneyTransactionEntity.setToAccount(toAccount);

        var toAccountNumber = toAccount.getAccountNumber();

        if (fromAccount.getId().equals(toAccount.getId())) {
            throw new TransferDirectionException("Can't transfer to the same account");
        }

        log.info("Checking amount....");
        BigDecimal transferAmount = request.amount();
        if (transferAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new TransferAmountException("Transfer amount must be greater than zero");
        }

        if (fromAccount.getBalance().compareTo(request.amount()) < 0) {
            throw new TransferAmountException("Not enough balance");
        }

        fromAccount.setBalance(fromAccount.getBalance().subtract(transferAmount));
        toAccount.setBalance(toAccount.getBalance().add(transferAmount));
        moneyTransactionEntity.setAmount(transferAmount);
        log.info("Transferring...");

        var status = MoneyTransactionStatus.APPROVED;
        moneyTransactionEntity.setStatus(status);
        var saved = moneyTransactionRepository.save(moneyTransactionEntity);
        var message = "Transfer successful";

        return new TransferResponseDto(saved.getId(), fromAccountNumber, toAccountNumber, status, message);
    }

    @Transactional
    protected boolean checkUserDetails(AccountEntity fromAccount)
    {
        UserDetails userDetails = (UserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        if (userDetails == null) {
            return false;
        }

        boolean isAdmin = userDetails
                .getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        boolean isOwner = userDetails.getUsername().equals(fromAccount.getUser().getEmail());
        return isAdmin || isOwner;
    }
}

package sanchez.bankingapi.transaction;

import jakarta.persistence.*;
import sanchez.bankingapi.account.AccountEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class MoneyTransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @JoinColumn(name = "from_account_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private AccountEntity fromAccount;

    @JoinColumn(name = "to_account_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private AccountEntity toAccount;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "description", nullable = true)
    private String description;

    @Column(name = "status", nullable = false)
    private MoneyTransactionStatus status;

    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate;




    public MoneyTransactionEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AccountEntity getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(AccountEntity fromAccount) {
        this.fromAccount = fromAccount;
    }

    public AccountEntity getToAccount() {
        return toAccount;
    }

    public void setToAccount(AccountEntity toAccount) {
        this.toAccount = toAccount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public MoneyTransactionStatus getStatus() {
        return status;
    }

    public void setStatus(MoneyTransactionStatus status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
}

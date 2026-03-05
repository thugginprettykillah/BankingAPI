package sanchez.bankingapi.account;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import sanchez.bankingapi.transaction.MoneyTransactionEntity;
import sanchez.bankingapi.user.UserEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

//jpa
@Table(name = "accounts")
@Entity
//lombok + swagger

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "Model of bank account")
public class AccountEntity {


    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Schema(description = "Account ID",
            example = "1")
    private Long id;


    @Column(name = "account_number", nullable = false, unique = true, length = 20)

    @Schema(description = "Account number",
            example = "USD-21")
    private String accountNumber;


    @Column(name = "balance", nullable = false)

    @Schema(description = "Current balance",
            example = "1000.40")
    private BigDecimal balance;


    @Enumerated(EnumType.STRING)
    @Column(name = "currency", nullable = false)

    @Schema(description = "Account currency",
            example = "123")
    private AccountCurrency currency;


    @Column(name = "created_at",updatable = false, nullable = false)

    @Schema(description = "Creation timestamp",
            example = "2026-03-03T12:00:00",
            allowableValues = {"RUB", "USD", "EUR"})
    private LocalDateTime createdAt;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private UserEntity user;


    @OneToMany(mappedBy = "fromAccount")

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<MoneyTransactionEntity> outcomingTransactions;


    @OneToMany(mappedBy = "toAccount")

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<MoneyTransactionEntity> incomingTransactions;


    public AccountEntity(String accountNumber, BigDecimal balance, AccountCurrency currency, LocalDateTime createdAt, UserEntity user) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.currency = currency;
        this.createdAt = createdAt;
        this.user = user;
    }

    public void addIncomingTransaction(MoneyTransactionEntity moneyTransactionEntity) {
        incomingTransactions.add(moneyTransactionEntity);
    }

    public void addOutgoingTransaction(MoneyTransactionEntity moneyTransactionEntity) {
        outcomingTransactions.add(moneyTransactionEntity);
    }


}

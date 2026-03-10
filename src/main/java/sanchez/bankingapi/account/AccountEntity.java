package sanchez.bankingapi.account;


import jakarta.persistence.*;
import lombok.*;
import sanchez.bankingapi.transaction.MoneyTransactionEntity;
import sanchez.bankingapi.user.UserEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Table(name = "accounts")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class AccountEntity {


    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "account_number", nullable = false, unique = true, length = 20)
    private String accountNumber;


    @Column(name = "balance", nullable = false)
    private BigDecimal balance;


    @Enumerated(EnumType.STRING)
    @Column(name = "currency", nullable = false)
    private AccountCurrency currency;


    @Column(name = "created_at",updatable = false, nullable = false)
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

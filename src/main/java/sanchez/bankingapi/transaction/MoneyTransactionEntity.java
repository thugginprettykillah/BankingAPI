package sanchez.bankingapi.transaction;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sanchez.bankingapi.account.AccountEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@NoArgsConstructor
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

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private MoneyTransactionStatus status;

    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate;
}

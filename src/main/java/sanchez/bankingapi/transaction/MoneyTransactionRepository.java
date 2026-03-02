package sanchez.bankingapi.transaction;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MoneyTransactionRepository extends JpaRepository<MoneyTransactionEntity, Long> {

}

package sanchez.bankingapi.account;

import org.springframework.data.jpa.domain.Specification;

public class AccountSpecification {

    public static Specification<AccountEntity> accountNumberLike(String accountNumber)
    {
        return (root, criteriaQuery, criteriaBuilder) -> {
            if (accountNumber == null) {
                return criteriaBuilder.conjunction();
            }

            return criteriaBuilder
                    .like(
                            criteriaBuilder.lower(root.get("accountNumber")),
                            "%" + accountNumber.toLowerCase() + "%"
                    );
        };
    }

    public static Specification<AccountEntity> hasUserId(Long userId)
    {
        return (root, criteriaQuery, criteriaBuilder) -> {
            if (userId == null) {
                return criteriaBuilder.disjunction();
            }

            return criteriaBuilder.equal(root.get("user").get("id"), userId);
        };
    }

    public static Specification<AccountEntity> hasCurrency(String currency)
    {

        return (root, criteriaQuery, criteriaBuilder) -> {
            if (currency == null) {
                return criteriaBuilder.conjunction();
            }
            try {
                AccountCurrency currencyEnum = AccountCurrency.valueOf(currency.toUpperCase());
                return criteriaBuilder.equal(root.get("currency"), currencyEnum);
            } catch (IllegalArgumentException e) {
                return criteriaBuilder.disjunction();
            }
        };
    }
}

package sanchez.bankingapi.dto;

import sanchez.bankingapi.model.AccountCurrency;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AccountResponseDto(


        Long id,

        String accountNumber,

        BigDecimal balance,

        AccountCurrency currency,

        LocalDateTime createdAt
                          )
{}

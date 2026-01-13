package com.amigoscode.account;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@AllArgsConstructor
@Service
@Transactional
public class AccountService {

    private final AccountRepository accountRepository;

    public void transfer(Account from, Account to, BigDecimal amount){

        from.setBalance(from.getBalance().subtract(amount));
        accountRepository.save(from);
        to.setBalance(to.getBalance().add(amount));
        accountRepository.save(to);

    }

}

package io.anhkhue.more.services;

import io.anhkhue.more.models.dto.Account;
import io.anhkhue.more.repositories.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository repository;

    public AccountService(AccountRepository repository) {
        this.repository = repository;
    }

    public Account authenticate(String username, String password) {
        Optional<Account> account = repository.findByUsernameAndPassword(username, password);
        return account.orElse(null);
    }
}

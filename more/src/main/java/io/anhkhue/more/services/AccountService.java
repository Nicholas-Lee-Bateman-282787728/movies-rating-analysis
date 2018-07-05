package io.anhkhue.more.services;

import io.anhkhue.more.models.dto.Account;
import io.anhkhue.more.repositories.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {

    public static final int CREATED = 1;
    public static final int USERNAME_EXISTED = 0;
    public static final int INTERNAL_DATABASE_ERROR = -1;

    private final AccountRepository repository;

    public AccountService(AccountRepository repository) {
        this.repository = repository;
    }

    public Account authenticate(String username, String password) {
        Optional<Account> account = repository.findByUsernameAndPassword(username, password);
        return account.orElse(null);
    }

    public int signUp(Account account) {
        if (repository.findByUsername(account.getUsername()).isPresent()) {
            return USERNAME_EXISTED;
        } else {
            try {
                repository.save(account);
                return CREATED;
            } catch (Exception e) {
                return INTERNAL_DATABASE_ERROR;
            }
        }
    }

    public void save(Account account) {
        repository.findByUsername(account.getUsername()).ifPresent(acc -> {
            acc.setVendorId(acc.getVendorId());
            repository.save(acc);
        });
    }
}

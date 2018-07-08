package io.anhkhue.more.repositories;

import io.anhkhue.more.models.dto.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, String> {

    Optional<Account> findByUsername(String username);

    Optional<Account> findByUsernameAndPassword(String username, String password);

    List<Account> findByUsernameNot(String username);

    List<Account> findByRole(int role);
}

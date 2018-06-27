package io.anhkhue.more.controllers.apis;

import io.anhkhue.more.models.dto.Account;
import io.anhkhue.more.services.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/sign-in")
    public ResponseEntity authenticate(HttpServletRequest request,
                                       @RequestParam String username,
                                       @RequestParam String password) {
        Account account = accountService.authenticate(username, password);
        if (account != null) {
            HttpSession session = request.getSession();
            session.setAttribute("USER", account);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Thông tin không chính xác! Xin vui lòng thử lại!");
    }
}

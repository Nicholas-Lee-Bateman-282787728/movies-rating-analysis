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

import static io.anhkhue.more.services.AccountService.USERNAME_EXISTED;
import static org.springframework.http.HttpStatus.*;

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
            session.setAttribute("NEW_USERNAME", null);
            session.setAttribute("USER", account);
            return ResponseEntity.status(OK).build();
        }

        return ResponseEntity.status(BAD_REQUEST).body("Thông tin không chính xác!<br/>Xin vui lòng thử lại!");
    }

    @PostMapping("/sign-up")
    public ResponseEntity signUp(HttpServletRequest request,
                                 Account account) {
        int result = accountService.signUp(account);
        switch (result) {
            case AccountService.CREATED:
                HttpSession session = request.getSession();
                session.setAttribute("NEW_USERNAME", account.getUsername());
                return ResponseEntity.status(HttpStatus.CREATED).build();
            case USERNAME_EXISTED:
                return ResponseEntity.status(NOT_ACCEPTABLE).body("Username đã tồn tại");
            default:
                return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }

}

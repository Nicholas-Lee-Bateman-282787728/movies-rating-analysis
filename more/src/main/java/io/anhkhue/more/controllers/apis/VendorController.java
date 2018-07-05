package io.anhkhue.more.controllers.apis;

import io.anhkhue.more.models.custom.VendorWrapper;
import io.anhkhue.more.services.AccountService;
import io.anhkhue.more.services.VendorService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.HttpStatus.*;

@Controller
public class VendorController {

    private final VendorService vendorService;

    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @PostMapping("/vendor")
    public ResponseEntity signUp(HttpServletRequest request,
                                 VendorWrapper vendorWrapper) {
        int result = vendorService.addVendor(vendorWrapper);
        switch (result) {
            case AccountService.USERNAME_EXISTED:
                return ResponseEntity.status(NOT_ACCEPTABLE).body("Username đã tồn tại");
            case AccountService.INTERNAL_DATABASE_ERROR:
                return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
            default:
                request.setAttribute("NEW_ID", result);
                return ResponseEntity.status(CREATED).body(result);
        }
    }
}

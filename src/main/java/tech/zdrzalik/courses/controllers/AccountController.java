package tech.zdrzalik.courses.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import tech.zdrzalik.courses.DTO.Request.RegisterAccountDTO;
import tech.zdrzalik.courses.exceptions.AccountInfoException;
import tech.zdrzalik.courses.services.AccountService;

import javax.annotation.security.PermitAll;

@Controller()
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping(value = "/register")
    @ResponseBody()
    @PermitAll
    public ResponseEntity<?> RegisterAccount(@RequestBody RegisterAccountDTO dto) throws AccountInfoException {
        accountService.registerAccount(dto);
        return ResponseEntity.ok().build();
    }
}

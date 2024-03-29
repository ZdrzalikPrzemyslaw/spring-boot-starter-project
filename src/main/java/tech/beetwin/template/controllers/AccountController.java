package tech.beetwin.template.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import tech.beetwin.template.dto.response.BasicMessageResponseDTO;
import tech.beetwin.template.common.I18nCodes;
import tech.beetwin.template.services.AccountService;
import tech.beetwin.template.dto.request.RegisterAccountDTO;

/**
 * Controller on /account mapping. All api paths concerning account should be defined here.
 */
@Controller()
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * Method which creates a new account for user details specified in {@link RegisterAccountDTO}.
     * @param dto {@link RegisterAccountDTO} constructed from the api request.
     * @return {@link ResponseEntity} with a {@link ResponseEntity#body} consisting of {@link BasicMessageResponseDTO} with the {@link BasicMessageResponseDTO#message} of {@link I18nCodes#ACCOUNT_CREATED_SUCCESSFULLY}
     */
    @PostMapping(value = "/register")
    @ResponseBody()
    @PreAuthorize("permitAll()")
    public ResponseEntity<BasicMessageResponseDTO> registerAccount(@RequestBody RegisterAccountDTO dto) {
        accountService.registerAccount(dto);
        return ResponseEntity.ok().body(new BasicMessageResponseDTO().setMessage(I18nCodes.ACCOUNT_CREATED_SUCCESSFULLY));
    }
}

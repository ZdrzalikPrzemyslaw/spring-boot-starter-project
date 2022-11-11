package tech.zdrzalik.courses.controllers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tech.zdrzalik.courses.DTO.Request.LoginRequestDTO;
import tech.zdrzalik.courses.DTO.Response.LoginResponseDTO;
import tech.zdrzalik.courses.DTO.Response.MessageResponseDTO;
import tech.zdrzalik.courses.common.I18nCodes;
import tech.zdrzalik.courses.services.AccountService;

import javax.annotation.security.PermitAll;

@Controller()
@RequestMapping("/auth")
public class AuthenticationController {

    private final AccountService accountService;

    public AuthenticationController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping()
    @PermitAll
    @ResponseBody()
//    @PreAuthorize("hasRole('admin')")
//    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<?> authenticate(@RequestBody LoginRequestDTO dto) throws Exception {
        String token = accountService.authenticate(dto);
        return ResponseEntity.ok().body(
                new LoginResponseDTO()
                        .setMessage(I18nCodes.AUTHENTICATION_SUCCESS)
                        .setToken(token));
    }
}

package tech.zdrzalik.courses.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tech.zdrzalik.courses.DTO.Request.AuthenticationRequestDTO;
import tech.zdrzalik.courses.DTO.Response.AuthenticationResponseDTO;
import tech.zdrzalik.courses.common.I18nCodes;
import tech.zdrzalik.courses.services.AccountService;

import javax.annotation.security.PermitAll;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Controller()
@RequestMapping("/auth")
public class AuthenticationController {

    private final AccountService accountService;

    public AuthenticationController(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * This method accepts {@link org.springframework.http.HttpMethod#POST} requests
     * @param dto {@link AuthenticationRequestDTO} constructed from the api request.
     * @return {@link ResponseEntity} containing the authentication token and basic info about user as specified in {@link AuthenticationResponseDTO}.
     */
    // TODO: 11/11/2022 https://stackoverflow.com/questions/33663801/how-do-i-customize-default-error-message-from-spring-valid-validation
    @PostMapping()
    @PermitAll
    @ResponseBody()
    public ResponseEntity<AuthenticationResponseDTO> authenticate(@RequestBody @Valid @NotNull(message = I18nCodes.REQUEST_NULL) AuthenticationRequestDTO dto) {
        var res = accountService.authenticate(dto);
        return ResponseEntity.ok().body(res);
    }
}

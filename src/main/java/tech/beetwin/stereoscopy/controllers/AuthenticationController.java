package tech.beetwin.stereoscopy.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tech.beetwin.stereoscopy.dto.request.AuthenticationRequestDTO;
import tech.beetwin.stereoscopy.dto.request.RefreshTokenRequestDTO;
import tech.beetwin.stereoscopy.dto.response.AuthenticationResponseDTO;
import tech.beetwin.stereoscopy.common.I18nCodes;
import tech.beetwin.stereoscopy.dto.response.BasicMessageResponseDTO;
import tech.beetwin.stereoscopy.services.AccountService;
import tech.beetwin.stereoscopy.utils.AuthJWTUtils;
import tech.beetwin.stereoscopy.utils.RefreshAuthJWTUtils;

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
    @PostMapping()
    @PreAuthorize("permitAll()")
    @ResponseBody()
    public ResponseEntity<AuthenticationResponseDTO> authenticate(@RequestBody @Valid @NotNull(message = I18nCodes.REQUEST_NULL) AuthenticationRequestDTO dto) {
        var res = accountService.authenticate(dto);
        return ResponseEntity.ok().body(res);
    }

    /**
     * This method accepts {@link org.springframework.http.HttpMethod#POST} requests
     * @param dto {@link AuthenticationRequestDTO} constructed from the api request.
     * @return {@link ResponseEntity} containing the authentication token and basic info about user as specified in {@link AuthenticationResponseDTO}.
     */
    @PostMapping("/refresh")
    @PreAuthorize("!hasAuthority('ROLE_ANONYMOUS')")
    @ResponseBody()
    public ResponseEntity<AuthenticationResponseDTO> refreshAuthToken(@RequestBody @Valid @NotNull(message = I18nCodes.REQUEST_NULL) RefreshTokenRequestDTO dto) {
        var res = accountService.refreshToken(dto);
        return ResponseEntity.ok().body(res);
    }


}

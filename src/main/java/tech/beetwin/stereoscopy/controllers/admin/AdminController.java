package tech.beetwin.stereoscopy.controllers.admin;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import tech.beetwin.stereoscopy.DTO.Request.AuthenticationRequestDTO;
import tech.beetwin.stereoscopy.DTO.Request.EditUserInfoDTO;
import tech.beetwin.stereoscopy.DTO.Request.RegisterAccountDTO;
import tech.beetwin.stereoscopy.common.I18nCodes;
import tech.beetwin.stereoscopy.exceptions.AccountInfoException;
import tech.beetwin.stereoscopy.model.AccountInfo.AccountInfoEntity;
import tech.beetwin.stereoscopy.services.AccountService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Controller()
@PreAuthorize("hasAuthority('admin')")
@RequestMapping("/admin")
public class AdminController {

    private final AccountService accountService;
    @Value("${jwt.validity}")
    private int jwtTokenValidity;


    public AdminController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PreAuthorize("permitAll()")
    @GetMapping(value = "", produces = MediaType.TEXT_HTML_VALUE)
    public Object getAdminPanel() {
        // TODO: 12/11/2022 Sprawić, by tylko admin mógł zobaczyć ten panel
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return new RedirectView("/admin/login", true);
        }
        return new ModelAndView("admin/admin-panel");
    }

    private Cookie createBearerTokenCookie(String value, long duration) {
        Cookie cookie = new Cookie("bearer-token", value);
        cookie.setPath("/");
        // TODO: 11/11/2022  secure
        cookie.setSecure(false);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(Math.toIntExact(duration / 1000));
        return cookie;
    }

    @PreAuthorize("permitAll()")
    @PostMapping(value = "/logout", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView logout(HttpServletResponse response) {
        response.addCookie(createBearerTokenCookie(null, 0));
        return new ModelAndView("redirect:/admin");
    }

    @PreAuthorize("permitAll()")
    @PostMapping(value = "/login", produces = MediaType.TEXT_HTML_VALUE)
    public Object authenticate(@ModelAttribute("DTO") @Valid @NotNull AuthenticationRequestDTO dto, BindingResult result, HttpServletResponse response) {
        if (result.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("admin/login");
            modelAndView.addObject("org.springframework.validation.BindingResult.DTO", result);
            modelAndView.addObject("DTO", new AuthenticationRequestDTO());
            modelAndView.setStatus(HttpStatus.BAD_REQUEST);
            return modelAndView;
        }
        try {
            // TODO: 12/11/2022 Sprawić, by tylko admin mógł zobaczyć ten panel
            var authDto = accountService.authenticate(dto);
            // TODO: 11/11/2022 Dodac expiration do cookie
            response.addCookie(createBearerTokenCookie(authDto.getToken(), authDto.getValidDuration()));
            return new ModelAndView("redirect:/admin");
        } catch (Exception e) {
            RedirectView redirectView = new RedirectView("/admin", true);
            return redirectView;
            // TODO: 11/11/2022 Handle wyjatki - pokazac jakas wiadomosc czy cos ze sie nie udalo zalogowac
        }
    }

    @PreAuthorize("permitAll()")
    @GetMapping(value = "/login", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getLogin() {
        ModelAndView modelAndView = new ModelAndView("admin/login");
        modelAndView.addObject("DTO", new AuthenticationRequestDTO());
        return modelAndView;
    }


    @GetMapping(value = "/create-account", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getCreateAccount() {
        ModelAndView modelAndView = new ModelAndView("admin/create-account");
        modelAndView.addObject("DTO", new RegisterAccountDTO());
        return modelAndView;
    }

    @PostMapping(value = "/create-account", produces = MediaType.TEXT_HTML_VALUE)
    public Object createAccount(@ModelAttribute("DTO") @Valid @NotNull RegisterAccountDTO dto, BindingResult result) {
        ModelAndView modelAndView = this.getCreateAccount();
        if (result.hasErrors()) {
            modelAndView.addObject("org.springframework.validation.BindingResult.DTO", result);
            return modelAndView;
        }
        try {
            accountService.registerAccount(dto);
        } catch (AccountInfoException e) {
            modelAndView.setStatus(HttpStatus.BAD_REQUEST);
            return modelAndView;
            // TODO: 11/11/2022 Handle wyjatki - pokazac jakas wiadomosc czy cos ze sie nie udalo stworzyć konta
        }
        modelAndView.addObject("showSuccess", true);
        return modelAndView;
    }

    @GetMapping(value = {"user-info/{id}"}, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getUser(@NotNull(message = I18nCodes.ID_NULL) @Valid @Min(value = 0) @PathVariable Long id) {
        AccountInfoEntity accountInfoEntity = accountService.findById(id);
        ModelAndView modelAndView = new ModelAndView("admin/user-info");
        modelAndView.addObject("accountInfoEntity", accountInfoEntity);
        modelAndView.addObject("DTO", new EditUserInfoDTO(accountInfoEntity));
        return modelAndView;
    }

    @GetMapping(value = {"user-info"}, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getUser(@PageableDefault(sort = "id") Pageable pageable) {
        ModelAndView modelAndView = new ModelAndView("admin/users-list");
        var page = accountService.findAll(pageable);
        modelAndView.addObject("users", page);
        return modelAndView;
    }

    @PostMapping(value = "user-info/{id}", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView editUser(@NotNull(message = I18nCodes.ID_NULL) @Valid @Min(value = 0) @PathVariable Long id, @ModelAttribute("DTO") @Valid @NotNull EditUserInfoDTO dto, BindingResult result) {
        ModelAndView modelAndView;
        if (result.hasErrors()) {
            modelAndView = this.getUser(id);
            modelAndView.addObject("org.springframework.validation.BindingResult.DTO", result);
            return modelAndView;
        }
        try {
            accountService.editAccount(id, dto);
        } catch (AccountInfoException e) {
//            modelAndView.addObject();
        }
        modelAndView = this.getUser(id);
        modelAndView.addObject("showSuccess", true);
        return modelAndView;
    }

}

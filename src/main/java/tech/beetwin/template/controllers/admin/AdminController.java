package tech.beetwin.template.controllers.admin;

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
import tech.beetwin.template.dto.request.AuthenticationRequestDTO;
import tech.beetwin.template.dto.request.EditUserInfoDTO;
import tech.beetwin.template.dto.request.RegisterAccountDTO;
import tech.beetwin.template.common.I18nCodes;
import tech.beetwin.template.exceptions.AccountInfoException;
import tech.beetwin.template.model.AccountInfo.AccountInfoEntity;
import tech.beetwin.template.services.AccountService;
import tech.beetwin.template.utils.VersionJWTUtils;

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
    @Value("${jwt.validity.auth-token}")
    private int jwtTokenValidity;


    private VersionJWTUtils jwtUtils;


    public AdminController(AccountService accountService, VersionJWTUtils jwtUtils) {
        this.accountService = accountService;
        this.jwtUtils = jwtUtils;
    }

    @RequestMapping(value = "/**")
    public RedirectView redirect() {
        return new RedirectView("/admin", true);
    }

    @PreAuthorize("permitAll()")
    @GetMapping(value = "", produces = MediaType.TEXT_HTML_VALUE)
    public Object getAdminPanel() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return new RedirectView("/admin/login", true);
        }
        return new RedirectView("/admin/user-info", true);
    }

    private Cookie createBearerTokenCookie(String value, long duration) {
        Cookie cookie = new Cookie("bearer-token", value);
        cookie.setPath("/");
        // TODO: 11/11/2022 secure?
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
            ModelAndView modelAndView = getLogin();
            modelAndView.addObject("org.springframework.validation.BindingResult.DTO", result);
            modelAndView.setStatus(HttpStatus.BAD_REQUEST);
            return modelAndView;
        }
        try {
            var authDto = accountService.authenticate(dto);
            response.addCookie(createBearerTokenCookie(authDto.getAuthToken(), authDto.getValidDuration()));
            return new ModelAndView("redirect:/admin/user-info");
        } catch (Exception e) {
            ModelAndView login = getLogin();
            login.addObject("errorMessage", e.getMessage());
            login.setStatus(HttpStatus.UNAUTHORIZED);
            return login;
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
            modelAndView.addObject("showSuccess", true);
        } catch (AccountInfoException e) {
            modelAndView.setStatus(HttpStatus.BAD_REQUEST);
            modelAndView.addObject("errorMessage", e.getMessage());
        }
        return modelAndView;
    }

    @GetMapping(value = {"user-info/{id}"}, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getUser(@NotNull(message = I18nCodes.ID_NULL) @Valid @Min(value = 0) @PathVariable Long id) {
        AccountInfoEntity accountInfoEntity = accountService.findById(id);
        ModelAndView modelAndView = new ModelAndView("admin/user-info");
        modelAndView.addObject("accountInfoEntity", accountInfoEntity);
        modelAndView.addObject("DTO", new EditUserInfoDTO(accountInfoEntity, jwtUtils.generateToken(accountInfoEntity)));
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
        ModelAndView modelAndView = this.getUser(id);
        if (result.hasErrors()) {
            modelAndView.addObject("org.springframework.validation.BindingResult.DTO", result);
            return modelAndView;
        }
        try {
            accountService.editAccount(id, dto);
            modelAndView.addObject("showSuccess", true);
        } catch (AccountInfoException e) {
            modelAndView.addObject("errorMessage", e.getMessage());
        }
        return modelAndView;
    }
}

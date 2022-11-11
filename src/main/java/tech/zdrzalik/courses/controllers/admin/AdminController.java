package tech.zdrzalik.courses.controllers.admin;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.context.IExpressionContext;
import tech.zdrzalik.courses.DTO.Request.EditUserInfoDTO;
import tech.zdrzalik.courses.DTO.Request.LoginRequestDTO;
import tech.zdrzalik.courses.common.I18nCodes;
import tech.zdrzalik.courses.exceptions.AccountInfoException;
import tech.zdrzalik.courses.model.AccountInfo.AccountInfoEntity;
import tech.zdrzalik.courses.services.AccountService;

import javax.annotation.security.PermitAll;
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

    public AdminController(AccountService accountService) {
        this.accountService = accountService;
    }


    @Value("${jwt.validity}")
    private int JWT_TOKEN_VALIDITY;

    @PreAuthorize("permitAll()")
    @GetMapping(value = "", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getAdminPanel() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            ModelAndView modelAndView = new ModelAndView("login");
            modelAndView.addObject("DTO", new LoginRequestDTO());
            return modelAndView;
        }
        return new ModelAndView("admin-panel");
    }

    private Cookie createBearerTokenCookie(String value, int duration) {
        Cookie cookie = new Cookie("bearer-token", value);
        cookie.setPath("/");
        // TODO: 11/11/2022  secure
        cookie.setSecure(false);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(duration);
        return cookie;
    }

    @PreAuthorize("permitAll()")
    @PostMapping(value = "/logout", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView logout(HttpServletResponse response) {

        response.addCookie(createBearerTokenCookie(null, 0));
        ModelAndView modelAndView = new ModelAndView("redirect:/admin");
        return modelAndView;
    }

    @PreAuthorize("permitAll()")
    @PostMapping(value = "/login", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView authenticate(@ModelAttribute("DTO") @Valid @NotNull LoginRequestDTO dto, BindingResult result, HttpServletResponse response) {
        if (result.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("login");
            modelAndView.addObject("org.springframework.validation.BindingResult.DTO", result);
            modelAndView.addObject("DTO", new LoginRequestDTO());
            return modelAndView;
        }
        try {
             String authenticate = accountService.authenticate(dto);
            // TODO: 11/11/2022 Dodac expiration do cookie
            response.addCookie(createBearerTokenCookie(authenticate, JWT_TOKEN_VALIDITY));
            return new ModelAndView("redirect:/admin");
        } catch (Throwable t) {
            // TODO: 11/11/2022 Handle wyjatki - pokazac jakas wiadomosc czy cos ze sie nie udalo zalogowac
        }
        ModelAndView modelAndView = new ModelAndView("login");
        modelAndView.addObject("DTO", new LoginRequestDTO());
        return modelAndView;
    }

    @GetMapping(value = "users-list", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getUsersList(@PageableDefault(sort = "id") Pageable pageable) {
        ModelAndView modelAndView = new ModelAndView("users-list");
        var page = accountService.findAll(pageable);
        modelAndView.addObject("users", page);
        return modelAndView;
    }

    @GetMapping(value = "add-user", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getAddUser() {
        ModelAndView modelAndView = new ModelAndView("add-user");
        return modelAndView;
    }

    @GetMapping(value = "user-info/{id}", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getUser(@NotNull(message = I18nCodes.ID_NULL) @Valid @Min(value = 0) @PathVariable Long id) {
        AccountInfoEntity accountInfoEntity = accountService.findById(id);
        ModelAndView modelAndView = new ModelAndView("user-info");
        modelAndView.addObject("accountInfoEntity", accountInfoEntity);
        modelAndView.addObject("DTO", new EditUserInfoDTO(accountInfoEntity));
        return modelAndView;
    }

    @PostMapping(value = "user-info/{id}", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView editUser(@NotNull(message = I18nCodes.ID_NULL) @Valid @Min(value = 0) @PathVariable Long id, @ModelAttribute("DTO") @Valid @NotNull EditUserInfoDTO dto, BindingResult result) {
        ModelAndView modelAndView;
        if (result.hasErrors()) {
            modelAndView = this.getUser(id);
            modelAndView.addObject("org.springframework.validation.BindingResult.DTO", result);
            // TODO: 08/11/2022 Wykorzystac result
//            modelAndView.addAllObjects(result.getAllErrors().);
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

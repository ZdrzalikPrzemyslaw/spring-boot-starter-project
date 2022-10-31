package tech.zdrzalik.courses.controllers.admin;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Controller()
@RequestMapping("/admin")
public class AdminController {

    @GetMapping(value = "", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getAdminPanel() {
        ModelAndView modelAndView = new ModelAndView("admin-panel");
        modelAndView.addObject("hello", "world");
        return modelAndView;
    }

    @GetMapping(value = "users-list", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getUsersList() {
        ModelAndView modelAndView = new ModelAndView("users-list");
        return modelAndView;
    }

    @GetMapping(value = "add-user", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getAddUser() {
        ModelAndView modelAndView = new ModelAndView("add-user");
        return modelAndView;
    }

    @GetMapping(value = "user-info/{id}", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getUser(@NotNull(message = ) @Valid @Min(value = 0) @PathVariable String id) {
        ModelAndView modelAndView = new ModelAndView("user-info");
        return modelAndView;
    }

}

package tech.beetwin.template.controllers;


import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller()
@PreAuthorize("permitAll()")
@RequestMapping("/")
public class WelcomeController {

    @GetMapping(value = "", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getWelcomePage() {
        return new ModelAndView("welcome");
    }

}

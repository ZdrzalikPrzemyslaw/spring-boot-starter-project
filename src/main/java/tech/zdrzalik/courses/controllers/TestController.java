package tech.zdrzalik.courses.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.LoggerContext;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import tech.zdrzalik.courses.model.AccessLevel.AccessLevel;

import javax.annotation.security.PermitAll;
import java.util.logging.LogManager;
import javax.annotation.security.RolesAllowed;

@Controller()
@RequestMapping("/test")
@PermitAll
public class TestController {

    @GetMapping(value = "", produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody()
    public String Test() {

        Logger logger = LoggerFactory.getLogger(this.getClass());
        logger.info("Test Successfull");
        return "Test Successful";
    }

    @GetMapping(value = "/logged", produces = MediaType.TEXT_PLAIN_VALUE)
    @PreAuthorize("hasAuthority('user')")
    @ResponseBody()
    public String TestLogged() {
        return "Test Successful";
    }
}

package tech.zdrzalik.courses.controllers;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller()
@RequestMapping("/test")
public class TestController {

    @GetMapping(value = "", produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody()
    public String Test() {
        return "Test Successful";
    }
}

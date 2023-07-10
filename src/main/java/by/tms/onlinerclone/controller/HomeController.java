package by.tms.onlinerclone.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Andrei Lisouski (Andrlis) 10/07/2023 - 12:11
 */
@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping
    public String showHomePage(){
        System.out.println("TEST THAT METHOD WAS CALLED");
        return "home";
    }
}

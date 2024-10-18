package com.adil.MunzMade.Controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequestMapping("/user")
public class UserController {


    //dashboard

    @GetMapping("/dashboard")
    public String dashboard() {


        return "user/dashboard";
    }


    //profile

    @GetMapping("/profile")
    public String profile(Model model, Authentication authentication) {


        

        return "user/profile";
    }


    

    //add Customers

    //view Customers

    //edit Customers

    //delete Customers

    //search Customers
}

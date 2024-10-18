package com.adil.MunzMade.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.adil.MunzMade.Helper.Helper;
import com.adil.MunzMade.Model.User;
import com.adil.MunzMade.Services.UserService;

@ControllerAdvice
public class RootController {



    @Autowired
    UserService service;


    private Logger logger = LoggerFactory.getLogger(UserController.class);


    @ModelAttribute
    public void loggedInUserInformation(Model model, Authentication authentication) {


        if (authentication == null) return;

	logger.info("adding logged in user info to the model");

        // System.out.println("adding logged in user info to the model");


        String username = Helper.getEmailOfLoggedInUser(authentication);

        logger.info("logged in: {}", username);


        User user = service.getUserByEmail(username);

	logger.info("User '{}' with email '{}' added to the model", user.getName(), user.getEmail());

        model.addAttribute("loggedInUser", user);

    }


}

package com.adil.MunzMade.Config;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.adil.MunzMade.Helper.Constants;
import com.adil.MunzMade.Model.Providers;
import com.adil.MunzMade.Model.User;
import com.adil.MunzMade.Repository.UserRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuthAuthenticationSuccessHandler implements AuthenticationSuccessHandler {


    @Autowired
    UserRepository repository;


    Logger logger = LoggerFactory.getLogger(OAuthAuthenticationSuccessHandler.class);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

                
        logger.info("Login Successful by OAuthAuthenticationSuccessHandler");

        // retrieve and save data in the database:

        DefaultOAuth2User user = (DefaultOAuth2User)authentication.getPrincipal();

        // logger.info(user.getName());

        // user.getAttributes().forEach((key, value) -> {

        //     logger.info("{} : {}", key, value);
        // });

        // logger.info(user.getAuthorities().toString());


        String email = user.getAttribute("email").toString();

        String name = user.getAttribute("name").toString();

        String profilePicture = user.getAttribute("picture").toString();


        // create user:

        User user1 = new User();

        user1.setEmail(email);
        user1.setName(name);
        user1.setPassword("password");
        user1.setProfilePic(profilePicture);
        user1.setId(UUID.randomUUID().toString());
        user1.setProvider(Providers.GOOGLE);
        user1.setProviderUserId(user.getName());
        user1.setRoleList(List.of(Constants.ROLE_USER));
        user1.setEmailVerified(true);
	user1.setEnabled(true);



        User user2 = repository.findByEmail(email).orElse(null);

        if (user2 == null) {

            repository.save(user1);

            logger.info("user saved: " + email);
        }


        new DefaultRedirectStrategy().sendRedirect(request, response, "/home");

    }



}

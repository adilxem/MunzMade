package com.adil.MunzMade.Config;

import java.io.IOException;

import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.adil.MunzMade.Helper.Message;
import com.adil.MunzMade.Helper.MessageType;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class AuthFailureHandler implements AuthenticationFailureHandler{

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
		
		
		HttpSession session = request.getSession();

		String errorMessage = "Invalid username or password";



		if (exception.getClass().isAssignableFrom(org.springframework.security.core.userdetails.UsernameNotFoundException.class)) {

			errorMessage = "User not found";

			session.setAttribute("message", Message.builder()
			.content("User not found. Please Sign Up to create an account.")
			.type(MessageType.red)
			.build()
			);

			response.sendRedirect("/login");
		}


		else if (exception.getClass().isAssignableFrom(org.springframework.security.authentication.BadCredentialsException.class)) {

			errorMessage = "Incorrect password";

			session.setAttribute("message", Message.builder()
			.content("Incorrect password")
			.type(MessageType.red)
			.build()
			);

			response.sendRedirect("/login");
		} 
		    
                

                else if (exception instanceof DisabledException) {
                    
                    // user is not enabled


                    session.setAttribute("message", Message.builder()
                    .content("Your account is not verified. Please check your email and click the verification link to activate your account.")
                    .type(MessageType.red)
                    .build()
                    );

                    response.sendRedirect("/login");
                }

                else {

                    response.sendRedirect("/login?error=" + errorMessage);
                }
                
    }

}

package com.adil.MunzMade.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.adil.MunzMade.Services.SecurityCustomUserDetailService;

@Configuration
public class SecurityConfig {


    // @Bean
    // public UserDetailsService userDetailsService() {


    //     UserDetails user = User
    //     .withUsername("admin")
    //     .password("admin")
    //     .roles("ADMIN","USER")
    //     .build();

    //     return new InMemoryUserDetailsManager(user);
    // }

    @Autowired
    private SecurityCustomUserDetailService userDetailService;


    @Autowired
    private OAuthAuthenticationSuccessHandler oAuthAuthenticationSuccessHandler;

    @Autowired
    private AuthFailureHandler authFailureHandler;


    @Bean
    AuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

        // get the object of UserDetailsService and pass in the argument below

        daoAuthenticationProvider.setUserDetailsService(userDetailService);

        // get the object of PasswordEncoder and pass in the argument below

        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

	// Propagate UsernameNotFoundException instead of treating it as BadCredentialsException
	daoAuthenticationProvider.setHideUserNotFoundExceptions(false);

        return daoAuthenticationProvider;        
    }


    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {


        // URL configurations to authorize all non /user URL patterns
        
        httpSecurity.authorizeHttpRequests(authorize -> {

            authorize.requestMatchers("/images/**", "/css/**", "/js/**", "/resources/**").permitAll();
            authorize.requestMatchers("/user/**").authenticated();
            authorize.anyRequest().permitAll();
        });


        // customize every/ anything related to form logins here...

        httpSecurity.formLogin(formLogin -> {


            formLogin.loginPage("/login");

            formLogin.loginProcessingUrl("/authenticate");

            formLogin.defaultSuccessUrl("/home", true);

            formLogin.failureUrl("/login?error=true");

            formLogin.usernameParameter("email");

            formLogin.passwordParameter("password");


            formLogin.failureHandler(authFailureHandler);

        });

        httpSecurity.logout(logoutForm -> {

            logoutForm.logoutUrl("/logout");

            logoutForm.logoutSuccessUrl("/login?logout=true");

        });


        // OAuth Config:

        httpSecurity.oauth2Login(oauth -> {

            oauth.loginPage("/login");

            oauth.successHandler(oAuthAuthenticationSuccessHandler);
        });

        return httpSecurity.build();
    }


    @Bean
    PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

}

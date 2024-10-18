package com.adil.MunzMade.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import com.adil.MunzMade.Forms.UserForm;
import com.adil.MunzMade.Helper.Message;
import com.adil.MunzMade.Helper.MessageType;
import com.adil.MunzMade.Model.User;
import com.adil.MunzMade.Services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;





@Controller
public class PageController {


    @Autowired
    UserService service;

    @GetMapping("/")
    public String index() {

        return "redirect:/home";
    }

    @RequestMapping("/home")
    public String home(Model model) {


        return "home";
    }
    
    @GetMapping("/about")
    public String about() {

        return "about";
    }


    @GetMapping("/services")
    public String services() {

        return "sevices";
    }


    @GetMapping("/contactUs")
    public String contact() {

        return "contactUs";
    }


    @GetMapping("/login")
    public String login() {

        return "login";
    }


    @GetMapping("/register")
    public String register(Model model) {

        UserForm userForm = new UserForm();

        model.addAttribute("userForm", userForm);

        return "register";
    }


    // process registration POST request


    @PostMapping("/register")
    public String registerRequest(@Valid @ModelAttribute UserForm userForm, BindingResult rBindingResult, HttpSession session) {
        
        // fetch form data 
        // done: in @GetMapping("/register") using UserForm

        // validate form data
        // done:

        if (rBindingResult.hasErrors()) return "register";


	// Map form data to user entity
	User user = new User();
	user.setEmail(userForm.getEmail());
	user.setPassword(userForm.getPassword());
	user.setName(userForm.getName());
	user.setPhoneNumber(userForm.getPhoneNumber());



	try {

		service.addUser(user);
	
	
		// message = "registration successful"
		// done:
	
		Message message = Message.builder().content("Registration Successful!. ").type(MessageType.green).build();
	
		session.setAttribute("message", message);
	
	
		// redirect to login page
		// done:
	
		return "redirect:/login";

	}

	catch (IllegalStateException e) {

		// If email already exists, set error message
		Message message = Message.builder()
			.content("User already exists with this email. Please use a different email or log in.")
			.type(MessageType.red)
			.build();
		session.setAttribute("message", message);
	
		return "redirect:/register"; // Redirect back to registration form
	}

        
    }

}

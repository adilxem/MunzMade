package com.adil.MunzMade.Controller;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.adil.MunzMade.Forms.CustomerForm;
import com.adil.MunzMade.Forms.CustomerSearchForm;
import com.adil.MunzMade.Helper.Constants;
import com.adil.MunzMade.Helper.Helper;
import com.adil.MunzMade.Helper.Message;
import com.adil.MunzMade.Helper.MessageType;
import com.adil.MunzMade.Model.Customer;
import com.adil.MunzMade.Model.User;
import com.adil.MunzMade.Services.CustomerService;
import com.adil.MunzMade.Services.ImageService;
import com.adil.MunzMade.Services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/user/customers")
public class CustomerController {

    private Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    CustomerService customerService;

    @Autowired
    UserService userService;

    @Autowired
    ImageService imageService;

    @RequestMapping("/add")
    public String addCustomerView(Model model) {


        CustomerForm customerForm = new CustomerForm();

        model.addAttribute("customerForm", customerForm);

        return "/user/add-customer";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String saveCustomer(@Valid @ModelAttribute CustomerForm customerForm, BindingResult result, Authentication authentication, HttpSession session) {


        // validating the add Customer form:

        if (result.hasErrors()) {

            Message message = Message.builder()
            .content("Invalid Field(s)!")
            .type(MessageType.red)
            .build();

            session.setAttribute("message", message);

            return "user/add-customer";
            
        } 

        String username = Helper.getEmailOfLoggedInUser(authentication);


        // processing image

        // logger.info("file information: {}", CustomerForm.getCustomerImage().getOriginalFilename());

        


        User user = userService.getUserByEmail(username);

        Customer customer = new Customer();

        customer.setName(customerForm.getName());
        customer.setEmail(customerForm.getEmail());
        customer.setPhoneNumber(customerForm.getPhoneNumber());
        customer.setAddress(customerForm.getAddress());
	customer.setPincode(customerForm.getPincode());
	customer.setProductDetails(customerForm.getProductDetails());
	customer.setQuantity(customerForm.getQuantity());
	customer.setAmount(customerForm.getAmount());
	customer.setCourierCompany(customerForm.getCourierCompany());
	customer.setTrackingId(customerForm.getTrackingId());
	customer.setDeliveryMode(customerForm.getDeliveryMode());



        if (customerForm.getCustomerImage() != null && !customerForm.getCustomerImage().isEmpty()) {
            

            String fileName = UUID.randomUUID().toString();
    
            String fileURL = imageService.uploadImage(customerForm.getCustomerImage(), fileName);
    
    
            customer.setPicture(fileURL);
            customer.setCloudinaryImagePublicId(fileName);

        }




        customer.setUser(user);


        customerService.save(customer);

	logger.info("printing customer form");
        System.out.println(customerForm);

        Message message = Message.builder()
        .content("New Customer Added Successfully!")
        .type(MessageType.green)
        .build();

        session.setAttribute("message", message);

        return "redirect:/user/customers/add";
    }


    @RequestMapping
    public String viewCustomers(@RequestParam(value = "page", defaultValue = "0") int page, 
    @RequestParam(value = "size", defaultValue = Constants.PAGE_SIZE + "") int size, 
    @RequestParam(value = "sortBy", defaultValue = "name") String sortBy, 
    @RequestParam(value = "direction", defaultValue = "asc") String direction, 
    @ModelAttribute CustomerSearchForm customerSearchForm,
    Model model, Authentication authentication) {

        String username = Helper.getEmailOfLoggedInUser(authentication);

        User user = userService.getUserByEmail(username);

        Page<Customer> pageCustomer = customerService.getByUser(user, page, size, sortBy, direction);

        model.addAttribute("pageCustomer", pageCustomer);

        model.addAttribute("pageSize", Constants.PAGE_SIZE);

        model.addAttribute("customerSearchForm", customerSearchForm);

        return "/user/customers";
    }

    


    @RequestMapping("/search")
    public String searchHandler(

        // @RequestParam("field") String field,
        // @RequestParam("keyword") String value,
        @ModelAttribute CustomerSearchForm customerSearchForm,
        @RequestParam(value = "page", defaultValue = "0") int page, 
        @RequestParam(value = "size", defaultValue = Constants.PAGE_SIZE + "") int size, 
        @RequestParam(value = "sortBy", defaultValue = "name") String sortBy, 
        @RequestParam(value = "direction", defaultValue = "asc") String direction, 
        Model model, Authentication authentication
        ) {

		logger.info("printing search form field and value");

        System.out.println("field: " + customerSearchForm.getField() + "    keyword: " + customerSearchForm.getValue());

        var user = userService.getUserByEmail(Helper.getEmailOfLoggedInUser(authentication));

        Page<Customer> pageCustomer = null;

        if (customerSearchForm.getField().equalsIgnoreCase("name")) {

            pageCustomer = customerService.searchByName(customerSearchForm.getValue(), page, size, sortBy, direction, user);
        }

        else if (customerSearchForm.getField().equalsIgnoreCase("email")) {

            pageCustomer = customerService.searchByEmail(customerSearchForm.getValue(), page, size, sortBy, direction, user);
        }

        else if (customerSearchForm.getField().equalsIgnoreCase("phone")) {

            pageCustomer = customerService.searchByPhone(customerSearchForm.getValue(), page, size, sortBy, direction, user);
        }

	logger.info("search handler customer page");

        System.out.println("pageCustomer: " + pageCustomer);

        model.addAttribute("pageSize", Constants.PAGE_SIZE);

        model.addAttribute("customerSearchForm", customerSearchForm);

        model.addAttribute("pageCustomer", pageCustomer);

        return "user/search";
    }

    @RequestMapping("/delete/{customerId}")
    public String deleteCustomer(@PathVariable String customerId, HttpSession session) {

        customerService.deleteCustomer(customerId);

        session.setAttribute("message", Message.builder()
        .content("Customer is deleted succesfully!")
        .type(MessageType.green)
        .build());

        

        return "redirect:/user/customers";
    }

    @GetMapping("/view/{customerId}")
    public String updateCustomerFormView(@PathVariable("customerId") String customerId, Model model) {

        var customer = customerService.geCustomerById(customerId);

        CustomerForm customerForm = new CustomerForm();

        customerForm.setName(customer.getName());
        customerForm.setEmail(customer.getEmail());
        customerForm.setAddress(customer.getAddress());
        customerForm.setPhoneNumber(customer.getPhoneNumber());
	customerForm.setPincode(customer.getPincode());
	customerForm.setProductDetails(customer.getProductDetails());
	customerForm.setQuantity(customer.getQuantity());
	customerForm.setAmount(customer.getAmount());
	customerForm.setCourierCompany(customer.getCourierCompany());
	customerForm.setTrackingId(customer.getTrackingId());
	customerForm.setDeliveryMode(customer.getDeliveryMode());


        customer.setPicture(customer.getPicture());
        model.addAttribute("customerForm", customerForm);
        model.addAttribute("customerId", customerId);

        return "user/update-customer-view";
    }


    @PostMapping("/update/{customerId}")
    public String updateCustomer(@PathVariable String customerId, @Valid @ModelAttribute CustomerForm customerForm,BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {

		model.addAttribute("message", Message.builder()
        .content("Error while updating customer!")
        .type(MessageType.red)
        .build());

		logger.info("error while updating customer: " + bindingResult.toString());
            
            return "user/update-customer-view";
        }


        var customer = customerService.geCustomerById(customerId);

        customer.setId(customerId);

        customer.setName(customerForm.getName());
        customer.setEmail(customerForm.getEmail());
        customer.setPhoneNumber(customerForm.getPhoneNumber());
        customer.setAddress(customerForm.getAddress());
        customer.setPincode(customerForm.getPincode());
	customer.setProductDetails(customerForm.getProductDetails());
	customer.setQuantity(customerForm.getQuantity());
	customer.setAmount(customerForm.getAmount());
	customer.setCourierCompany(customerForm.getCourierCompany());
	customer.setTrackingId(customerForm.getTrackingId());
	customer.setDeliveryMode(customerForm.getDeliveryMode());



        if (customerForm.getCustomerImage() != null && !customerForm.getCustomerImage().isEmpty()) {
            

            String fileName = UUID.randomUUID().toString();

            String imageURL = imageService.uploadImage(customerForm.getCustomerImage(), fileName);

            customer.setCloudinaryImagePublicId(fileName);

            customer.setPicture(imageURL);
            customerForm.setPicture(imageURL);

        }


        customerService.update(customer);
	logger.info("customer is updated");

        model.addAttribute("message", Message.builder()
        .content("Customer updated!")
        .type(MessageType.green)
        .build());

        // return "redirect:/user/Customers/view/" + CustomerId;
        return "redirect:/user/customers";
    }
}

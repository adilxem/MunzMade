package com.adil.MunzMade.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adil.MunzMade.Model.Customer;
import com.adil.MunzMade.Services.CustomerService;

@RestController
@RequestMapping("/user/api")
public class ApiController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/customer/{customerId}")
    public Customer geCustomer(@PathVariable String customerId) {

        return customerService.geCustomerById(customerId);
    }

}

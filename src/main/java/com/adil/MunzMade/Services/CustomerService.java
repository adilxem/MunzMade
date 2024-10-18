package com.adil.MunzMade.Services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.adil.MunzMade.Helper.ResourceNotFoundExcepiton;
import com.adil.MunzMade.Model.Customer;
import com.adil.MunzMade.Model.User;
import com.adil.MunzMade.Repository.CustomerRepository;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    public Customer save(Customer customer) {

        String customerId = UUID.randomUUID().toString();

        customer.setId(customerId);

        return customerRepository.save(customer);
    }


    public List<Customer> getAllCustomers() {

        return customerRepository.findAll();
    }

    public Customer geCustomerById(String id) {

        return customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundExcepiton("Customer not found"));
    }

    public void deleteCustomer(String id){

        customerRepository.deleteById(id);
    }

    public List<Customer> getByUserId(String userId) {

        return customerRepository.findByUserId(userId);
    }

    public Page<Customer> getByUser(User user, int page, int size, String sortBy, String direction) {


        Sort sort = direction.equals("desc")? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

        var pageable = PageRequest.of(page, size, sort);

        return customerRepository.findByUser(user, pageable);
        
    }

    public Page<Customer> searchByName (String nameKeyword, int page, int size, String sortBy, String direction, User user) {

        Sort sort = direction.equals("desc")? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

        var pageable = PageRequest.of(page, size, sort);

        return customerRepository.findByUserAndNameContaining(user, nameKeyword, pageable);
    }

    public Page<Customer> searchByEmail (String emailKeyword,  int page, int size, String sortBy, String direction, User user) {


        Sort sort = direction.equals("desc")? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

        var pageable = PageRequest.of(page, size, sort);

        return customerRepository.findByUserAndEmailContaining(user, emailKeyword, pageable);
    }

    public Page<Customer> searchByPhone (String phoneKeyword,  int page, int size, String sortBy, String direction, User user) {


        Sort sort = direction.equals("desc")? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

        var pageable = PageRequest.of(page, size, sort);

        return customerRepository.findByUserAndPhoneNumberContaining(user, phoneKeyword, pageable);
    }

    

    public Customer update(Customer customer) {
        
        var customerOld = customerRepository.findById(customer.getId()).orElseThrow(() -> new ResourceNotFoundExcepiton("Customer Not Found"));

        customerOld.setName(customer.getName());
        customerOld.setEmail(customer.getEmail());
        customerOld.setPhoneNumber(customer.getPhoneNumber());
        customerOld.setAddress(customer.getAddress());
	customerOld.setPincode(customer.getPincode());
	customerOld.setProductDetails(customer.getProductDetails());
	customerOld.setQuantity(customer.getQuantity());
	customerOld.setAmount(customer.getAmount());
	customerOld.setCourierCompany(customer.getCourierCompany());
	customerOld.setTrackingId(customer.getTrackingId());
	customerOld.setDeliveryMode(customer.getDeliveryMode());

        customerOld.setPicture(customer.getPicture());
        customerOld.setCloudinaryImagePublicId(customer.getCloudinaryImagePublicId());


        return customerRepository.save(customerOld);
    }
}

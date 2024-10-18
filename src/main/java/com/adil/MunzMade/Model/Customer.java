package com.adil.MunzMade.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id
    private String id;

    private String name;

    private String phoneNumber;
    
    private String address;

    private String pincode;
    
    private String email;

    private String productDetails;

    private String quantity;

    private String amount;

    private String courierCompany;

    private String trackingId;

    private String deliveryMode;

    private String picture;

    private String cloudinaryImagePublicId;


    @ManyToOne
    @JsonIgnore
    private User user;
}

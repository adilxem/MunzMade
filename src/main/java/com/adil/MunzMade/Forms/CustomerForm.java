package com.adil.MunzMade.Forms;

import org.springframework.web.multipart.MultipartFile;

import com.adil.MunzMade.Validators.ValidFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CustomerForm {


    private String id;

    @NotBlank(message = "Name is required!")
    private String name;

    @Email(message = "Invalid email address")
    private String email;

    @NotBlank(message = "Phone is required!")
    @Pattern(regexp = "^[0-9]{10}$", message = "Invalid Phone Number!")
    private String phoneNumber;

    @NotBlank(message = "Address is required!")
    private String address;

    @Pattern(regexp = "^[0-9]{6}$", message = "Invalid Pincode!")
    @NotBlank(message = "Pincode is required!")
    private String pincode;
    
    @NotBlank(message = "Enter product details!")
    private String productDetails;

    @Pattern(regexp = "^[1-9][0-9]?$", message = "Invalid Quantity!")
    @NotBlank(message = "Enter the quantity!")
    private String quantity;

    @Pattern(regexp = "^[1-9][0-9]*$", message = "Invalid Amount!")
    @NotBlank(message = "Enter the amount!")
    private String amount;

    private String courierCompany;

    private String trackingId;

    private String deliveryMode;


    @ValidFile
    private MultipartFile customerImage;

    private String picture;

}

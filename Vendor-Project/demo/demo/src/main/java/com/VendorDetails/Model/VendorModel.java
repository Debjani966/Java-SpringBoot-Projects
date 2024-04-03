package com.VendorDetails.Model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.math.BigInteger;

@Entity
@Table(name = "VendorInfo")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VendorModel {

    @Id
    @NotNull(message = "Vendor Id cannot be Null")
    private String vendorID;
    @NotNull(message = "Vendor Name cannot be Null")
    private String vendorName;
    @Email(message = "Vendor Email is not correct")
    private String vendorEmail;
    @NotBlank(message = "Vendor Address cannot be blank")
    private String vendorAddress;
    @NotBlank(message = "Vendor Phone Number cannot be blank")
    private String vendorPhoneNumber;
}

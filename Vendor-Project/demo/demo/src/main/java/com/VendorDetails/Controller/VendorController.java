package com.VendorDetails.Controller;

import com.VendorDetails.Model.VendorModel;
import com.VendorDetails.Response.ResponseHandler;
import com.VendorDetails.Service.VendorService;
import com.VendorDetails.util.APIClass;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(APIClass.Vendor.PATH)
public class VendorController {
    @Autowired
    VendorService vendorService;

    @GetMapping(APIClass.Vendor.Get_Id)
    public ResponseEntity<Object> getVendorModelByID(@PathVariable("vendorID") String vendorID)
    {
        return ResponseHandler.responseBuilder(
                "Requested Vendor are here", HttpStatus.OK,
                vendorService.getVendorModelByID(vendorID)
                );
    }
    @GetMapping
    public List<VendorModel> getAllVendorModel()
    {
        return vendorService.getAllVendorModel();
    }

    @PostMapping
    public String createVendorModel(@Valid @RequestBody VendorModel vendorModel)
    {
        vendorService.createVendorModel(vendorModel);
        return "Vendor Created successfully";
    }

    @PutMapping
    public String updateVendorModel(@RequestBody VendorModel vendorModel)
    {
        vendorService.updateVendorModel(vendorModel);
        return "Vendor Updated successfully";
    }

    @DeleteMapping(APIClass.Vendor.Get_Id)
    public String deleteVendorModel(@PathVariable("vendorID") String vendorID)
    {
        vendorService.deleteVendorModel(vendorID);
        return "Vendor Deleted Successfully";
    }

}

package com.VendorDetails.Service.Impl;

import com.VendorDetails.Model.VendorModel;
import com.VendorDetails.Repository.VendorRepository;
import com.VendorDetails.Service.VendorService;
import com.VendorDetails.exception.VendorNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VendorServiceImpl implements VendorService {

    VendorRepository vendorRepository;

    public VendorServiceImpl(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    @Override
    public String createVendorModel(VendorModel vendorModel) {
        vendorRepository.save(vendorModel);
        return "Vendor created successful";
    }

    @Override
    public String updateVendorModel(VendorModel vendorModel) {
        vendorRepository.save(vendorModel);
        return "Vendor update successful";
    }

    @Override
    public String deleteVendorModel(String vendorID) {
        vendorRepository.deleteById(vendorID);
        return "Vendor deleted successful";
    }

    @Override
    public VendorModel getVendorModelByID(String vendorID) {
        if(vendorRepository.findById(vendorID).isEmpty())
        {
            throw new VendorNotFoundException("Requested Vendor Not Exist");
        }
        return vendorRepository.findById(vendorID).get();
    }

    @Override
    public List<VendorModel> getAllVendorModel() {
        return vendorRepository.findAll();
    }
}

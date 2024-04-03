package com.VendorDetails.Service;

import com.VendorDetails.Model.VendorModel;

import java.util.List;

public interface VendorService {
    public String createVendorModel(VendorModel vendorModel);
    public String updateVendorModel(VendorModel vendorModel);
    public String deleteVendorModel(String vendorID);
    public VendorModel getVendorModelByID(String vendorID);
    public List<VendorModel> getAllVendorModel();
}

package com.VendorDetails.Repository;

import com.VendorDetails.Model.VendorModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface VendorRepository extends JpaRepository<VendorModel, String> {
}

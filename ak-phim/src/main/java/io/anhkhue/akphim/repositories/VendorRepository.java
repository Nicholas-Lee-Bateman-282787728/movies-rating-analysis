package io.anhkhue.akphim.repositories;

import io.anhkhue.akphim.models.mining.report.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VendorRepository extends JpaRepository<Vendor, Integer> {

    List<Vendor> findAll();
}

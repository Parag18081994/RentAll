package com.rental.RentAllv1.repository;

import com.rental.RentAllv1.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
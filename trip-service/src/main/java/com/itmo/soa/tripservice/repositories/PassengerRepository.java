package com.itmo.soa.tripservice.repositories;

import model.entity.Passenger;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassengerRepository extends CrudRepository<Passenger, String> {

}

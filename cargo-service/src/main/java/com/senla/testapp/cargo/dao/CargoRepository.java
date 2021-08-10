package com.senla.testapp.cargo.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.senla.testapp.cargo.model.Cargo;

public interface CargoRepository extends MongoRepository<Cargo, Long> {
    
    
}

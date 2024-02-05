package com.example.reporter.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.reporter.entities.Operation;

public interface OperationRepository extends CrudRepository<Operation, Long> {
    List<Operation> findAllByOrderByOpStoreNameAscOpIdDesc();
}

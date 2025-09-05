package com.example.dual_entity_managers.repos.first;

import com.example.dual_entity_managers.model.First;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface FirstRepository extends CrudRepository<First, Long> {

    Optional<First> findById(long id);

}

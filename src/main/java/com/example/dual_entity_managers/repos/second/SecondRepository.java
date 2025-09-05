package com.example.dual_entity_managers.repos.second;

import com.example.dual_entity_managers.model.Second;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SecondRepository extends CrudRepository<Second, Long> {

    Optional<Second> findById(long id);

}

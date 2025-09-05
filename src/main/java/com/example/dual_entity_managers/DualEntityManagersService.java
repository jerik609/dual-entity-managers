package com.example.dual_entity_managers;

import com.example.dual_entity_managers.model.First;
import com.example.dual_entity_managers.model.Second;
import com.example.dual_entity_managers.repos.first.FirstRepository;
import com.example.dual_entity_managers.repos.second.SecondRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DualEntityManagersService {

    private static final Log log = LogFactory.getLog(DualEntityManagersService.class);

    private final FirstRepository firstRepository;
    private final SecondRepository secondRepository;

    public DualEntityManagersService(
            FirstRepository firstRepository,
            SecondRepository secondRepository) {
        this.firstRepository = firstRepository;
        this.secondRepository = secondRepository;
    }

    //@Transactional(transactionManager = "firstPlatformTransactionManager")
    public void first(String data, String extraData) {
        final var stuff = new First();
        stuff.setData(data);
        stuff.setExtended_data(extraData);
        stuff.setModified(LocalDateTime.now());
        firstRepository.save(stuff);
    }

    //@Transactional(transactionManager = "secondPlatformTransactionManager")
    public void second(String data, String extraData) {
        final var swag = new Second();
        swag.setData(data);
        swag.setExtended_data(extraData);
        swag.setModified(LocalDateTime.now());
        secondRepository.save(swag);
    }

}

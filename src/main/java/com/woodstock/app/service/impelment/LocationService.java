package com.woodstock.app.service.impelment;

import com.woodstock.app.entity.Location;
import com.woodstock.app.repositorty.LocationPostRepository;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class LocationService extends BaseServiceImpl<LocationPostRepository, Location>{

    @Autowired
    protected LocationService(LocationPostRepository repository, EntityManager entityManager) {
        super(repository, entityManager);
    }

    public Location Update(Location location, UUID id){

        log.info("try get location by id");
        Location oldLocation = findbyId(id);

        log.info("update old location");
        location.setId(oldLocation.getId());

        return save(location);

    }

    public boolean isExists(UUID id){

        return repository.existsById(id);

    }

}

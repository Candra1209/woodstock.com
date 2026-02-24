package com.woodstock.app.repositorty;

import com.woodstock.app.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface LocationPostRepository extends JpaRepository<Location, UUID>, JpaSpecificationExecutor<Location> {
}

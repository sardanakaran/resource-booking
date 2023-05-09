package com.osttra.resource.repository;

import com.osttra.resource.model.Resource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResourceRepository extends JpaRepository<Resource, Long> {
    List<Resource> findByDocStationEnabled(boolean docStationEnabled);

    List<Resource> findByNameContaining(String title);
}

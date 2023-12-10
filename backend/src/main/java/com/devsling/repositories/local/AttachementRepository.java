package com.devsling.repositories.local;

import org.springframework.stereotype.Repository;

import com.devsling.models.local.Attachement;
import com.devsling.repositories.config.AbstractRepository;

@Repository
public interface AttachementRepository extends AbstractRepository<Attachement, String> {

}

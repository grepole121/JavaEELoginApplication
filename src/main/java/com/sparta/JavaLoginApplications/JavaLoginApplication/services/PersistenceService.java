package com.sparta.JavaLoginApplications.JavaLoginApplication.services;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@ApplicationScoped
public class PersistenceService {

    @PersistenceContext(unitName = "users")
    EntityManager entityManager;

    public EntityManager getEntityManager() {
        return entityManager;
    }
}
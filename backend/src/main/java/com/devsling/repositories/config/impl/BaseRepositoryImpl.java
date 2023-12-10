package com.devsling.repositories.config.impl;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import com.devsling.repositories.config.AbstractDSLRepository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseRepositoryImpl<T, U> extends SimpleJpaRepository<T, U>
        implements AbstractDSLRepository<T, U> {

    private final EntityManager entityManager;

    private final JPAQueryFactory jpaQueryFactory;

    protected BaseRepositoryImpl(Class<T> domainClass, EntityManager entityManager) {
        super(domainClass, entityManager);
        this.entityManager = entityManager;
        this.jpaQueryFactory = new JPAQueryFactory(entityManager);
    }

}

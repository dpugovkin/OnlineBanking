package com.pugovkin.onlinebanking.repository;

import com.pugovkin.onlinebanking.entity.AbstractEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface AbstractRepository<T extends AbstractEntity> extends JpaRepository<T, Serializable> {
}

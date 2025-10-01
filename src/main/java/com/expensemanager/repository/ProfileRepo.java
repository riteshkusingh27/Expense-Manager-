package com.expensemanager.repository;

import com.expensemanager.entity.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepo extends JpaRepository<ProfileEntity, Integer>{

    Optional<ProfileEntity> findByEmail(String email);

}

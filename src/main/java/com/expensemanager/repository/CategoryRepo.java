package com.expensemanager.repository;


import com.expensemanager.entity.CategoryEntity;
import com.expensemanager.entity.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepo extends JpaRepository<CategoryEntity,Long> {

     // select * from category where profile_id=?
    List<CategoryEntity> findByProfileId(Long profileId);
    // select * from category where id=? and profile_id=?
      Optional<CategoryEntity> findByIdAndProfileId(Long id, Long profile_id);
      // select * from category where type=? and profile_id=?
     List<CategoryEntity> findByTypeAndProfileId(String type, Long profileId);
    //
     Boolean existsByNameAndProfileId(String name, Long profileId);
}

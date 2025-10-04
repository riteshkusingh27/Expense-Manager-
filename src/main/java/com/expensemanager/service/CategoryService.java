package com.expensemanager.service;

import com.expensemanager.dto.CategoryEntity;
import com.expensemanager.dto.Categorydto;
import com.expensemanager.entity.ProfileEntity;
import com.expensemanager.repository.CategoryRepo;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@Data
@RequiredArgsConstructor
public class CategoryService {
    private final ProfileService profileService;

    private final CategoryRepo categoryRepo;

    // save category

    public Categorydto saveCategory(Categorydto categorydto){
        // get profile id
        ProfileEntity profile = profileService.getcurrentProfile();
        if(categoryRepo.existsByNameAndProfileId(categorydto.getName(),profile.getId())){
            throw new ResponseStatusException(HttpStatus.CONFLICT , "Category with name "+categorydto.getName()+" already exists");
        }
        CategoryEntity newcategory = toEntity(categorydto,profile);
        newcategory = categoryRepo.save(newcategory);

        return toDto(newcategory);

    }






















    // helper methods to convert dto to entity and entity to dto
    private CategoryEntity toEntity(Categorydto categorydto , ProfileEntity profileEntity){
        return CategoryEntity.builder()
                .name(categorydto.getName())
                .icon(categorydto.getIcon())
                .type(categorydto.getType())
                .profile(profileEntity)
                .build();
    }

    private Categorydto  toDto(CategoryEntity categoryEntity){
        return Categorydto.builder()
                .id(categoryEntity.getId())
                .profileId(categoryEntity.getProfile()!=null ? categoryEntity.getProfile().getId() : null)
                .name(categoryEntity.getName())
                .icon(categoryEntity.getIcon())
                .createdAt(categoryEntity.getCreatedAt())
                .updatedAt(categoryEntity.getUpdatedAt())
                .build();
    }
}

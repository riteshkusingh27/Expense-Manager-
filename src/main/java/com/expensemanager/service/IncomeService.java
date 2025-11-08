package com.expensemanager.service;


import com.expensemanager.dto.ExpenseDto;
import com.expensemanager.dto.IncomeDto;
import com.expensemanager.entity.CategoryEntity;

import com.expensemanager.entity.ExpenseEntity;
import com.expensemanager.entity.IncomeEntity;
import com.expensemanager.entity.ProfileEntity;

import com.expensemanager.repository.CategoryRepo;
import com.expensemanager.repository.IncomeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IncomeService {

    private final CategoryRepo categoryRepo;
    private final IncomeRepo incomerepo;
    private final ProfileService profileService;

    public IncomeDto
    addExpense(IncomeDto dto){
        ProfileEntity profile =  profileService.getcurrentProfile();
        CategoryEntity category =  categoryRepo.findById(dto.getCategoryId()).orElseThrow(()-> new RuntimeException(("Category not found")));
        IncomeEntity newincome = toEntity(dto,profile,category);
        newincome = incomerepo.save(newincome);
        return toDto(newincome);


    }


    // helper methods
    private IncomeEntity toEntity(IncomeDto dto , ProfileEntity profile , CategoryEntity category){
        return IncomeEntity.builder()
                .name(dto.getName())
                .icon(dto.getIcon())
                .amount(dto.getAmount())
                .date(dto.getDate())
                .profile(profile)
                .category(category)
                .build();
    }
    //     for the front end
    private IncomeDto toDto(IncomeEntity entity){
        return  IncomeDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .icon(entity.getIcon())
                .categoryId(entity.getCategory() !=null ? entity.getCategory().getId() : null)
                .amount(entity.getAmount())
                .categoryName(entity.getCategory()!=null ? entity.getCategory().getName() : null)
                .date(entity.getDate())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}

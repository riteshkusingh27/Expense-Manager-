package com.expensemanager.service;

import com.expensemanager.dto.ExpenseDto;
import com.expensemanager.entity.CategoryEntity;
import com.expensemanager.entity.ExpenseEntity;
import com.expensemanager.entity.ProfileEntity;
import com.expensemanager.repository.CategoryRepo;
import com.expensemanager.repository.ExpenseRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExpenseService {
    private final CategoryRepo categoryRepo;
    private final ExpenseRepo expenseRepo;
    private final ProfileService profileService;


    // adding a new expense to the databse
    public ExpenseDto addExpense(ExpenseDto dto){
       ProfileEntity profile =  profileService.getcurrentProfile();
      CategoryEntity category =  categoryRepo.findById(dto.getCategoryId()).orElseThrow(()-> new RuntimeException(("Category not found")));
      ExpenseEntity newexpense = toEntity(dto,profile,category);
      newexpense = expenseRepo.save(newexpense);
      return toDto(newexpense);


    }


    // helper methods
    private ExpenseEntity toEntity(ExpenseDto dto , ProfileEntity profile , CategoryEntity category){
          return ExpenseEntity.builder()
                  .name(dto.getName())
                  .icon(dto.getIcon())
                  .amount(dto.getAmount())
                  .date(dto.getDate())
                  .profile(profile)
                  .category(category)
                  .build();
    }
//     for the front end
    private ExpenseDto toDto(ExpenseEntity entity){
       return  ExpenseDto.builder()
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

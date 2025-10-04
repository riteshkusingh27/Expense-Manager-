package com.expensemanager.controller;


import com.expensemanager.dto.Categorydto;
import com.expensemanager.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<Categorydto> saveCategory( @RequestBody  Categorydto categorydto){
        Categorydto savedCategory = categoryService.saveCategory(categorydto);
        return ResponseEntity.status(201).body(savedCategory);
    }
}

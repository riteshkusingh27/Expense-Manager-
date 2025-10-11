package com.expensemanager.controller;


import com.expensemanager.dto.Categorydto;
import com.expensemanager.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {
    // response entity can return any type of data obect(t) as the body
    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<Categorydto> saveCategory(@RequestBody Categorydto categorydto) {
        Categorydto savedCategory = categoryService.saveCategory(categorydto);
        return ResponseEntity.status(201).body(savedCategory);
    }

    @GetMapping
    public ResponseEntity<List<Categorydto>> getCategories() {
        List<Categorydto> Categories = categoryService.getCategoriesForCurrentProfile();
        return ResponseEntity.ok(Categories);
    }

    @GetMapping("/{type}")
    public ResponseEntity<List<Categorydto>> getCategoriesByType(@PathVariable String type) {
        List<Categorydto> list = categoryService.getCategoriesByTypeForCurrentProfile(type);
        return ResponseEntity.ok(list);
    }

    @PutMapping("/{categoryid}")
    public ResponseEntity<Categorydto> updateCategory(@PathVariable Long categoryid, @RequestBody Categorydto dto){
        Categorydto updatedCategory = categoryService.updateCategory(categoryid,dto);
        return ResponseEntity.ok(updatedCategory);
    }

}

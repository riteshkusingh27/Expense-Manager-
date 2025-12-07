package com.expensemanager.controller;


import com.expensemanager.dto.ExpenseDto;
import com.expensemanager.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/expenses")
public class ExpenseContoller {

    private final ExpenseService expenseService;
     @PostMapping
    public ResponseEntity<ExpenseDto> addexpense(@RequestBody ExpenseDto dto ){
        ExpenseDto saved = expenseService.addExpense(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
      @GetMapping
    public ResponseEntity<List<ExpenseDto>> getExpenses(){
        List<ExpenseDto> expenses = expenseService.getCurrentMonthExpenses();
        return ResponseEntity.ok(expenses);
    }
}

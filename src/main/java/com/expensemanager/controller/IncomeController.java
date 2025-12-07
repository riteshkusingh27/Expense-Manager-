package com.expensemanager.controller;

import com.expensemanager.dto.ExpenseDto;
import com.expensemanager.dto.IncomeDto;
import com.expensemanager.service.ExpenseService;
import com.expensemanager.service.IncomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/incomes")
public class IncomeController {

    private final IncomeService incomeService;
     @PostMapping
    public ResponseEntity<IncomeDto> addexpense(@RequestBody IncomeDto dto ){
        IncomeDto saved = incomeService.addExpense(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping
    public ResponseEntity<List<IncomeDto>> getExpenses(){
        List<IncomeDto> expenses = incomeService.getCurrentMonthIncome();
        return ResponseEntity.ok(expenses);
    }
}


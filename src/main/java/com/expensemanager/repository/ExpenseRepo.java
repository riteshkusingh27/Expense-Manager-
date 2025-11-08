package com.expensemanager.repository;

import com.expensemanager.entity.ExpenseEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface ExpenseRepo  extends JpaRepository<ExpenseEntity,Long> {


    // find expense for current logged user

      List<ExpenseEntity> findByProfileIdOrderByDateDesc(Long profileId);

      // top 5 expense for the logges in user
    // select * from tbl_exoense where profile_id = ?1  order by date desc limit 5
       List<ExpenseEntity> findTop5ByProfileIdOrderByDateDesc(Long profileId);


       //  summ of total expenses   JPQL query
    @Query("SELECT SUM(e.amount) FROM ExpenseEntity  e WHERE e.profile.id = : profileId")
    BigDecimal findTotalExpenseByProfileId(@Param("profileId") Long profileId);

  // filter methods

  List<ExpenseEntity>  findByProfileIdAndDateBetweenAndNameContainingIgnoreCase(
            Long profileId,
            LocalDate startDate,
            LocalDate endDate,
            String keyword,
            Sort sort
    );


   List<ExpenseEntity> findByProfileIdAndDateBetween(Long profileId , LocalDate startdate , LocalDate enddate);



}

package com.expensemanager.repository;

import com.expensemanager.entity.ExpenseEntity;
import com.expensemanager.entity.IncomeEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface IncomeRepo  extends JpaRepository<IncomeEntity,Long> {
    // find expense for current logged user

    List<IncomeEntity> findByProfileIdOrderByDateDesc(Long profileId);

    // top 5 expense for the logged in user

    // select * from tbl_exoense where profile_id = ?1  order by date desc limit 5
    List<IncomeEntity> findTop5ByProfileIdOrderByDateDesc(Long profileId);


    //  summ of total expenses   JPQL query
    @Query("SELECT SUM(i.amount) FROM IncomeEntity  i WHERE i.profile.id = : profileId")
    BigDecimal findTotalExpenseByProfileId(@Param("profileId") Long profileId);

    // filter methods

    List<IncomeEntity>  findByProfileIdAndDateBetweenAndNameContainingIgnoreCase(
            Long profileId,
            LocalDate startDate,
            LocalDate endDate,
            String keyword,
            Sort sort
    );


    List<IncomeEntity> findByProfileIdAndDateBetween(Long profileId , LocalDate startdate , LocalDate enddate);
}

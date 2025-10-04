package com.expensemanager.dto;


import com.expensemanager.entity.ProfileEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Categorydto {


    private Long id ;
    private Long profileId;
    private String name ;
    private String icon ;
     private String type ;
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}

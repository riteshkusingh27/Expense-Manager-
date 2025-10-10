package com.expensemanager.entity;


import com.expensemanager.entity.ProfileEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_categories")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id ;
    private String name ;
    @Column(updatable = false)    // when ever we update the entity this field will not be updated
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    private String type ;
    private String icon ;
    @ManyToOne(fetch = FetchType.LAZY)  // the only parent entity is loaded profile entity is not loaded
    @JoinColumn(name = "profile_id", nullable = false)
    private ProfileEntity profile ;
}

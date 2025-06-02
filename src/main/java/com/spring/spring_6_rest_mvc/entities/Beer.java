package com.spring.spring_6_rest_mvc.entities;

import com.spring.spring_6_rest_mvc.models.BeerStyle;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Beer {

    // marks primary key field (id)
    @Id
    // configures automatic primary key (id) generation
    @GeneratedValue(generator = "UUID")
    @UuidGenerator
    // specify attribute
    @Column(length = 36, columnDefinition = "varchar", updatable = false, nullable = false)
    private UUID id;

    // hibernate locking uses this, auto-increments it, checks if version to be written is stale
    @Version
    private Integer version;

    private String beerName;
    private BeerStyle beerStyle;
    private String upc;
    private Integer quantityOnHand;
    private BigDecimal price;
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;

}

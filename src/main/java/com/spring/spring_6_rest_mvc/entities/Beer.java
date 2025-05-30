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

    @Id // marks primary key field (id)
    @GeneratedValue(generator = "UUID") // configures automatic key generation
    @UuidGenerator
    private UUID id;
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

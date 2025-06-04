package com.spring.spring_6_rest_mvc.entities;

import com.spring.spring_6_rest_mvc.models.BeerStyle;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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

    @NotBlank
    @Size(max = 50) // throws ConstraintViolationException (validates before even trying to save to database - best practice)
    // @Column(length = 50) // throws DataIntegrityViolationException (validates when trying to save to database)
    private String beerName;
    @NotNull
    private BeerStyle beerStyle;
    @NotBlank
    @Pattern(regexp = "\\d+")
    @Size(min = 12, max = 12)
    private String upc;
    @PositiveOrZero
    @NotNull
    private Integer quantityOnHand;
    @Positive
    @NotNull
    private BigDecimal price;

    private LocalDateTime createdDate;
    private LocalDateTime updateDate;


}

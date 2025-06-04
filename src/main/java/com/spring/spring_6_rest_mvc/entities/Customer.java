package com.spring.spring_6_rest_mvc.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Customer {

    @Id
    @GeneratedValue(generator = "UUID")
    @UuidGenerator
    @Column(length = 36, columnDefinition = "varchar", updatable = false, nullable = false)
    private UUID id;

    @Version
    private Integer version;

    @NotBlank
    @Size(max = 50) // throws ConstraintViolationException (validates before even trying to save to database)
    // @Column(length = 50) // throws DataIntegrityViolationException (validates when trying to save to database)
    private String customerName;

    private LocalDateTime createDate;
    private LocalDateTime lastModifiedDate;

}

package com.spring.spring_6_rest_mvc.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import lombok.*;

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
    private UUID id;
    @Version
    private Integer version;

    private String customerName;
    private LocalDateTime createDate;
    private LocalDateTime lastModifiedDate;

}

package com.spring.spring_6_rest_mvc.models;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class CustomerDTO {

    private UUID id;
    private String customerName;
    private Integer version;
    private LocalDateTime createDate;
    private LocalDateTime lastModifiedDate;

}

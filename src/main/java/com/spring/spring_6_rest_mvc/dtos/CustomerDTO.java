package com.spring.spring_6_rest_mvc.dtos;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class CustomerDTO {

    private String customerName;
    private UUID id;
    private Integer version;
    private LocalDateTime createDate;
    private LocalDateTime lastModifiedDate;

}

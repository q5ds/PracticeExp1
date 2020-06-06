package com.Domain.Order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdercreateRequest {
    private Integer gid;
    private Integer amount;
    private String buyer;
}

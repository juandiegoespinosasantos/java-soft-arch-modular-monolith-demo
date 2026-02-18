package com.jdespinosa.demo.restaurant.placeorder.model.dto;


import com.jdespinosa.demo.restaurant.placeorder.model.enums.OrderStatuses;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Order data transfer object.
 *
 * @author juandiegoespinosasantos@outlook.com
 * @version Feb 17, 2026
 * @since 17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 4719641294289679235L;

    private Long id;
    private String customerName;
    private String customerEmail;
    private int tableNumber;
    private OrderStatuses status;
    private double total;
    private List<OrderItemDTO> items = new ArrayList<>();
    private Long createdAt;
}
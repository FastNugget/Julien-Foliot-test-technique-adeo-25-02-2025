package com.yaourt.service.stock.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockDtoREQ {

    // -- Data
    private String productName;
    private int quantity;
    private int quantityMultiple;
    private int deliveryDelay;

}

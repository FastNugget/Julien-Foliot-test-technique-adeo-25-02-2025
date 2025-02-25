package com.onetranslate.service.business.stock.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockDtoREQ {

    // -- Data
    private String productName;
    private int quantity;
    private int deliveryDelay;

}

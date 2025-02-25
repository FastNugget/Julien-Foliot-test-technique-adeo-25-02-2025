package com.onetranslate.service.business.stock.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document("Stock")
public class StockDao {

    // -- Mongoid
    @Id private String id;

    // -- Data
    private String productName;
    private int quantity;
    private int deliveryDelay;

}

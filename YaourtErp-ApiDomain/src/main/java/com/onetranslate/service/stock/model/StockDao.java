package com.onetranslate.service.stock.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class StockDao {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    // -- Data
    private String productName;
    private int quantity;
    private int quantityMultiple;
    private int deliveryDelay;

}

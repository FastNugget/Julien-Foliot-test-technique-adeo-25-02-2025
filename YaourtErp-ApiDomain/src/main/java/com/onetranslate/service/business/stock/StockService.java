package com.onetranslate.service.business.stock;

import com.onetranslate.service.business.stock.model.StockDao;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j(topic = "StockService")
@RequiredArgsConstructor
@Service()
public class StockService {

    // -- VARS
    private final MongoTemplate mongoTemplate;


    // -- LIFECYCLE ----------------------------------------------------------------------------------------------------

    @PostConstruct
    public void init() {}


    // -- CALLBACKS ----------------------------------------------------------------------------------------------------

    public StockDao createStock(String productName, int initialQuantity){

        // -- Get
        if(Optional.ofNullable(this.mongoTemplate.findOne(new Query().addCriteria(Criteria.where("productName").is(productName)), StockDao.class)).isPresent()){

            // -- Error
            throw new RuntimeException("Stock already exists");

        }

        StockDao stockDao = new StockDao();
        stockDao.setProductName(productName);
        stockDao.setQuantity(initialQuantity);

        // -- Update
        stockDao = this.mongoTemplate.save(stockDao);

        // -- Return
        return stockDao;

    }

    public StockDao decreaseStock(String productName, int quantity){

        // -- Get
        StockDao stockDao = Optional.ofNullable(this.mongoTemplate.findOne(new Query().addCriteria(Criteria.where("productName").is(productName)), StockDao.class))
                .orElseThrow(() -> new RuntimeException("Stock not found"));

        // -- Decrease
        stockDao.setQuantity(stockDao.getQuantity() - quantity);

        // -- Update
        stockDao
                = this.mongoTemplate.findAndModify(new Query().addCriteria(Criteria.where("productName").is(productName)),
                new Update().set("quantity", stockDao.getQuantity()),
                FindAndModifyOptions.options().returnNew(true),
                StockDao.class);

        // -- Return
        return stockDao;

    }

    public StockDao getStock(String productName, int quantity){

        // -- Call
        return Optional.ofNullable(this.mongoTemplate.findOne(new Query().addCriteria(Criteria.where("productName").is(productName)), StockDao.class))
                .orElseThrow(() -> new RuntimeException("Stock not found"));

    }

}
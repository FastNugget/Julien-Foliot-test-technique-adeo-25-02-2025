package com.onetranslate.service.business.stock;

import com.onetranslate.service.business.stock.model.StockDao;
import com.onetranslate.service.business.stock.model.dto.StockDtoREQ;
import com.onetranslate.service.business.stock.model.dto.StockDtoRES;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
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
    private ModelMapper modelMapper;


    // -- LIFECYCLE ----------------------------------------------------------------------------------------------------

    @PostConstruct
    public void init() {

        // -- Set
        this.modelMapper = new ModelMapper();
        this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

    }

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

    public StockDao decreaseStock(String id, int quantity){

        // -- Get
        StockDao stockDao = Optional.ofNullable(this.mongoTemplate.findOne(new Query().addCriteria(Criteria.where("id").is(new ObjectId(id))), StockDao.class))
                .orElseThrow(() -> new RuntimeException("Stock not found"));

        // -- Decrease
        stockDao.setQuantity(stockDao.getQuantity() - quantity);

        // -- Update
        stockDao
                = this.mongoTemplate.findAndModify(new Query().addCriteria(Criteria.where("id").is(new ObjectId(id))),
                new Update().set("quantity", stockDao.getQuantity()),
                FindAndModifyOptions.options().returnNew(true),
                StockDao.class);

        // -- Return
        return stockDao;

    }

    public StockDao getStock(String id, int quantity){

        // -- Call
        return Optional.ofNullable(this.mongoTemplate.findOne(new Query().addCriteria(Criteria.where("id").is(new ObjectId(id))), StockDao.class))
                .orElseThrow(() -> new RuntimeException("Stock not found"));

    }

    public StockDtoRES updateDeliveryDelay(String id, StockDtoREQ stockDtoREQ){

        // -- Get
        StockDao stockDao = this.mongoTemplate.findAndModify(new Query().addCriteria(Criteria.where("id").is(new ObjectId(id))),
                new Update().set("quantity", stockDtoREQ.getDeliveryDelay()),
                FindAndModifyOptions.options().returnNew(true),
                StockDao.class);

        // -- Commit
        return this.modelMapper.map(stockDao, StockDtoRES.class);

    }

}
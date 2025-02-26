package com.onetranslate.service.stock;

import com.onetranslate.service.stock.model.StockDao;
import com.onetranslate.service.stock.model.dto.StockDtoREQ;
import com.onetranslate.service.stock.model.dto.StockDtoRES;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j(topic = "StockService")
@RequiredArgsConstructor
@Service()
public class StockService {

    // -- VARS
    private final StockRepository stockRepository;
    private ModelMapper modelMapper;


    // -- LIFECYCLE ----------------------------------------------------------------------------------------------------

    @PostConstruct
    public void init() {

        // -- Set
        this.modelMapper = new ModelMapper();
        this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

    }

    // -- CALLBACKS ----------------------------------------------------------------------------------------------------

    public StockDtoRES createStock(StockDtoREQ stockDtoREQ){

        // -- Check & Get
        Optional<StockDao> stockDaoOptional = this.stockRepository.findByProductName(stockDtoREQ.getProductName());

        // -- Get
        if(stockDaoOptional.isPresent()){

            // -- Error
            return this.modelMapper.map(stockDaoOptional.get(), StockDtoRES.class);

        }

        StockDao stockDao = new StockDao();
        stockDao.setProductName(stockDtoREQ.getProductName());
        stockDao.setQuantity(stockDtoREQ.getQuantity());
        stockDao.setQuantityMultiple(stockDtoREQ.getQuantityMultiple());
        stockDao.setDeliveryDelay(stockDtoREQ.getDeliveryDelay());

        // -- Update
        stockDao = this.stockRepository.save(stockDao);

        // -- Return
        return this.modelMapper.map(stockDao, StockDtoRES.class);

    }

    public StockDao decreaseStock(String id, int quantity){

        // -- Get
        StockDao stockDao = this.stockRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new RuntimeException("Stock not found"));

        // -- Decrease
        stockDao.setQuantity(stockDao.getQuantity() - quantity);

        // -- Update
        stockDao = this.stockRepository.save(stockDao);

        // -- Return
        return stockDao;

    }

    public StockDao getStock(String id){

        // -- Call
        return this.stockRepository.findById(Long.valueOf(id)).orElseThrow(() -> new RuntimeException("Stock not found"));

    }

    public StockDtoRES updateDeliveryDelay(String id, StockDtoREQ stockDtoREQ){

        // -- Get
        StockDao stockDao = this.modelMapper.map(stockDtoREQ, StockDao.class);

        // -- Save
        stockDao = this.stockRepository.save(stockDao);

        // -- Commit
        return this.modelMapper.map(stockDao, StockDtoRES.class);

    }

}
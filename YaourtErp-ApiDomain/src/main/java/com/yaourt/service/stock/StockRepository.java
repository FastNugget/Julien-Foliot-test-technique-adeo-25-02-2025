package com.yaourt.service.stock;

import com.yaourt.service.stock.model.StockDao;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StockRepository extends CrudRepository<StockDao, Long> {

    Optional<StockDao> findByProductName(String productName);

}

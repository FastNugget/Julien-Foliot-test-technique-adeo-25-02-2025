package com.onetranslate.service.yaourt.model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface YaourtRepository extends CrudRepository<YaourtDao, Long> {
}

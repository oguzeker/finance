package com.openpayd.finance.repository;

import com.openpayd.finance.entity.Exchange;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ExchangeRepository extends MongoRepository<Exchange, String> {

    Optional<Exchange> findById(String id);

    Page<Exchange> findAllByTransactionDateBetweenOrderByTransactionDate(LocalDateTime beginDate, LocalDateTime endDate,
                                                                         Pageable paging);

}

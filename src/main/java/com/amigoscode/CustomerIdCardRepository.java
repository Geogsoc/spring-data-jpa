package com.amigoscode;

import org.apache.commons.lang3.text.translate.NumericEntityUnescaper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CustomerIdCardRepository extends JpaRepository<CustomerIdCard, Long> {

    @Query("SELECT c FROM  CustomerIdCard c JOIN FETCH c.customer WHERE c.id = ?1")
   Optional<CustomerIdCard> findCustomerIdCardByIdWithCustomerIdCard(Long id);
}

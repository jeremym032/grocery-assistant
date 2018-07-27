package com.jeremycarvermartin.repository;

import com.jeremycarvermartin.domain.StoreGroceryItem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the StoreGroceryItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StoreGroceryItemRepository extends JpaRepository<StoreGroceryItem, Long> {

}

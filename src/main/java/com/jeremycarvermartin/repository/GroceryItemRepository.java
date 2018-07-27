package com.jeremycarvermartin.repository;

import com.jeremycarvermartin.domain.GroceryItem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the GroceryItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GroceryItemRepository extends JpaRepository<GroceryItem, Long> {

}

package com.jeremycarvermartin.repository;

import com.jeremycarvermartin.domain.Store;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Store entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

}

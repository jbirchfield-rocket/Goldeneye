package com.goldeneye.repo;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import com.goldeneye.model.Stone;


/**
 *
 * @author dshelby
 */
public interface StoneRepo extends ListCrudRepository<Stone, Integer> {
    @Query("SELECT STONEID, NAME, INVENTORY, PRICE FROM GLDEYE.TBSTONE")
    List<Stone> findAll();


    @Query("SELECT STONEID, NAME, INVENTORY, PRICE FROM GLDEYE.TBSTONE WHERE STONEID = :stoneId")
    Stone findByStoneId(@Param("stoneId") int stoneId);
}

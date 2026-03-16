package com.goldeneye.repo;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;

import com.goldeneye.model.Material;


/**
 *
 * @author dshelby
 */
public interface MaterialRepo extends ListCrudRepository<Material, Integer> {
    @Query("SELECT MATTID, NAME, INVENTORY, MULTIPLIER FROM GLDEYE.TBMATT")
    List<Material> findAll();

    // TODO: add query for finding material by id
}

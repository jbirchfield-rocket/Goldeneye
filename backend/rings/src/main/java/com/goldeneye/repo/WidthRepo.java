package com.goldeneye.repo;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import com.goldeneye.model.Width;


/**
 *
 * @author dshelby
 */
public interface WidthRepo extends ListCrudRepository<Width, Integer> {
    @Query("SELECT WID, WIDTH, MULTIPLIER, MATTUSE FROM GLDEYE.TBWIDTH")
    List<Width> findAll();


    @Query("SELECT WID, WIDTH, MULTIPLIER, MATTUSE FROM GLDEYE.TBWIDTH WHERE WID = :widthId")
    Width findByWidthId(@Param("widthId") int widthId);
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.goldeneye.repo;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import com.goldeneye.model.Location;

/**
 *
 * @author dshelby
 */
public interface LocationRepo extends ListCrudRepository<Location, Integer> {
    @Query("SELECT LOCID, CUSTID, STR, CITY, ST, ZIP FROM GLDEYE.TBLOC WHERE CUSTID = :custId")
    List<Location> findByCustId(@Param("custId") int custId);
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.goldeneye.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Modifying;
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

    @Query("SELECT LOCID, CUSTID, STR, CITY, ST, ZIP FROM GLDEYE.TBLOC WHERE LOCID = :locId")
    Optional<Location> findByLocId(@Param("locId") int locId);

    @Modifying
    @Query("DELETE FROM GLDEYE.TBLOC WHERE LOCID = :locId")
    void deleteByLocId(@Param("locId") int locId);
  
    @Query("INSERT INTO GLDEYE.TBLOC (CUSTID, STR, CITY, ST, ZIP) VALUES (:custId, :str, :city, :st, :zip) RETURNING LOCID")
    void insertLocation(@Param("custId") int custId, @Param("str") String str, @Param("city") String city, @Param("st") String st, @Param("zip") String zip);
}

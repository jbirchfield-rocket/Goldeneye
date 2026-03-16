package com.goldeneye.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.goldeneye.dto.StoneDTO;
import com.goldeneye.repo.StoneRepo;

/**
 *
 * @author dshelby
 */
@Service
public class StoneService {

    private final StoneRepo stoneRepo;

    public StoneService(StoneRepo stoneRepo) {
        this.stoneRepo = stoneRepo;
    }

    public List<StoneDTO> getAllStones() {
        return stoneRepo.findAll()
            .stream()
            .map(c -> new StoneDTO(c.getStoneId(), c.getStoneName(), c.getInventory(), c.getPrice()))
            .toList();
    }

    // TODO: add method for getting stone by id
}
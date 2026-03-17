package com.goldeneye.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.goldeneye.dto.StoneDTO;
import com.goldeneye.repo.StoneRepo;
import com.goldeneye.model.Stone;

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


    public StoneDTO getStoneById(int stoneId) {
        Stone curr_stone = stoneRepo.findByStoneId(stoneId);
        return new StoneDTO(curr_stone.getStoneId(), curr_stone.getStoneName(), curr_stone.getInventory(), curr_stone.getPrice());

    }
}
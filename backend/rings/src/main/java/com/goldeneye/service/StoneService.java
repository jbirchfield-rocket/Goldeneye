package com.goldeneye.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.goldeneye.dto.StoneDTO;
import com.goldeneye.repo.StoneRepo;
import com.goldeneye.model.Stone;
import com.goldeneye.exception.ResourceNotFoundException;

/**
 *
 * @author dshelby
 */
@Service
public class StoneService {

    private static final Logger logger = LoggerFactory.getLogger(StoneService.class);

    private final StoneRepo stoneRepo;

    public StoneService(StoneRepo stoneRepo) {
        this.stoneRepo = stoneRepo;
    }

    public List<StoneDTO> getAllStones() {
        logger.info("Fetching all stones");
        List<StoneDTO> stones = stoneRepo.findAll()
            .stream()
            .map(c -> new StoneDTO(c.getStoneId(), c.getStoneName(), c.getInventory(), c.getPrice()))
            .toList();
        if (stones.isEmpty()) {
            logger.warn("No stones found");
            throw new ResourceNotFoundException("No stones found.");
        }
        logger.debug("Retrieved {} stones", stones.size());
        return stones;
    }

    public StoneDTO getStoneById(int stoneId) {
        logger.info("Fetching stone with ID: {}", stoneId);
        Stone curr_stone = stoneRepo.findByStoneId(stoneId);
        if (curr_stone == null) {
            logger.warn("Stone not found with ID: {}", stoneId);
            throw new ResourceNotFoundException("Stone not found with id: " + stoneId);
        }
        logger.debug("Found stone: {}", curr_stone.getStoneName());
        return new StoneDTO(curr_stone.getStoneId(), curr_stone.getStoneName(), curr_stone.getInventory(), curr_stone.getPrice());
    }
}
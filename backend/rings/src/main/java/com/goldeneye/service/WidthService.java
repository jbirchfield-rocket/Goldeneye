package com.goldeneye.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.goldeneye.dto.WidthDTO;
import com.goldeneye.repo.WidthRepo;

/**
 *
 * @author dshelby
 */
@Service
public class WidthService {

    private final WidthRepo widthRepo;

    public WidthService(WidthRepo widthRepo) {
        this.widthRepo = widthRepo;
    }

    public List<WidthDTO> getAllWidths() {
        return widthRepo.findAll()
            .stream()
            .map(c -> new WidthDTO(c.getWidthId(), c.getWidthName(), c.getMultiplier(), c.getMaterialUse()))
            .toList();
    }
}
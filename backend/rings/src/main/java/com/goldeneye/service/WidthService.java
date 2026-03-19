package com.goldeneye.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.goldeneye.dto.WidthDTO;
import com.goldeneye.repo.WidthRepo;
import com.goldeneye.model.Width;
import com.goldeneye.exception.ResourceNotFoundException;

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
            .map(c -> new WidthDTO(c.getWidthId(), c.getWidth(), c.getMultiplier(), c.getMaterialUse()))
            .toList();
    }


    public WidthDTO getWidthById(int widthId) {
        Width curr_width = widthRepo.findByWidthId(widthId);
        if (curr_width == null) {
            throw new ResourceNotFoundException("Width not found with id: " + widthId);
        }
        return new WidthDTO(curr_width.getWidthId(), curr_width.getWidth(), curr_width.getMultiplier(), curr_width.getMaterialUse());

    }
}
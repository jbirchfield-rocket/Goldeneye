package com.goldeneye.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static final Logger logger = LoggerFactory.getLogger(WidthService.class);

    private final WidthRepo widthRepo;

    public WidthService(WidthRepo widthRepo) {
        this.widthRepo = widthRepo;
    }

    public List<WidthDTO> getAllWidths() {
        logger.info("Fetching all widths");
        List<WidthDTO> widths = widthRepo.findAll()
            .stream()
            .map(c -> new WidthDTO(c.getWidthId(), c.getWidth(), c.getMultiplier(), c.getMaterialUse()))
            .toList();
        if (widths.isEmpty()) {
            logger.warn("No widths found");
            throw new ResourceNotFoundException("No widths found.");
        }
        logger.debug("Retrieved {} widths", widths.size());
        return widths;
    }

    public WidthDTO getWidthById(int widthId) {
        logger.info("Fetching width with ID: {}", widthId);
        Width curr_width = widthRepo.findByWidthId(widthId);
        if (curr_width == null) {
            logger.warn("Width not found with ID: {}", widthId);
            throw new ResourceNotFoundException("Width not found with id: " + widthId);
        }
        logger.debug("Found width: {}", curr_width.getWidth());
        return new WidthDTO(curr_width.getWidthId(), curr_width.getWidth(), curr_width.getMultiplier(), curr_width.getMaterialUse());
    }
}
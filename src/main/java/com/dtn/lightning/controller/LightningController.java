package com.dtn.lightning.controller;

import com.dtn.lightning.model.Lightning;
import com.dtn.lightning.service.LightningService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/lightning")
public class LightningController {

    private final LightningService lightningService;

    public LightningController(
        final LightningService lightningService
    ) {
        this.lightningService = lightningService;
    }

    @GetMapping
    public ResponseEntity<List<Lightning>> getLightningList() {
        ResponseEntity<List<Lightning>> response = ResponseEntity.notFound().build();
        List<Lightning> lightnings = lightningService.getLightnings();
        if(!lightnings.isEmpty()) {
            response = ResponseEntity.ok(lightnings);
        }
        return response;
    }

}

package com.dtn.lightning.controller;

import com.dtn.lightning.config.ResponseConfig;
import com.dtn.lightning.model.Lightning;
import com.dtn.lightning.model.dto.ResponseMessageDto;
import com.dtn.lightning.service.AlertService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/alert")
public class AlertController {

    private final ResponseConfig responseConfig;

    private final AlertService alertService;

    public AlertController(
            final ResponseConfig responseConfig,
            final AlertService alertService
    ) {
        this.responseConfig = responseConfig;
        this.alertService = alertService;
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseMessageDto> alertAll() {
        log.info("Started Alert All request");
        ResponseEntity<ResponseMessageDto> response = ResponseEntity.badRequest().body(new ResponseMessageDto(this.responseConfig, false));
        if(this.alertService.alertAll().booleanValue()) {
            response = ResponseEntity.ok().body(new ResponseMessageDto(this.responseConfig, true));
        }
        log.info("Ended Alert All request");
        return response;
    }

    @PostMapping()
    public ResponseEntity<ResponseMessageDto> alertNew(
            @RequestBody List<Lightning> lightnings
    ) {
        log.info("Started Alert New request");
        ResponseEntity<ResponseMessageDto> response = ResponseEntity.badRequest().body(new ResponseMessageDto(this.responseConfig, false));
        if(this.alertService.alertNew(lightnings).booleanValue()) {
            response = ResponseEntity.ok().body(new ResponseMessageDto(this.responseConfig, true));
        }
        log.info("Ended Alert New request");
        return response;
    }

}

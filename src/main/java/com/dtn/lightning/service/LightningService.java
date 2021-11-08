package com.dtn.lightning.service;

import com.dtn.lightning.model.Lightning;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class LightningService {

    private final FileService<Lightning> lightningFileService;

    private List<Lightning> lightningList;

    public LightningService(
            final FileService<Lightning> lightningFileService
    ) {
        this.lightningFileService = lightningFileService;
        this.lightningList = this.getLightnings();
    }

    public List<Lightning> getLightnings() {
        List<Lightning> result = new ArrayList<>();
        if(!Optional.ofNullable(this.lightningList).isPresent()) {
            try {
                result = this.lightningFileService.readFile();
            } catch (FileNotFoundException e) {
                log.error("Lightning file not found");
            } catch (IOException e) {
                log.error("Failed to parse Lightning file");
            }
        } else {
            result = this.lightningList;
        }
        return result;
    }

    public Boolean appendLightning(Lightning lightning) {
        Boolean result = Boolean.FALSE;
        try {
            this.lightningFileService.appendFile(lightning);
            this.lightningList.add(lightning);
            result = Boolean.TRUE;
        } catch (FileNotFoundException e) {
            log.error("Lightning file not found");
        } catch (IOException e) {
            log.error("Failed to parse Lightning file");
        }
        return result;
    }

}

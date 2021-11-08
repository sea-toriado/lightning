package com.dtn.lightning.service;

import com.dtn.lightning.model.AlertedAsset;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class AlertedAssetService {

    private final FileService<AlertedAsset> alertedAssetFileService;

    private List<AlertedAsset> alertedAssetList;

    public AlertedAssetService(
            final FileService<AlertedAsset> alertedAssetFileService
    ) {
        this.alertedAssetFileService = alertedAssetFileService;
        this.alertedAssetList = new ArrayList<>();
    }

    public List<AlertedAsset> getAlertedAssets() {
        List<AlertedAsset> result = new ArrayList<>();
        if(!Optional.ofNullable(this.alertedAssetList).isPresent()
        && !this.alertedAssetList.isEmpty()) {
            try {
                result = this.alertedAssetFileService.readFile();
            } catch (FileNotFoundException e) {
                log.error("Alerted Asset file not found");
            } catch (IOException e) {
                log.error("Failed to parse Alerted Asset file");
            }
        } else {
            result = this.alertedAssetList;
        }
        return result;
    }

    public Boolean appendAlertedAsset(AlertedAsset alertedAsset) {
        Boolean result = Boolean.FALSE;
        try {
            this.alertedAssetFileService.appendFile(alertedAsset);
            result = Boolean.TRUE;
        } catch (FileNotFoundException e) {
            log.error("Alerted Asset file not found");
        } catch (IOException e) {
            log.error("Failed to parse Alerted Asset file");
        }
        return result;
    }

}

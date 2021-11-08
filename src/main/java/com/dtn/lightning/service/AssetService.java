package com.dtn.lightning.service;

import com.dtn.lightning.model.Asset;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class AssetService {

    private final FileService<Asset> assetFileService;

    private List<Asset> assetList;

    public AssetService(
            final FileService<Asset> assetFileService
    ) {
        this.assetFileService = assetFileService;
        this.assetList = new ArrayList<>();
    }

    public List<Asset> getAssets() {
        List<Asset> result = new ArrayList<>();
        if(!Optional.ofNullable(this.assetList).isPresent()
        && !this.assetList.isEmpty()) {
            try {
                result = this.assetFileService.readFile();
            } catch (FileNotFoundException e) {
                log.error("Asset file not found");
            } catch (IOException e) {
                log.error("Failed to parse Asset file");
            }
        } else {
            result = this.assetList;
        }
        return result;
    }

    public Boolean appendAsset(Asset asset) {
        Boolean result = Boolean.FALSE;
        try {
            this.assetFileService.appendFile(asset);
            result = Boolean.TRUE;
        } catch (FileNotFoundException e) {
            log.error("Asset file not found");
        } catch (IOException e) {
            log.error("Failed to parse Asset file");
        }
        return result;
    }
}

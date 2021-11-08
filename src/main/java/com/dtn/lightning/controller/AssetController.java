package com.dtn.lightning.controller;

import com.dtn.lightning.config.ResponseConfig;
import com.dtn.lightning.model.Asset;
import com.dtn.lightning.model.dto.ResponseMessageDto;
import com.dtn.lightning.service.AssetService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/asset")
public class AssetController {

    private final ResponseConfig responseConfig;

    private final AssetService assetService;

    public AssetController(
            final ResponseConfig responseConfig,
            final AssetService assetService
    ) {
        this.responseConfig = responseConfig;
        this.assetService = assetService;
    }

    @GetMapping
    public ResponseEntity<List<Asset>> getAssetList() {
        ResponseEntity<List<Asset>> response = ResponseEntity.badRequest().build();
        List<Asset> assets = assetService.getAssets();
        if(!assets.isEmpty()) {
            response = ResponseEntity.ok(assets);
        }
        return response;
    }

    @PostMapping
    public ResponseEntity<ResponseMessageDto> appendAsset(@RequestBody Asset asset) {
        ResponseEntity<ResponseMessageDto> response = ResponseEntity.badRequest().body(new ResponseMessageDto(this.responseConfig, false));
        if(this.assetService.appendAsset(asset).booleanValue()) {
            response = ResponseEntity.ok().body(new ResponseMessageDto(this.responseConfig, true));
        }
        return response;
    }
}

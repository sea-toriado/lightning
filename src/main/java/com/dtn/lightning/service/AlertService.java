package com.dtn.lightning.service;

import com.dtn.lightning.config.WeatherConfig;
import com.dtn.lightning.model.AlertedAsset;
import com.dtn.lightning.model.Asset;
import com.dtn.lightning.model.Lightning;
import lombok.extern.slf4j.Slf4j;
import org.geotools.tile.impl.bing.BingTileUtil;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AlertService {

    private final LightningService lightningService;

    private final AssetService assetService;

    private final AlertedAssetService alertedAssetService;

    private final WeatherConfig weatherConfig;

    public AlertService(
            final LightningService lightningService,
            final AssetService assetService,
            final AlertedAssetService alertedAssetService,
            final WeatherConfig weatherConfig
    ) {
        this.lightningService = lightningService;
        this.assetService = assetService;
        this.alertedAssetService = alertedAssetService;
        this.weatherConfig = weatherConfig;
    }

    public Boolean alertAll() {
        Boolean result = Boolean.FALSE;
        List<Lightning> lightnings = this.lightningService.getLightnings();
        List<Asset> assets = this.assetService.getAssets();
        List<String> alertedAssets =  this.alertedAssetService
                .getAlertedAssets()
                .stream()
                .map(AlertedAsset::getQuadKey)
                .collect(Collectors.toList());
        if(!lightnings.isEmpty() && !assets.isEmpty()) {
            lightnings
                .stream()
                .forEach(lightning ->
                    this.process(
                        lightning,
                        assets,
                        alertedAssets
                    )
                );
            result = Boolean.TRUE;
        }
        return result;
    }

    public Boolean alertNew(List<Lightning> lightnings) {
        Boolean result;
        List<Asset> assets = assetService.getAssets();
        List<String> alertedAssets =  this.alertedAssetService
                .getAlertedAssets()
                .stream()
                .map(AlertedAsset::getQuadKey)
                .collect(Collectors.toList());
        lightnings.forEach(lightning ->
            this.process(
                lightning,
                assets,
                alertedAssets
            )
        );
        result = Boolean.TRUE;
        return result;
    }

    private void process(
            Lightning lightning,
            List<Asset> assets,
            List<String> alertedAssets) {
        if (!this.weatherConfig.getExceptionFlashType().contains(lightning.getFlashType())) {
            String quadKey = BingTileUtil.lonLatToQuadKey(
                    lightning.getLongitude(),
                    lightning.getLatitude(),
                    this.weatherConfig.getZoomLevel()
            );
            if(alertedAssets
                    .stream()
                    .noneMatch(quadKey::contains)) {
                Optional<Asset> filteredAsset = assets
                        .stream()
                        .filter(asset -> asset.getQuadKey().equals(quadKey))
                        .collect(Collectors.toList())
                        .stream()
                        .findFirst();
                alertedAssets.add(quadKey);
                if(filteredAsset.isPresent()) {
                    this.alertedAssetService.appendAlertedAsset(
                            new AlertedAsset(
                                    quadKey,
                                    filteredAsset.get().getAssetOwner(),
                                    (new Date()).getTime(),
                                    lightning.getStrikeTime())
                    );
                    log.info(
                            this.weatherConfig.getAlertMessage(),
                            filteredAsset.get().getAssetOwner(),
                            filteredAsset.get().getAssetName());
                } else {
                    log.error("Unable to find Quadkey:{} in Assets", quadKey);
                }
            }
        }
    }

}

package com.dtn.lightning.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class WeatherConfig {

    @Getter
    private final String assetsPath;
    @Getter
    private final String lightningPath;
    @Getter
    private final String alertedAssetPath;
    @Getter
    private final Integer zoomLevel;
    @Getter
    private final String alertMessage;
    @Getter
    private final List<Integer> exceptionFlashType;

    public WeatherConfig(
        @Value("${weather.assets.path}") final String assetsPath,
        @Value("${weather.lightning.path}") final String lightningPath,
        @Value("${weather.alerted-assets.path}") final String alertedAssetPath,
        @Value("${weather.map.zoom-level}") final Integer zoomLevel,
        @Value("${weather.alert.message}") final String alertMessage,
        @Value("${weather.alert.exception.flash-type}") final List<Integer> exceptionFlashType
    ) {
        this.assetsPath = assetsPath;
        this.lightningPath = lightningPath;
        this.alertedAssetPath = alertedAssetPath;
        this.zoomLevel = zoomLevel;
        this.alertMessage = alertMessage;
        this.exceptionFlashType = exceptionFlashType;
    }

}

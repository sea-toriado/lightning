package com.dtn.lightning.service.impl;

import com.dtn.lightning.config.WeatherConfig;
import com.dtn.lightning.model.AlertedAsset;
import com.dtn.lightning.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class AlertedAssetFileServiceImpl implements FileService<AlertedAsset> {

    private final WeatherConfig weatherConfig;

    private BufferedReader fileReader;

    private FileWriter fileWriter;

    public AlertedAssetFileServiceImpl(
            final WeatherConfig weatherConfig
    ) throws IOException {
        this.weatherConfig = weatherConfig;
        this.fileReader = new BufferedReader(new FileReader(weatherConfig.getAlertedAssetPath()));
        this.fileWriter = new FileWriter(weatherConfig.getAlertedAssetPath(), true);
    }


    @Override
    public List<AlertedAsset> readFile() throws IOException {
        List<AlertedAsset> result = new ArrayList<>();
        StringBuilder fileString = new StringBuilder();
        String line;
        this.fileReader = new BufferedReader(new FileReader(weatherConfig.getAlertedAssetPath()));
        while((line = this.fileReader.readLine()) != null) {
            fileString.append(line);
        }
        this.fileReader.close();
        JSONArray fileJson = new JSONArray(fileString.toString());
        fileJson.forEach(jsonObj ->
            result.add(new AlertedAsset((JSONObject) jsonObj))
        );
        return result;
    }

    @Override
    public void appendFile(AlertedAsset alertedAsset) throws IOException {
        List<AlertedAsset> alertedAssetList = this.readFile();
        alertedAssetList.add(alertedAsset);
        this.fileWriter = new FileWriter(weatherConfig.getAlertedAssetPath(), false);
        this.fileWriter.write((new JSONArray(alertedAssetList)).toString());
        this.fileWriter.close();
    }

}

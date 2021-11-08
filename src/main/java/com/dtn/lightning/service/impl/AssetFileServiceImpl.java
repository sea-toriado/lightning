package com.dtn.lightning.service.impl;

import com.dtn.lightning.config.WeatherConfig;
import com.dtn.lightning.model.Asset;
import com.dtn.lightning.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class AssetFileServiceImpl implements FileService<Asset> {

    private final WeatherConfig weatherConfig;

    private BufferedReader fileReader;

    private FileWriter fileWriter;

    public AssetFileServiceImpl(
        final WeatherConfig weatherConfig
    ) throws IOException {
        this.weatherConfig = weatherConfig;
        this.fileReader = new BufferedReader(new FileReader(weatherConfig.getAssetsPath()));
        this.fileWriter = new FileWriter(weatherConfig.getAssetsPath(), true);
    }

    @Override
    public List<Asset> readFile() throws IOException {
        List<Asset> result = new ArrayList<>();
        StringBuilder fileString = new StringBuilder();
        String line;
        this.fileReader = new BufferedReader(new FileReader(weatherConfig.getAssetsPath()));
        while((line = this.fileReader.readLine()) != null) {
            fileString.append(line);
        }
        this.fileReader.close();
        JSONArray fileJson = new JSONArray(fileString.toString());
        fileJson.forEach(jsonObj ->
            result.add(new Asset((JSONObject) jsonObj))
        );
        return result;
    }

    @Override
    public void appendFile(Asset asset) throws IOException {
        List<Asset> assetList = this.readFile();
        assetList.add(asset);
        this.fileWriter = new FileWriter(this.weatherConfig.getAssetsPath(), false);
        this.fileWriter.write(asset.toString());
        this.fileWriter.close();
    }

}

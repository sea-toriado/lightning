package com.dtn.lightning.service.impl;

import com.dtn.lightning.config.WeatherConfig;
import com.dtn.lightning.model.Lightning;
import com.dtn.lightning.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class LightningFileServiceImpl implements FileService<Lightning> {

    private final WeatherConfig weatherConfig;

    private BufferedReader fileReader;

    private FileWriter fileWriter;

    public LightningFileServiceImpl(
        final WeatherConfig weatherConfig
    ) throws IOException {
        this.weatherConfig = weatherConfig;
        this.fileReader = new BufferedReader(new FileReader(weatherConfig.getLightningPath()));
        this.fileWriter = new FileWriter(weatherConfig.getLightningPath(), true);
    }

    @Override
    public List<Lightning> readFile() throws IOException {
        String line;
        List<Lightning> result = new ArrayList<>();

        this.fileReader = new BufferedReader(new FileReader(weatherConfig.getLightningPath()));
        while((line = this.fileReader.readLine()) != null) {
            result.add(new Lightning(new JSONObject(line)));
        }
        this.fileReader.close();
        return result;
    }

    @Override
    public void appendFile(Lightning lightning) throws IOException {
        this.fileWriter = new FileWriter(weatherConfig.getLightningPath(), true);
        this.fileWriter.write(lightning.toJson().toString() + "\n");
        this.fileWriter.close();
    }

}

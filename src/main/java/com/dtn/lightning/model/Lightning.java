package com.dtn.lightning.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

@Data
@NoArgsConstructor
public class Lightning {

    private Integer flashType;

    private Long strikeTime;

    private Double latitude;

    private Double longitude;

    private Integer peakAmps;

    private String reserved;

    private Integer icHeight;

    private Long receivedTime;

    private Integer numberOfSensors;

    private Integer multiplicity;

    public Lightning(JSONObject jsonObject) {
        this.flashType = jsonObject.optInt("flashType");
        this.strikeTime = jsonObject.optLong("strikeTime");
        this.latitude = jsonObject.optDouble("latitude");
        this.longitude = jsonObject.optDouble("longitude");
        this.peakAmps = jsonObject.optInt("peakAmps");
        this.reserved = jsonObject.optString("reserved");
        this.icHeight = jsonObject.optInt("icHeight");
        this.receivedTime = jsonObject.optLong("receivedTime");
        this.numberOfSensors = jsonObject.optInt("numberOfSensors");
        this.multiplicity = jsonObject.optInt("multiplicity");
    }

    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("flashType", this.flashType);
        jsonObject.put("strikeTime", this.strikeTime);
        jsonObject.put("latitude", this.latitude);
        jsonObject.put("longitude", this.longitude);
        jsonObject.put("peakAmps", this.peakAmps);
        jsonObject.put("reserved", this.reserved);
        jsonObject.put("icHeight", this.icHeight);
        jsonObject.put("receivedTime", this.receivedTime);
        jsonObject.put("numberOfSensors", this.numberOfSensors);
        jsonObject.put("multiplicity", this.multiplicity);
        return  jsonObject;
    }

}

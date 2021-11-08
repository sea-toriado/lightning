package com.dtn.lightning.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.json.JSONObject;

@Data
@AllArgsConstructor
public class AlertedAsset {

    private String quadKey;
    private String assetOwner;
    private Long alertTime;
    private Long strikeTime;

    public AlertedAsset(JSONObject jsonObject) {
        this.quadKey = jsonObject.optString("quadKey");
        this.assetOwner = jsonObject.optString("assetOwner");
        this.alertTime = jsonObject.optLong("alertTime");
        this.strikeTime = jsonObject.optLong("strikeTime");
    }

}

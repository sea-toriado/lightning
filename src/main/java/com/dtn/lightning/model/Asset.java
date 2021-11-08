package com.dtn.lightning.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

@Data
@NoArgsConstructor
public class Asset {

    private String assetName;

    private String quadKey;

    private String assetOwner;

    public Asset(JSONObject jsonObject) {
        assetName = jsonObject.optString("assetName");
        quadKey = jsonObject.optString("quadKey");
        assetOwner = jsonObject.optString("assetOwner");
    }

    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("assetName", this.assetName);
        jsonObject.put("quadKey", this.quadKey);
        jsonObject.put("assetOwner", this.assetOwner);
        return jsonObject;
    }

    public String toString() {
        return toJson().toString();
    }

}

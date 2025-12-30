package com.mudrex.sdk;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mudrex.sdk.exceptions.MudrexException;
import com.mudrex.sdk.models.Enums;
import com.mudrex.sdk.models.OtherModels;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * Leverage API endpoints
 */
public class LeverageAPI {
    private final MudrexClient client;
    private final Gson gson;

    LeverageAPI(MudrexClient client) {
        this.client = client;
        this.gson = client.getGson();
    }

    /**
     * Get current leverage for an asset
     */
    public OtherModels.Leverage get(String assetId) throws MudrexException {
        String path = "/futures/" + assetId + "/leverage";
        byte[] resp = client.get(path);
        Type type = new TypeToken<OtherModels.APIResponse<OtherModels.Leverage>>(){}.getType();
        OtherModels.APIResponse<OtherModels.Leverage> apiResp = gson.fromJson(
                new String(resp, StandardCharsets.UTF_8), type);
        return apiResp.data;
    }

    /**
     * Set leverage and margin type for an asset
     */
    public OtherModels.Leverage set(String assetId, String leverage, Enums.MarginType marginType) throws MudrexException {
        String path = "/futures/" + assetId + "/leverage";
        Map<String, String> body = new HashMap<>();
        body.put("leverage", leverage);
        body.put("margin_type", marginType.toString());
        
        byte[] resp = client.patch(path, gson.toJson(body));
        Type type = new TypeToken<OtherModels.APIResponse<OtherModels.Leverage>>(){}.getType();
        OtherModels.APIResponse<OtherModels.Leverage> apiResp = gson.fromJson(
                new String(resp, StandardCharsets.UTF_8), type);
        return apiResp.data;
    }
}

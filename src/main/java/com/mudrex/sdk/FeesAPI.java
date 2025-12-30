package com.mudrex.sdk;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mudrex.sdk.exceptions.MudrexException;
import com.mudrex.sdk.models.OtherModels;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Fees API endpoints
 */
public class FeesAPI {
    private final MudrexClient client;
    private final Gson gson;

    FeesAPI(MudrexClient client) {
        this.client = client;
        this.gson = client.getGson();
    }

    /**
     * Get fee history with pagination
     */
    public List<OtherModels.FeeRecord> getHistory(int page, int perPage) throws MudrexException {
        StringBuilder path = new StringBuilder("/fees");
        
        boolean first = true;
        if (page > 0) {
            path.append("?page=").append(page);
            first = false;
        }
        if (perPage > 0) {
            path.append(first ? "?" : "&").append("per_page=").append(perPage);
        }
        
        byte[] resp = client.get(path.toString());
        Type type = new TypeToken<OtherModels.APIResponse<List<OtherModels.FeeRecord>>>(){}.getType();
        OtherModels.APIResponse<List<OtherModels.FeeRecord>> apiResp = gson.fromJson(
                new String(resp, StandardCharsets.UTF_8), type);
        return apiResp.data;
    }
}

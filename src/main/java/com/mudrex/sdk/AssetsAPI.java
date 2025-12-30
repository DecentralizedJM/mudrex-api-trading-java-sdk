package com.mudrex.sdk;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mudrex.sdk.exceptions.MudrexException;
import com.mudrex.sdk.models.AssetModels;
import com.mudrex.sdk.models.OtherModels;

import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Assets API endpoints
 */
public class AssetsAPI {
    private final MudrexClient client;
    private final Gson gson;

    AssetsAPI(MudrexClient client) {
        this.client = client;
        this.gson = client.getGson();
    }

    /**
     * List all tradable assets with pagination
     */
    public List<AssetModels.Asset> listAll(int page, int perPage, String sortBy, String sortOrder) throws MudrexException {
        StringBuilder path = new StringBuilder("/assets");
        
        boolean first = true;
        if (page > 0) {
            path.append("?page=").append(page);
            first = false;
        }
        if (perPage > 0) {
            path.append(first ? "?" : "&").append("per_page=").append(perPage);
            first = false;
        }
        if (sortBy != null && !sortBy.isEmpty()) {
            path.append(first ? "?" : "&").append("sort_by=").append(URLEncoder.encode(sortBy, StandardCharsets.UTF_8));
            first = false;
        }
        if (sortOrder != null && !sortOrder.isEmpty()) {
            path.append(first ? "?" : "&").append("sort_order=").append(URLEncoder.encode(sortOrder, StandardCharsets.UTF_8));
        }
        
        byte[] resp = client.get(path.toString());
        Type type = new TypeToken<OtherModels.APIResponse<AssetModels.AssetListResponse>>(){}.getType();
        OtherModels.APIResponse<AssetModels.AssetListResponse> apiResp = gson.fromJson(
                new String(resp, StandardCharsets.UTF_8), type);
        return apiResp.data.assets;
    }

    /**
     * Get a specific asset by ID
     */
    public AssetModels.Asset getAsset(String assetId) throws MudrexException {
        String path = "/assets/" + assetId;
        byte[] resp = client.get(path);
        Type type = new TypeToken<OtherModels.APIResponse<AssetModels.Asset>>(){}.getType();
        OtherModels.APIResponse<AssetModels.Asset> apiResp = gson.fromJson(
                new String(resp, StandardCharsets.UTF_8), type);
        return apiResp.data;
    }
}

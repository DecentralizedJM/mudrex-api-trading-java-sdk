package com.mudrex.sdk;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mudrex.sdk.exceptions.MudrexException;
import com.mudrex.sdk.models.OtherModels;
import com.mudrex.sdk.models.PositionModels;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Positions API endpoints
 */
public class PositionsAPI {
    private final MudrexClient client;
    private final Gson gson;

    PositionsAPI(MudrexClient client) {
        this.client = client;
        this.gson = client.getGson();
    }

    /**
     * List all open positions
     */
    public List<PositionModels.Position> listOpen() throws MudrexException {
        byte[] resp = client.get("/positions");
        Type type = new TypeToken<OtherModels.APIResponse<List<PositionModels.Position>>>(){}.getType();
        OtherModels.APIResponse<List<PositionModels.Position>> apiResp = gson.fromJson(
                new String(resp, StandardCharsets.UTF_8), type);
        return apiResp.data;
    }

    /**
     * Get a specific position
     */
    public PositionModels.Position get(String positionId) throws MudrexException {
        String path = "/positions/" + positionId;
        byte[] resp = client.get(path);
        Type type = new TypeToken<OtherModels.APIResponse<PositionModels.Position>>(){}.getType();
        OtherModels.APIResponse<PositionModels.Position> apiResp = gson.fromJson(
                new String(resp, StandardCharsets.UTF_8), type);
        return apiResp.data;
    }

    /**
     * Close a position completely
     */
    public boolean close(String positionId) throws MudrexException {
        String path = "/positions/" + positionId + "/close";
        client.post(path, null);
        return true;
    }

    /**
     * Close a position partially
     */
    public boolean closePartial(String positionId, String quantity) throws MudrexException {
        String path = "/positions/" + positionId + "/close";
        Map<String, String> body = new HashMap<>();
        body.put("quantity", quantity);
        client.post(path, gson.toJson(body));
        return true;
    }

    /**
     * Reverse a position (LONG to SHORT or vice versa)
     */
    public boolean reverse(String positionId) throws MudrexException {
        String path = "/positions/" + positionId + "/reverse";
        client.post(path, null);
        return true;
    }

    /**
     * Set a risk order (stop loss or take profit)
     */
    public PositionModels.RiskOrder setRiskOrder(String positionId, String triggerType, String triggerPrice) throws MudrexException {
        String path = "/positions/" + positionId + "/risk-order";
        Map<String, String> body = new HashMap<>();
        body.put("trigger_type", triggerType);
        body.put("trigger_price", triggerPrice);
        
        byte[] resp = client.post(path, gson.toJson(body));
        Type type = new TypeToken<OtherModels.APIResponse<PositionModels.RiskOrder>>(){}.getType();
        OtherModels.APIResponse<PositionModels.RiskOrder> apiResp = gson.fromJson(
                new String(resp, StandardCharsets.UTF_8), type);
        return apiResp.data;
    }

    /**
     * Set a stop loss
     */
    public PositionModels.RiskOrder setStopLoss(String positionId, String triggerPrice) throws MudrexException {
        return setRiskOrder(positionId, "STOP_LOSS", triggerPrice);
    }

    /**
     * Set a take profit
     */
    public PositionModels.RiskOrder setTakeProfit(String positionId, String triggerPrice) throws MudrexException {
        return setRiskOrder(positionId, "TAKE_PROFIT", triggerPrice);
    }

    /**
     * Edit an existing risk order
     */
    public PositionModels.RiskOrder editRiskOrder(String positionId, String riskOrderId, String triggerPrice) throws MudrexException {
        String path = "/positions/" + positionId + "/risk-order/" + riskOrderId;
        Map<String, String> body = new HashMap<>();
        body.put("trigger_price", triggerPrice);
        
        byte[] resp = client.patch(path, gson.toJson(body));
        Type type = new TypeToken<OtherModels.APIResponse<PositionModels.RiskOrder>>(){}.getType();
        OtherModels.APIResponse<PositionModels.RiskOrder> apiResp = gson.fromJson(
                new String(resp, StandardCharsets.UTF_8), type);
        return apiResp.data;
    }

    /**
     * Get position history with pagination
     */
    public List<PositionModels.Position> getHistory(int page, int perPage) throws MudrexException {
        StringBuilder path = new StringBuilder("/positions/history");
        
        boolean first = true;
        if (page > 0) {
            path.append("?page=").append(page);
            first = false;
        }
        if (perPage > 0) {
            path.append(first ? "?" : "&").append("per_page=").append(perPage);
        }
        
        byte[] resp = client.get(path.toString());
        Type type = new TypeToken<OtherModels.APIResponse<List<PositionModels.Position>>>(){}.getType();
        OtherModels.APIResponse<List<PositionModels.Position>> apiResp = gson.fromJson(
                new String(resp, StandardCharsets.UTF_8), type);
        return apiResp.data;
    }
}

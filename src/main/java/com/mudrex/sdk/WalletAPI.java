package com.mudrex.sdk;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mudrex.sdk.exceptions.MudrexException;
import com.mudrex.sdk.models.Enums;
import com.mudrex.sdk.models.OtherModels;
import com.mudrex.sdk.models.WalletModels;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * Wallet API endpoints
 */
public class WalletAPI {
    private final MudrexClient client;
    private final Gson gson;

    WalletAPI(MudrexClient client) {
        this.client = client;
        this.gson = client.getGson();
    }

    /**
     * Get spot wallet balance
     */
    public WalletModels.WalletBalance getSpotBalance() throws MudrexException {
        byte[] resp = client.get("/wallet/funds");
        Type type = new TypeToken<OtherModels.APIResponse<WalletModels.WalletBalance>>(){}.getType();
        OtherModels.APIResponse<WalletModels.WalletBalance> apiResp = gson.fromJson(
                new String(resp, StandardCharsets.UTF_8), type);
        return apiResp.data;
    }

    /**
     * Get futures wallet balance
     */
    public WalletModels.FuturesBalance getFuturesBalance() throws MudrexException {
        byte[] resp = client.get("/wallet/balance");
        Type type = new TypeToken<OtherModels.APIResponse<WalletModels.FuturesBalance>>(){}.getType();
        OtherModels.APIResponse<WalletModels.FuturesBalance> apiResp = gson.fromJson(
                new String(resp, StandardCharsets.UTF_8), type);
        return apiResp.data;
    }

    /**
     * Transfer funds between wallets
     */
    public WalletModels.TransferResult transfer(Enums.WalletType fromWallet, Enums.WalletType toWallet, String amount) throws MudrexException {
        Map<String, String> body = new HashMap<>();
        body.put("from_wallet_type", fromWallet.toString());
        body.put("to_wallet_type", toWallet.toString());
        body.put("amount", amount);
        
        byte[] resp = client.post("/wallet/transfer", gson.toJson(body));
        Type type = new TypeToken<OtherModels.APIResponse<WalletModels.TransferResult>>(){}.getType();
        OtherModels.APIResponse<WalletModels.TransferResult> apiResp = gson.fromJson(
                new String(resp, StandardCharsets.UTF_8), type);
        return apiResp.data;
    }

    /**
     * Transfer from spot to futures
     */
    public WalletModels.TransferResult transferToFutures(String amount) throws MudrexException {
        return transfer(Enums.WalletType.SPOT, Enums.WalletType.FUTURES, amount);
    }

    /**
     * Transfer from futures to spot
     */
    public WalletModels.TransferResult transferToSpot(String amount) throws MudrexException {
        return transfer(Enums.WalletType.FUTURES, Enums.WalletType.SPOT, amount);
    }
}

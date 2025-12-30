package com.mudrex.sdk.models;

import com.google.gson.annotations.SerializedName;
import java.time.LocalDateTime;

/**
 * Leverage and Fee models
 */
public class OtherModels {

    public static class Leverage {
        @SerializedName("asset_id")
        public String assetId;
        
        @SerializedName("leverage")
        public String leverage;
        
        @SerializedName("margin_type")
        public Enums.MarginType marginType;

        @Override
        public String toString() {
            return "Leverage{" +
                    "assetId='" + assetId + '\'' +
                    ", leverage='" + leverage + '\'' +
                    ", marginType=" + marginType +
                    '}';
        }
    }

    public static class FeeRecord {
        @SerializedName("asset_id")
        public String assetId;
        
        @SerializedName("symbol")
        public String symbol;
        
        @SerializedName("fee_amount")
        public String feeAmount;
        
        @SerializedName("fee_rate")
        public String feeRate;
        
        @SerializedName("trade_type")
        public String tradeType;
        
        @SerializedName("order_id")
        public String orderId;
        
        @SerializedName("created_at")
        public LocalDateTime createdAt;

        @Override
        public String toString() {
            return "FeeRecord{" +
                    "symbol='" + symbol + '\'' +
                    ", feeAmount='" + feeAmount + '\'' +
                    ", tradeType='" + tradeType + '\'' +
                    '}';
        }
    }

    public static class APIResponse<T> {
        @SerializedName("success")
        public Boolean success;
        
        @SerializedName("message")
        public String message;
        
        @SerializedName("data")
        public T data;
        
        @SerializedName("error")
        public APIError error;
    }

    public static class APIError {
        @SerializedName("code")
        public Integer code;
        
        @SerializedName("message")
        public String message;
    }
}

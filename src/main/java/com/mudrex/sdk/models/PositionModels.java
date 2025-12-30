package com.mudrex.sdk.models;

import com.google.gson.annotations.SerializedName;
import java.time.LocalDateTime;

/**
 * Position-related models
 */
public class PositionModels {

    public static class Position {
        @SerializedName("position_id")
        public String positionId;
        
        @SerializedName("symbol")
        public String symbol;
        
        @SerializedName("asset_id")
        public String assetId;
        
        @SerializedName("entry_price")
        public String entryPrice;
        
        @SerializedName("quantity")
        public String quantity;
        
        @SerializedName("side")
        public Enums.OrderType side;
        
        @SerializedName("status")
        public Enums.PositionStatus status;
        
        @SerializedName("leverage")
        public String leverage;
        
        @SerializedName("unrealized_pnl")
        public String unrealizedPnL;
        
        @SerializedName("realized_pnl")
        public String realizedPnL;
        
        @SerializedName("margin")
        public String margin;
        
        @SerializedName("margin_ratio")
        public String marginRatio;
        
        @SerializedName("mark_price")
        public String markPrice;
        
        @SerializedName("stop_loss")
        public String stopLoss;
        
        @SerializedName("take_profit")
        public String takeProfit;
        
        @SerializedName("created_at")
        public LocalDateTime createdAt;
        
        @SerializedName("updated_at")
        public LocalDateTime updatedAt;

        public double getPnLPercentage() {
            // Placeholder - actual implementation would calculate from prices
            return 0.0;
        }

        @Override
        public String toString() {
            return "Position{" +
                    "symbol='" + symbol + '\'' +
                    ", unrealizedPnL='" + unrealizedPnL + '\'' +
                    ", side=" + side +
                    '}';
        }
    }

    public static class RiskOrder {
        @SerializedName("order_id")
        public String orderId;
        
        @SerializedName("position_id")
        public String positionId;
        
        @SerializedName("order_type")
        public String orderType;
        
        @SerializedName("trigger_price")
        public String triggerPrice;
        
        @SerializedName("execution_price")
        public String executionPrice;
        
        @SerializedName("status")
        public String status;
        
        @SerializedName("created_at")
        public LocalDateTime createdAt;
        
        @SerializedName("updated_at")
        public LocalDateTime updatedAt;
    }
}

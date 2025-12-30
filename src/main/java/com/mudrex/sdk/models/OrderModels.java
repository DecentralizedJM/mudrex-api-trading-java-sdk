package com.mudrex.sdk.models;

import com.google.gson.annotations.SerializedName;
import java.time.LocalDateTime;

/**
 * Order-related models
 */
public class OrderModels {

    public static class OrderRequest {
        @SerializedName("leverage")
        public String leverage;
        
        @SerializedName("quantity")
        public String quantity;
        
        @SerializedName("order_type")
        public Enums.OrderType orderType;
        
        @SerializedName("trigger_type")
        public Enums.TriggerType triggerType;
        
        @SerializedName("price")
        public String price;
        
        @SerializedName("stoploss_price")
        public String stoplossPrice;
        
        @SerializedName("takeprofit_price")
        public String takeprofitPrice;
        
        @SerializedName("reduce_only")
        public Boolean reduceOnly;

        public OrderRequest(String leverage, String quantity, Enums.OrderType orderType, Enums.TriggerType triggerType) {
            this.leverage = leverage;
            this.quantity = quantity;
            this.orderType = orderType;
            this.triggerType = triggerType;
        }
    }

    public static class Order {
        @SerializedName("order_id")
        public String orderId;
        
        @SerializedName("symbol")
        public String symbol;
        
        @SerializedName("asset_id")
        public String assetId;
        
        @SerializedName("order_type")
        public Enums.OrderType orderType;
        
        @SerializedName("trigger_type")
        public Enums.TriggerType triggerType;
        
        @SerializedName("price")
        public String price;
        
        @SerializedName("quantity")
        public String quantity;
        
        @SerializedName("filled_quantity")
        public String filledQuantity;
        
        @SerializedName("avg_filled_price")
        public String avgFilledPrice;
        
        @SerializedName("status")
        public Enums.OrderStatus status;
        
        @SerializedName("leverage")
        public String leverage;
        
        @SerializedName("stoploss_price")
        public String stoplossPrice;
        
        @SerializedName("takeprofit_price")
        public String takeprofitPrice;
        
        @SerializedName("created_at")
        public LocalDateTime createdAt;
        
        @SerializedName("updated_at")
        public LocalDateTime updatedAt;
        
        @SerializedName("reduce_only")
        public Boolean reduceOnly;

        @Override
        public String toString() {
            return "Order{" +
                    "orderId='" + orderId + '\'' +
                    ", symbol='" + symbol + '\'' +
                    ", status=" + status +
                    '}';
        }
    }
}

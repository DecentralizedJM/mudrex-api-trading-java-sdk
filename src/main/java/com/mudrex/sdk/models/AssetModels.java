package com.mudrex.sdk.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Asset-related models
 */
public class AssetModels {

    public static class Asset {
        @SerializedName("asset_id")
        public String assetId;
        
        @SerializedName("symbol")
        public String symbol;
        
        @SerializedName("base_currency")
        public String baseCurrency;
        
        @SerializedName("quote_currency")
        public String quoteCurrency;
        
        @SerializedName("min_quantity")
        public String minQuantity;
        
        @SerializedName("max_quantity")
        public String maxQuantity;
        
        @SerializedName("quantity_step")
        public String quantityStep;
        
        @SerializedName("min_leverage")
        public String minLeverage;
        
        @SerializedName("max_leverage")
        public String maxLeverage;
        
        @SerializedName("maker_fee")
        public String makerFee;
        
        @SerializedName("taker_fee")
        public String takerFee;
        
        @SerializedName("is_active")
        public Boolean isActive;

        @Override
        public String toString() {
            return "Asset{" +
                    "symbol='" + symbol + '\'' +
                    ", assetId='" + assetId + '\'' +
                    ", maxLeverage='" + maxLeverage + '\'' +
                    '}';
        }
    }

    public static class AssetListResponse {
        @SerializedName("assets")
        public List<Asset> assets;
        
        @SerializedName("page")
        public Integer page;
        
        @SerializedName("per_page")
        public Integer perPage;
        
        @SerializedName("total")
        public Integer total;
        
        @SerializedName("total_pages")
        public Integer totalPages;
    }
}

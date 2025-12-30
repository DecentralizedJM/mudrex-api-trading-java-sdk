package com.mudrex.sdk.models;

import com.google.gson.annotations.SerializedName;

/**
 * Wallet-related models
 */
public class WalletModels {

    public static class WalletBalance {
        @SerializedName("total")
        public String total;
        
        @SerializedName("available")
        public String available;
        
        @SerializedName("rewards")
        public String rewards;
        
        @SerializedName("withdrawable")
        public String withdrawable;
        
        @SerializedName("currency")
        public String currency;

        @Override
        public String toString() {
            return "WalletBalance{" +
                    "total='" + total + '\'' +
                    ", available='" + available + '\'' +
                    ", rewards='" + rewards + '\'' +
                    ", withdrawable='" + withdrawable + '\'' +
                    ", currency='" + currency + '\'' +
                    '}';
        }
    }

    public static class FuturesBalance {
        @SerializedName("balance")
        public String balance;
        
        @SerializedName("available_transfer")
        public String availableTransfer;
        
        @SerializedName("unrealized_pnl")
        public String unrealizedPnL;
        
        @SerializedName("margin_used")
        public String marginUsed;
        
        @SerializedName("currency")
        public String currency;

        @Override
        public String toString() {
            return "FuturesBalance{" +
                    "balance='" + balance + '\'' +
                    ", availableTransfer='" + availableTransfer + '\'' +
                    ", unrealizedPnL='" + unrealizedPnL + '\'' +
                    ", marginUsed='" + marginUsed + '\'' +
                    ", currency='" + currency + '\'' +
                    '}';
        }
    }

    public static class TransferResult {
        @SerializedName("transaction_id")
        public String transactionId;
        
        @SerializedName("success")
        public Boolean success;

        @Override
        public String toString() {
            return "TransferResult{" +
                    "transactionId='" + transactionId + '\'' +
                    ", success=" + success +
                    '}';
        }
    }
}

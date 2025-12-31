package com.mudrex.sdk.models;

import com.google.gson.annotations.SerializedName;

/**
 * Wallet-related models
 */
public class WalletModels {

    public static class WalletBalance {
        @SerializedName("total")
        public String total;
        
        @SerializedName("withdrawable")
        public String withdrawable;
        
        @SerializedName("invested")
        public String invested;
        
        @SerializedName("rewards")
        public String rewards;
        
        @SerializedName("coin_investable")
        public String coinInvestable;
        
        @SerializedName("coinset_investable")
        public String coinsetInvestable;
        
        @SerializedName("vault_investable")
        public String vaultInvestable;

        @Override
        public String toString() {
            return "WalletBalance{" +
                    "total='" + total + '\'' +
                    ", withdrawable='" + withdrawable + '\'' +
                    ", invested='" + invested + '\'' +
                    ", rewards='" + rewards + '\'' +
                    ", coinInvestable='" + coinInvestable + '\'' +
                    ", coinsetInvestable='" + coinsetInvestable + '\'' +
                    ", vaultInvestable='" + vaultInvestable + '\'' +
                    '}';
        }
    }

    public static class FuturesBalance {
        @SerializedName("balance")
        public String balance;
        
        @SerializedName("locked_amount")
        public String lockedAmount;
        
        @SerializedName("first_time_user")
        public boolean firstTimeUser;

        @Override
        public String toString() {
            return "FuturesBalance{" +
                    "balance='" + balance + '\'' +
                    ", lockedAmount='" + lockedAmount + '\'' +
                    ", firstTimeUser=" + firstTimeUser +
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

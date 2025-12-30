package com.mudrex.sdk.models;

import com.google.gson.annotations.SerializedName;
import java.time.LocalDateTime;

/**
 * Enums for Mudrex Trading API
 */
public class Enums {
    public enum OrderType {
        @SerializedName("LONG")
        LONG,
        @SerializedName("SHORT")
        SHORT
    }

    public enum TriggerType {
        @SerializedName("MARKET")
        MARKET,
        @SerializedName("LIMIT")
        LIMIT
    }

    public enum MarginType {
        @SerializedName("ISOLATED")
        ISOLATED
    }

    public enum OrderStatus {
        @SerializedName("OPEN")
        OPEN,
        @SerializedName("FILLED")
        FILLED,
        @SerializedName("PARTIALLY_FILLED")
        PARTIALLY_FILLED,
        @SerializedName("CANCELLED")
        CANCELLED,
        @SerializedName("EXPIRED")
        EXPIRED
    }

    public enum PositionStatus {
        @SerializedName("OPEN")
        OPEN,
        @SerializedName("CLOSED")
        CLOSED
    }

    public enum WalletType {
        @SerializedName("SPOT")
        SPOT,
        @SerializedName("FUTURES")
        FUTURES
    }
}

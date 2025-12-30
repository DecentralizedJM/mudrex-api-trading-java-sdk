# Mudrex Java SDK

[![Java 11+](https://img.shields.io/badge/java-11+-blue.svg)](https://www.oracle.com/java/technologies/downloads/)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![GitHub](https://img.shields.io/github/stars/DecentralizedJM/mudrex-java-sdk?style=social)](https://github.com/DecentralizedJM/mudrex-java-sdk)

**Unofficial Java SDK for [Mudrex Futures Trading API](https://docs.trade.mudrex.com/docs/overview)** - Enterprise-ready trading client for Java developers.

**Built and maintained by [DecentralizedJM](https://github.com/DecentralizedJM)**

## 🚀 Features

- **Enterprise-Ready** - Built for production trading systems
- **Type-Safe** - Full type definitions and models with POJO classes
- **High-Performance** - Built-in rate limiting and efficient request handling
- **Well-Documented** - Comprehensive JavaDoc and examples
- **Maven Support** - Easy integration with Maven/Gradle projects

## 📦 Installation

### Maven
```xml
<dependency>
    <groupId>com.mudrex</groupId>
    <artifactId>mudrex-trading-sdk</artifactId>
    <version>1.0.0</version>
</dependency>
```

### Gradle
```gradle
implementation 'com.mudrex:mudrex-trading-sdk:1.0.0'
```

### Or build from source
```bash
git clone https://github.com/DecentralizedJM/mudrex-java-sdk.git
cd mudrex-java-sdk
mvn clean install
```

## ⚡ Quick Start

```java
import com.mudrex.sdk.MudrexClient;
import com.mudrex.sdk.models.Enums;
import com.mudrex.sdk.models.WalletModels;
import com.mudrex.sdk.exceptions.MudrexException;

public class Example {
    public static void main(String[] args) throws MudrexException {
        // Initialize the client
        try (MudrexClient client = new MudrexClient("your-api-secret")) {
            
            // Check your balance
            WalletModels.FuturesBalance balance = client.wallet.getFuturesBalance();
            System.out.println("Balance: " + balance.balance + " " + balance.currency);
            
            // List tradable assets
            client.assets.listAll(1, 10, "", "").forEach(asset ->
                System.out.println(asset.symbol + ": up to " + asset.maxLeverage + "x leverage")
            );
            
            // Set leverage
            var leverage = client.leverage.set("BTCUSDT", "10", Enums.MarginType.ISOLATED);
            System.out.println("Leverage set to: " + leverage.leverage);
            
            // Place a market order
            var order = client.orders.createMarketOrder(
                "BTCUSDT",
                Enums.OrderType.LONG,
                "0.001",
                "10"
            );
            System.out.println("Order placed: " + order.orderId);
            
            // Monitor positions
            client.positions.listOpen().forEach(pos ->
                System.out.println(pos.symbol + ": " + pos.unrealizedPnL + " PnL")
            );
        }
    }
}
```

## 📚 Documentation

### API Modules

| Module | Description |
|--------|-------------|
| `client.wallet` | Spot & futures wallet balances, fund transfers |
| `client.assets` | Discover tradable instruments, get specifications |
| `client.leverage` | Get/set leverage and margin type |
| `client.orders` | Create, view, cancel, and amend orders |
| `client.positions` | Manage positions, set SL/TP, close/reverse |
| `client.fees` | View trading fee history |

### Complete Trading Workflow

```java
try (MudrexClient client = new MudrexClient("your-api-secret")) {
    
    // 1. Check available balance
    var balance = client.wallet.getFuturesBalance();
    System.out.println("Available: " + balance.availableTransfer);
    
    // 2. Discover available assets
    var assets = client.assets.listAll(1, 50, "symbol", "asc");
    
    // 3. Set leverage for the asset
    client.leverage.set("BTCUSDT", "5", Enums.MarginType.ISOLATED);
    
    // 4. Place an order with stop loss and take profit
    var order = client.orders.createMarketOrder(
        "BTCUSDT",
        Enums.OrderType.LONG,
        "0.001",
        "5"
    );
    
    // 5. Monitor your position
    var positions = client.positions.listOpen();
    for (var pos : positions) {
        if (pos.positionId.equals(order.orderId)) {
            // Set stop loss at 5% below entry
            client.positions.setStopLoss(pos.positionId, "95000");
        }
    }
    
    // 6. Close position when ready
    client.positions.close(order.orderId);
}
```

## 🔧 Configuration

### Custom Base URL and Timeout

```java
import java.time.Duration;

MudrexClient client = new MudrexClient(
    "your-api-secret",
    "https://custom.mudrex.com/fapi/v1",
    Duration.ofSeconds(30)
);
```

## ⚠️ Error Handling

```java
import com.mudrex.sdk.exceptions.MudrexException;

try {
    var balance = client.wallet.getFuturesBalance();
} catch (MudrexException e) {
    int code = e.getCode();
    int httpStatus = e.getHttpStatus();
    
    if (httpStatus == 401) {
        System.out.println("Authentication failed");
    } else if (httpStatus == 429) {
        System.out.println("Rate limit exceeded");
    } else if (httpStatus == 400 && code == 1002) {
        System.out.println("Insufficient balance");
    } else {
        e.printStackTrace();
    }
}
```

## 🧪 Testing

```bash
mvn test
```

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 👥 Contributors

- [@DecentralizedJM](https://github.com/DecentralizedJM) - Creator & Maintainer

## 📄 License

MIT License - see [LICENSE](LICENSE) for details.

## 🔗 Links

- [Mudrex Trading API Docs](https://docs.trade.mudrex.com)
- [API Quick Reference](https://docs.trade.mudrex.com/docs/overview)
- [Mudrex Platform](https://mudrex.com)

## ⚠️ Disclaimer

**This is an UNOFFICIAL SDK.** This SDK is for educational and informational purposes. Cryptocurrency trading involves significant risk. Always:
- Start with small amounts
- Use proper risk management (stop-losses)
- Never trade more than you can afford to lose
- Test thoroughly in a safe environment first

---

Built and maintained by [DecentralizedJM](https://github.com/DecentralizedJM) with ❤️

package io.bineng.api.test;

import io.bineng.api.client.BiNengApiClientFactory;
import io.bineng.api.client.BiNengApiWebSocketClient;
import io.bineng.api.client.domain.market.CandlestickInterval;


public class MarketDataStreamTest {

    public static void main(String[] args) {

        BiNengApiWebSocketClient client = BiNengApiClientFactory.newInstance().newWebSocketClient();

        // depth
        client.onDepthEvent("BTCUSDT", response -> System.out.println(response));

        // kline
        client.onCandlestickEvent("BTCUSDT", CandlestickInterval.ONE_MINUTE, response -> System.out.println(response));

        // trades
        client.onTradeEvent("BTCUSDT", response -> System.out.println(response));

        // ticker for 24 hour
        client.onTicker24HourEvent("BTCUSDT", response -> System.out.println(response));

        // index
        client.onIndexEvent("BTCUSDT", System.out::println);
    }
}

package io.bineng.api.test;

import io.bineng.api.client.BiNengApiClientFactory;
import io.bineng.api.client.BiNengApiRestClient;
import io.bineng.api.client.domain.market.BookTicker;
import io.bineng.api.client.domain.market.Candlestick;
import io.bineng.api.client.domain.market.CandlestickInterval;
import io.bineng.api.client.domain.market.Index;
import io.bineng.api.client.domain.market.OrderBook;
import io.bineng.api.client.domain.market.TickerPrice;
import io.bineng.api.client.domain.market.TickerStatistics;
import io.bineng.api.client.domain.market.TradeHistoryItem;

import java.util.List;

public class MarketDataRestApiTest {


    public static void main(String[] args) {

        BiNengApiClientFactory factory = BiNengApiClientFactory.newInstance();
        BiNengApiRestClient client = factory.newRestClient();
        String symbol = "BTCUSDT";


        System.out.println("\n ------Ping-----");
        client.ping();

        System.out.println("\n ------serverTime-----");
        long serverTime = client.getServerTime();
        System.out.println(serverTime);

        System.out.println("\n ------get order book-----");
        OrderBook orderBook = client.getOrderBook(symbol, 100);
        System.out.println(orderBook);

        System.out.println("\n ------get recent trades-----");
        List<TradeHistoryItem> tradeHistoryItemList = client.getTrades(symbol, 10);
        System.out.println(tradeHistoryItemList);

        System.out.println("\n ------get klines-----");
        List<Candlestick> candlestickList = client.getCandlestickBars(symbol, CandlestickInterval.ONE_MINUTE, 0l, 0L, 0);
        System.out.println(candlestickList);

        System.out.println("\n ------get 24h ticker-----");
        TickerStatistics tickerStatistics = client.get24HrPriceStatistics(symbol);
        System.out.println(tickerStatistics);

        System.out.println("\n ------get ticker price-----");
        TickerPrice tickerPrice = client.getPrice(symbol);
        System.out.println(tickerPrice);

        System.out.println("\n ------get book ticker-----");
        BookTicker bookTickerList = client.getBookTicker(symbol);
        System.out.println(bookTickerList);

        System.out.println("\n ------get index-----");
        Index index = client.getIndex(symbol);
        System.out.println(index);
    }
}

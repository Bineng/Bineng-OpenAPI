package io.bineng.api.client.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;

import io.bineng.api.client.BiNengApiCallback;
import io.bineng.api.client.BiNengApiWebSocketClient;
import io.bineng.api.client.constant.BiNengConstants;
import io.bineng.api.client.domain.account.SocketUserResponse;
import io.bineng.api.client.domain.channel.ChannelRequest;
import io.bineng.api.client.domain.channel.EventTopic;
import io.bineng.api.client.domain.channel.EventType;
import io.bineng.api.client.domain.event.CandlestickEvent;
import io.bineng.api.client.domain.event.DepthEvent;
import io.bineng.api.client.domain.event.IndexEvent;
import io.bineng.api.client.domain.event.TickerEvent;
import io.bineng.api.client.domain.event.TradeEvent;
import io.bineng.api.client.domain.market.CandlestickInterval;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

import java.io.Closeable;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * BHex API WebSocket client implementation using OkHttp.
 */
@Slf4j
public class BiNengApiWebSocketClientImpl implements BiNengApiWebSocketClient, Closeable {

    private final OkHttpClient client;
    private String wsApiQuoteUrl = BiNengConstants.WS_API_BASE_URL;
    private String wsApiUserUrl = BiNengConstants.WS_API_USER_URL;

    private ExecutorService executorService = Executors.newCachedThreadPool();

    public BiNengApiWebSocketClientImpl(OkHttpClient client) {
        this.client = client;
    }

    public BiNengApiWebSocketClientImpl(OkHttpClient client, String wsApiQuoteUrl, String wsApiUserUrl) {
        this.client = client;
        this.wsApiQuoteUrl = wsApiQuoteUrl;
        this.wsApiUserUrl = wsApiUserUrl;
    }

    @Override
    public Closeable onDepthEvent(String symbols, BiNengApiCallback<DepthEvent> callback) {
        return this.onDepthEvent(symbols, callback, false);
    }

    @Override
    public Closeable onDepthEvent(String symbols, BiNengApiCallback<DepthEvent> callback, boolean retry) {

        ChannelRequest request = new ChannelRequest();
        request.setSymbol(symbols);
        request.setTopic(EventTopic.DEPTH.getTopic());
        request.setEvent(EventType.SUB.getType());
        ObjectMapper mapper = new ObjectMapper();
        try {
            String channel = mapper.writeValueAsString(request);
            return createNewWebSocket(channel, new BiNengApiWebSocketListener<>(callback, DepthEvent.class), retry);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Closeable onCandlestickEvent(String symbols, CandlestickInterval interval, BiNengApiCallback<CandlestickEvent> callback) {
        return this.onCandlestickEvent(symbols, interval, callback, false);
    }

    @Override
    public Closeable onCandlestickEvent(String symbols, CandlestickInterval interval, BiNengApiCallback<CandlestickEvent> callback, boolean retry) {
        ChannelRequest request = new ChannelRequest();
        request.setSymbol(symbols);
        request.setTopic(EventTopic.KLINE.getTopic(interval.getIntervalId()));
        request.setEvent(EventType.SUB.getType());
        ObjectMapper mapper = new ObjectMapper();
        try {
            String channel = mapper.writeValueAsString(request);
            return createNewWebSocket(channel, new BiNengApiWebSocketListener<>(callback, CandlestickEvent.class), retry);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Closeable onTradeEvent(String symbols, BiNengApiCallback<TradeEvent> callback) {
        return this.onTradeEvent(symbols, callback, false);
    }

    @Override
    public Closeable onTradeEvent(String symbols, BiNengApiCallback<TradeEvent> callback, boolean retry) {
        ChannelRequest request = new ChannelRequest();
        request.setSymbol(symbols);
        request.setTopic(EventTopic.TRADES.getTopic());
        request.setEvent(EventType.SUB.getType());
        ObjectMapper mapper = new ObjectMapper();
        try {
            String channel = mapper.writeValueAsString(request);
            return createNewWebSocket(channel, new BiNengApiWebSocketListener<>(callback, TradeEvent.class), retry);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Closeable onTicker24HourEvent(String symbols, BiNengApiCallback<TickerEvent> callback) {
        return onTicker24HourEvent(symbols, callback, false);
    }

    @Override
    public Closeable onTicker24HourEvent(String symbols, BiNengApiCallback<TickerEvent> callback, boolean retry) {
        ChannelRequest request = new ChannelRequest();
        request.setSymbol(symbols);
        request.setTopic(EventTopic.REALTIMES.getTopic());
        request.setEvent(EventType.SUB.getType());
        ObjectMapper mapper = new ObjectMapper();
        try {
            String channel = mapper.writeValueAsString(request);
            return createNewWebSocket(channel, new BiNengApiWebSocketListener<>(callback, TickerEvent.class), retry);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Closeable onIndexEvent(String symbols, BiNengApiCallback<IndexEvent> callback) {
        return onIndexEvent(symbols, callback, false);
    }

    @Override
    public Closeable onIndexEvent(String symbols, BiNengApiCallback<IndexEvent> callback, boolean retry) {
        ChannelRequest request = new ChannelRequest();
        request.setSymbol(symbols);
        request.setTopic(EventTopic.INDEX.getTopic());
        request.setEvent(EventType.SUB.getType());
        ObjectMapper mapper = new ObjectMapper();
        try {
            String channel = mapper.writeValueAsString(request);
            return createNewWebSocket(channel, new BiNengApiWebSocketListener<>(callback, IndexEvent.class), retry);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Closeable onUserEvent(String listenKey, BiNengApiCallback<SocketUserResponse> callback) {
        return this.onUserEvent(listenKey, callback, false);
    }

    @Override
    public Closeable onUserEvent(String listenKey, BiNengApiCallback<SocketUserResponse> callback, boolean retry) {
        ChannelRequest request = new ChannelRequest();
        request.setTopic(EventTopic.ORDER.getTopic());
        request.setEvent(EventType.SUB.getType());
        ObjectMapper mapper = new ObjectMapper();
        try {
            BiNengApiWebSocketUserListener listener = new BiNengApiWebSocketUserListener<>(callback);
            String channel = mapper.writeValueAsString(request);
            Closeable closeable = createNewUserWebSocket(channel, listenKey, listener, retry);
            return closeable;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @deprecated This method is no longer functional. Please use the returned {@link Closeable} from any of the other methods to close the web socket.
     */
    @Override
    public void close() {
    }

    private Closeable createNewWebSocket(String channel, BiNengApiWebSocketListener<?> listener, boolean retry) {
        String streamingUrl = String.format("%s/%s", wsApiQuoteUrl, channel);
        Request request = new Request.Builder().url(streamingUrl).build();
        final WebSocket webSocket = client.newWebSocket(request, listener);
        webSocket.send(channel);
        Closeable closeable = createCloseable(listener, webSocket);
        // 添加监控线程
        executorService.execute(this.socketMonitor(webSocket, channel, listener, retry));
        return closeable;
    }

    private Closeable createNewUserWebSocket(String channel, String listenKey, BiNengApiWebSocketUserListener<?> listener, boolean retry) {
        Request request = new Request.Builder().url(wsApiUserUrl + listenKey).build();
        listener.setFailure(false);
        final WebSocket webSocket = client.newWebSocket(request, listener);
        webSocket.send(channel);
        Closeable closeable = createCloseable(listener, webSocket);
        if (retry) {
            // 添加监控线程
            executorService.execute(this.userSocketMonitor(channel, listenKey, listener));
        }
        return closeable;
    }

    public Closeable createCloseable(WebSocketListener listener, final WebSocket webSocket) {
        try {
            return () -> {
                final int code = 1000;
                listener.onClosing(webSocket, code, null);
                webSocket.close(code, null);
                listener.onClosed(webSocket, code, null);
            };
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Runnable socketMonitor(WebSocket webSocket, String channel, BiNengApiWebSocketListener<?> listener, boolean retry) {
        return new Runnable() {

            private long lastTime = 0;
            private Map<String, Long> pingMap = Maps.newHashMap();

            @Override
            public void run() {
                while (true) {
                    try {
                        long time = System.currentTimeMillis();
                        // 间隔大于1分钟，发送一次心跳
                        if ((time - lastTime) > BiNengConstants.HEART_BEAT_INTERVAL) {
                            ObjectMapper mapper = new ObjectMapper();
                            pingMap.put(BiNengConstants.PING_MSG_KEY, time);
                            webSocket.send(mapper.writeValueAsString(pingMap));
                            lastTime = time;
                        }

                        Thread.sleep(200);
                        if (listener.getFailure() && retry) {
                            log.debug("socket monitor : retry to connect");
                            createNewWebSocket(channel, listener, true);
                            break;
                        }
                    } catch (Exception e) {
                        log.error(" socket monitor catch Exception: ", e);
                    }
                }
            }
        };
    }

    public Runnable userSocketMonitor(String channel, String listenKey, BiNengApiWebSocketUserListener<?> listener) {
        return () -> {
            while (true) {
                try {
                    Thread.sleep(200);
                    if (listener.getFailure()) {
                        log.debug("user socket monitor : retry to connect");
                        createNewUserWebSocket(channel, listenKey, listener, true);
                        break;
                    }
                } catch (Exception e) {
                    log.error(" user socket monitor catch Exception: ", e);
                }
            }
        };
    }
}

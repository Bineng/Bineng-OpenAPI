package io.bineng.api.client;

import io.bineng.api.client.constant.BiNengConstants;
import io.bineng.api.client.impl.BiNengApiRestClientImpl;
import io.bineng.api.client.impl.BiNengApiWebSocketClientImpl;
import io.bineng.api.client.impl.BiNengOptionApiRestClientImpl;
import static io.bineng.api.client.impl.BiNengApiServiceGenerator.getSharedClient;

/**
 * A factory for creating BHexApi client objects.
 */
public final class BiNengApiClientFactory {

    /**
     * API Key
     */
    private String apiKey;

    /**
     * Secret.
     */
    private String secret;

    private String baseUrl = BiNengConstants.API_BASE_URL;

    /**
     * Instantiates a new BHex api client factory.
     *
     * @param apiKey the API key
     * @param secret the Secret
     */
    private BiNengApiClientFactory(String apiKey, String secret) {
        this.apiKey = apiKey;
        this.secret = secret;
    }

    private BiNengApiClientFactory(String baseUrl, String apiKey, String secret) {
        this.baseUrl = baseUrl;
        this.apiKey = apiKey;
        this.secret = secret;
    }

    /**
     * New instance.
     *
     * @param apiKey the API key
     * @param secret the Secret
     * @return the BHex api client factory
     */
    public static BiNengApiClientFactory newInstance(String apiKey, String secret) {
        return new BiNengApiClientFactory(apiKey, secret);
    }

    /**
     * for bhop.cloud client and inner test only
     *
     * @param baseUrl
     * @param apiKey
     * @param secret
     * @return
     */
    public static BiNengApiClientFactory newInstance(String baseUrl, String apiKey, String secret) {
        return new BiNengApiClientFactory(baseUrl, apiKey, secret);
    }

    /**
     * New instance without authentication.
     *
     * @return the BHex api client factory
     */
    public static BiNengApiClientFactory newInstance() {
        return new BiNengApiClientFactory(null, null);
    }

    /**
     * Creates a new synchronous/blocking REST client.
     */
    public BiNengApiRestClient newRestClient() {
        return new BiNengApiRestClientImpl(baseUrl, apiKey, secret);
    }


    public BiNengApiWebSocketClient newWebSocketClient() {
        return new BiNengApiWebSocketClientImpl(getSharedClient(), BiNengConstants.WS_API_BASE_URL, BiNengConstants.WS_API_USER_URL);
    }

    /**
     * for bhop.cloud client and inner test only
     *
     * @param wsApiBaseUrl
     * @param wsApiUserUrl
     * @return
     */
    public BiNengApiWebSocketClient newWebSocketClient(String wsApiBaseUrl, String wsApiUserUrl) {
        return new BiNengApiWebSocketClientImpl(getSharedClient(), wsApiBaseUrl, wsApiUserUrl);
    }

    /**
     * Creates a new synchronous/blocking Option REST client.
     */
    public BiNengOptionApiRestClient newOptionRestClient() {
        return new BiNengOptionApiRestClientImpl(baseUrl, apiKey, secret);
    }

}

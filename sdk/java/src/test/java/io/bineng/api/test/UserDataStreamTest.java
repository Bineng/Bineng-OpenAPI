package io.bineng.api.test;

import io.bineng.api.client.BiNengApiClientFactory;
import io.bineng.api.client.BiNengApiRestClient;
import io.bineng.api.client.BiNengApiWebSocketClient;
import io.bineng.api.client.constant.BiNengConstants;
import io.binengapi.test.constant.Constants;

//@Slf4j
public class UserDataStreamTest {

    public static void main(String[] args) {
//
        BiNengApiWebSocketClient client = BiNengApiClientFactory.newInstance().newWebSocketClient();
        BiNengApiRestClient restClient = BiNengApiClientFactory.newInstance(Constants.ACCESS_KEY, Constants.SECRET_KEY).newRestClient();

        System.out.println("\n ------Get Listen Key -----");
        System.out.println();
        String listenKey = restClient.startUserDataStream(BiNengConstants.DEFAULT_RECEIVING_WINDOW, System.currentTimeMillis());
        System.out.println("listenKey:" + listenKey);
        // order
        client.onUserEvent(listenKey, response -> System.out.println(response), true);

    }
}

package io.bineng.api.test;

import io.bineng.api.client.BiNengApiClientFactory;
import io.bineng.api.client.BiNengApiRestClient;
import io.bineng.api.client.constant.BiNengConstants;
import io.binengapi.test.constant.Constants;

public class UserDataStreamRestApiTest {

    public static void main(String[] args) {

        BiNengApiClientFactory factory = BiNengApiClientFactory.newInstance(Constants.ACCESS_KEY, Constants.SECRET_KEY);
        BiNengApiRestClient client = factory.newRestClient();

        System.out.println("\n ------start user data stream-----");
        String listenKey = client.startUserDataStream(BiNengConstants.DEFAULT_RECEIVING_WINDOW, System.currentTimeMillis());
        System.out.println(listenKey);

        System.out.println("\n ------keepAlive user data stream-----");
        client.keepAliveUserDataStream(listenKey, BiNengConstants.DEFAULT_RECEIVING_WINDOW, System.currentTimeMillis());

        System.out.println("\n ------close user data stream-----");
        client.closeUserDataStream(listenKey, BiNengConstants.DEFAULT_RECEIVING_WINDOW, System.currentTimeMillis());

    }

}

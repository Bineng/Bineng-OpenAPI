package io.bineng.api.test;

import io.bineng.api.client.BiNengApiClientFactory;
import io.bineng.api.client.BiNengApiRestClient;
import io.bineng.api.client.domain.general.BrokerInfo;

public class GeneralRestApiTest {

    public static void main(String[] args) {

        BiNengApiClientFactory factory = BiNengApiClientFactory.newInstance();
        BiNengApiRestClient client = factory.newRestClient();

        System.out.println("\n ------BrokerInfo-----");
        BrokerInfo brokerInfo = client.getBrokerInfo();
        System.out.println(brokerInfo);

    }


}

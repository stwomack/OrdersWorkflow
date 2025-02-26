package com.womack.ordersworkflow.activities;

import com.womack.ordersworkflow.domain.Customer;
import com.womack.ordersworkflow.domain.OrderActivityOutput;
import com.womack.ordersworkflow.domain.OrderItem;
import com.womack.ordersworkflow.domain.OrderPackage;
import com.womack.ordersworkflow.domain.Payment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import java.util.List;

public class OrderActivitiesImpl  implements OrderActivities {
    private String serviceUrl;
    RestTemplate restTemplate = new RestTemplate();
    public static final Logger LOG = LoggerFactory.getLogger(OrderActivitiesImpl.class);

    @Override
    public OrderActivityOutput processPayment(Payment payment) {
        LOG.debug("orderprocessingservice-url {} ", serviceUrl);
        String response = restTemplate.postForObject(serviceUrl + "processPayment", getHttpEntity(payment), String.class);
        return new OrderActivityOutput(response);
    }

    @Override
    public OrderActivityOutput checkInventory(List<OrderItem> orderItems) {
        String response = restTemplate.postForObject(serviceUrl + "checkInventory", getHttpEntity(orderItems), String.class);
        return new OrderActivityOutput(response);
    }

    @Override
    public OrderActivityOutput shipPackage(List<OrderPackage> orderPackages) {
        String response = restTemplate.postForObject(serviceUrl + "shipPackage", getHttpEntity(orderPackages), String.class);
        return new OrderActivityOutput(response);
    }

    @Override
    public OrderActivityOutput notifyCustomer(Customer customer) {
        String response = restTemplate.postForObject(serviceUrl + "notifyCustomer", getHttpEntity(customer), String.class);
        return new OrderActivityOutput(response);
    }

    private static HttpEntity<Object> getHttpEntity(Object object) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Object> requestEntity = new HttpEntity<>(object, headers);
        return requestEntity;
    }

    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }
}

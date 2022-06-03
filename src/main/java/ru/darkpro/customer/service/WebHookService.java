package ru.darkpro.customer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Service
public class WebHookService {

    private final RestTemplate restTemplate;
    private final URI uri = URI.create("https://webhook.site/98daafd7-3c49-414e-8cd7-03fcd03bc283");

    @Autowired
    public WebHookService(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    @Async
    public void send(Object request){
        restTemplate.postForEntity(uri, request, String.class);
    }
}

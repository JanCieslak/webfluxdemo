package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.reactive.function.client.WebClient;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class DemoApplicationTests {

    private final WebClient webClient = WebClient.create("http://localhost:8080");

    @Test
    void testSync() {
        System.out.println("1");

        var config = webClient.get()
                .uri("/config")
                .retrieve()
                .bodyToMono(Config.class)
                .block();

        System.out.println("2");

        assertThat(config)
                .isNotNull()
                .extracting(Config::getName)
                .isEqualTo("Name");

        System.out.println("3");
    }

    @Test
    void testAsync() throws InterruptedException {
        System.out.println("1");

        var response = webClient.get()
                .uri("/config")
                .retrieve()
                .bodyToMono(Config.class)
                .subscribe(config -> {
                    // Won't be tested
                    System.out.println("2");
                    assertThat(config)
                            .isNotNull()
                            .extracting(Config::getName)
                            .isEqualTo("Name");
                });

        System.out.println("3");
    }

    @Test
    void testLogSync() {
        var response = webClient.post()
                .uri("/log")
                .bodyValue(new Log("lulz"))
                .exchangeToMono(clientResponse -> clientResponse.bodyToMono(Void.class))
                .block();

        assertThat(response)
                .extracting(Void::getClass)
                .isEqualTo(Void.TYPE);
    }

}

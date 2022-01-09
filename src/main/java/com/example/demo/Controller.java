package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
public class Controller {

    @GetMapping("/config")
    public Mono<Config> getConfig() {
        log.info("Getting config");
        return Mono.just(new Config("Name"));
    }

    @PostMapping("/log")
    public Mono<Void> postLog(Log logs) {
        log.info("Log info: {}", logs.getMessage());
        System.out.println("lul");
        return Mono.empty();
    }

}

package com.exchange.filter;

import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class ResponseHeaderWebFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        exchange.getResponse().getHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.getResponse().getHeaders().add("Access-Control-Allow-Methods", "GET, PUT, POST, DELETE, OPTIONS");
        exchange.getResponse().getHeaders().add("Access-Control-Allow-Headers", "Content-Type, Authorization");
        exchange.getResponse().getHeaders().add("Access-Control-Expose-Headers", "Authorization");
        exchange.getResponse().getHeaders().add("Access-Control-Max-Age", "3600");
        exchange.getResponse().getHeaders().add("Access-Control-Allow-Credentials", "true");
        return chain.filter(exchange);
    }
}
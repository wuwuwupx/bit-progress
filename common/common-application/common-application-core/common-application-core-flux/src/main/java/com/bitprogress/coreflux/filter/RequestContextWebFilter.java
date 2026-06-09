package com.bitprogress.coreflux.filter;

import com.bitprogress.coreflux.holder.ReactiveRequestContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class RequestContextWebFilter implements WebFilter {

    /**
     * Process the Web request and (optionally) delegate to the next
     * {@code WebFilter} through the given {@link WebFilterChain}.
     * @param exchange the current server exchange
     * @param chain provides a way to delegate to the next filter
     * @return {@code Mono<Void>} to indicate when request processing is complete
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        return Mono.defer(() -> {
            ReactiveRequestContextHolder.setExchange(exchange);

            return chain.filter(exchange)
                    .doFinally(_ -> ReactiveRequestContextHolder.reset());
        });
    }

}
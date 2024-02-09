package com.book_store.full.filter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class MaxReqest implements Filter {

    private int maxRequests = 100;
    private AtomicInteger current_Requests = new AtomicInteger(0);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        if (current_Requests.get() >= maxRequests) {
            HttpServletResponse response2 = (HttpServletResponse) response;
            response2.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
            byte[] byteMessage = "Server Unavailable. Please retry after sometime..".getBytes(StandardCharsets.UTF_8);
            response.getOutputStream().write(byteMessage);
            return;
        }

        current_Requests.incrementAndGet();

        try {
            chain.doFilter(request, response);
        } finally {
            current_Requests.decrementAndGet();
        }

    }

}

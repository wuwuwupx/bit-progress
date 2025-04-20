package com.bitprogress.securityspring.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@AllArgsConstructor
public abstract class AbstractWhiteRequestFilter extends OncePerRequestFilter {

    /**
     * 请求拦截
     *
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (filter(request)) {
            doPreFilter(request);
        }
        try {
            filterChain.doFilter(request, response);
        } finally {
            doPostFilter(request, response);
        }
    }

    protected  abstract boolean filter(HttpServletRequest request);

    protected abstract void doPreFilter(HttpServletRequest request);

    protected abstract void doPostFilter(HttpServletRequest request, HttpServletResponse response);

}

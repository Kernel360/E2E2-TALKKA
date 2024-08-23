package com.talkka.server.common.log;

import java.io.IOException;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class LoggingFilter extends OncePerRequestFilter {
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {

		var cachingRequestWrapper = new ContentCachingRequestWrapper(request);
		var cachingResponseWrapper = new ContentCachingResponseWrapper(response);

		var startTime = System.currentTimeMillis();
		filterChain.doFilter(cachingRequestWrapper, cachingResponseWrapper);
		var endTime = System.currentTimeMillis();

		try {
			var logMessage = HttpLogMessage.create(cachingRequestWrapper, cachingResponseWrapper,
					(endTime - startTime) / 1000.0)
				.beatify();
			log.info(logMessage);
			cachingResponseWrapper.copyBodyToResponse();
		} catch (Exception exception) {
			log.error("Logging 실패");
		}
	}
}

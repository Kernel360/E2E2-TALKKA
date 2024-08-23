package com.talkka.server.common.log;

import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import lombok.Builder;

@Builder
public record HttpLogMessage(String httpMethod, String requestUri, Integer httpStatus, String clientIp,
							 Double elapsedTimeMs, String requestParam, String requestBody, String responseBody) {
	public static HttpLogMessage create(ContentCachingRequestWrapper request, ContentCachingResponseWrapper response,
		Double elapsedTimeMs) {
		return HttpLogMessage.builder()
			.httpMethod(request.getMethod())
			.requestUri(request.getRequestURI())
			.httpStatus(response.getStatus())
			.clientIp(request.getRemoteAddr())
			.elapsedTimeMs(elapsedTimeMs)
			.requestParam(request.getQueryString())
			.requestBody(new String(request.getContentAsByteArray()))
			.responseBody(new String(response.getContentAsByteArray()))
			.build();
	}

	public String beatify() {
		String format = """
			HTTP REQUEST %s %s %s
			| ClientIp: %s
			| ElapsedTimeMs: %s
			| RequestParam: %s
			| RequestBody: %s
			| ResponseBody: %s
			""";

		return String.format(format, httpMethod, requestUri, httpStatus, clientIp, elapsedTimeMs, requestParam,
			requestBody, responseBody
		);
	}
}

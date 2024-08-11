package com.talkka.server.api.datagg.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.talkka.server.api.core.config.ApiKeyProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Component
@ConfigurationProperties(prefix = "openapi.public.bus.service-key")
public class BusApiKeyProperty implements ApiKeyProperty {
	@Setter
	private List<String> keys;

	private int rollingKeyIndex = 0;

	@Override
	public String getApiKey() {
		rollingKeyIndex = (rollingKeyIndex + 1) % keys.size();
		return keys.get(rollingKeyIndex);
	}
}

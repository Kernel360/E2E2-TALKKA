package com.talkka.server.api.core.config;

public interface ApiKeyProvider {
	String getApiKey(String path);
}

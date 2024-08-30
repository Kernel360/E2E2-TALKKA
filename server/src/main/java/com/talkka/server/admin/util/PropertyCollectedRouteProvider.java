package com.talkka.server.admin.util;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@Setter
@Getter
@ConfigurationProperties(prefix = "bus.location.collect")
public class PropertyCollectedRouteProvider implements CollectedRouteProvider {
	private List<String> targetIdList;
}

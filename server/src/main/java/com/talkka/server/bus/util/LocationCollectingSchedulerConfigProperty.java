package com.talkka.server.bus.util;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "scheduling.collect.bus-location")
public class LocationCollectingSchedulerConfigProperty {
	private boolean enabled;
	private List<String> enabledTime;
}

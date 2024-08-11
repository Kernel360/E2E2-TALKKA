package com.talkka.server.api.datagg.dto;

import java.util.List;
import java.util.Map;

public interface PublicBusApiResp<T> {
	Map<String, String> comMsgHeader();

	Map<String, String> msgHeader();

	List<T> msgBody();
}

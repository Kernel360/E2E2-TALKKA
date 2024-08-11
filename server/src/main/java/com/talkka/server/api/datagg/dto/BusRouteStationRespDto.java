package com.talkka.server.api.datagg.dto;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JacksonXmlRootElement(localName = "response")
public class BusRouteStationRespDto implements PublicBusApiResp<BusRouteStationBodyDto> {
	@JacksonXmlProperty(localName = "comMsgHeader")
	private Map<String, String> comMsgHeader;
	@JacksonXmlProperty(localName = "msgHeader")
	private Map<String, String> msgHeader;
	@JacksonXmlProperty(localName = "msgBody")
	private List<BusRouteStationBodyDto> msgBody;
}

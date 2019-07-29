package com.ekaplus.msg;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class MyFirstRoute extends RouteBuilder {

	public static final String ROUTE_NAME = "TIMER_ROUTE";
	public static final String FILE_ROUTE_NAME = "FILE_ROUTE";
	public static final String ETL_ROUTE_NAME = "ETL_ROUTE";

	@Override
	public void configure() throws Exception {
		from("file:d://inputFolder?noop=true").routeId(FILE_ROUTE_NAME).to("file:d://outputFolder");
		from("timer:initial//start?period=10000").routeId(ROUTE_NAME).to("log:executed");

		from("timer:final?period={{timer.period}}").routeId(ETL_ROUTE_NAME).routeGroup(ETL_ROUTE_NAME).transform()
				.method("myBean", "saySomething").filter(simple("${body} contains 'foo'")).to("log:foo").end()
				.to("stream:out");
	}

}

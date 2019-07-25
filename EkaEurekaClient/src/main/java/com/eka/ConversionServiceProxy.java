package com.eka;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="eka-exchange-service")
@RibbonClient(name="eka-exchange-service")
public interface ConversionServiceProxy {
	 @GetMapping("/currency-exchange/from/{from}/to/{to}")
	  public ConversionBean retrieveExchangeValue(@PathVariable("from") String from, @PathVariable("to") String to);

}

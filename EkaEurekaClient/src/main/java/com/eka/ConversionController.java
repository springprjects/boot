

package com.eka;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
public class ConversionController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ConversionServiceProxy proxy;

	@GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
	@HystrixCommand(commandKey="conversionFromService",fallbackMethod = "findDefaultConversion")
	public ConversionBean convertCurrency(@PathVariable String from, @PathVariable String to,
			@PathVariable BigDecimal quantity) {

		Map<String, String> uriVariables = new HashMap<>();
		uriVariables.put("from", from.toUpperCase());
		uriVariables.put("to", to.toUpperCase());

		logger.info("{}",System.currentTimeMillis());
		ConversionBean response = getConversionValue(uriVariables).getBody();
		logger.info("{}",System.currentTimeMillis());
		
		
		return new ConversionBean(response.getId(), from, to, response.getConversionMultiple(), quantity,
				quantity.multiply(response.getConversionMultiple()), response.getPort());
	}

	@GetMapping("/currency-converter-feign/from/{from}/to/{to}/quantity/{quantity}")
	@HystrixCommand(commandKey="conversionFromServiceFeign",fallbackMethod = "findDefaultConversion")
	public ConversionBean convertCurrencyFeign(@PathVariable String from, @PathVariable String to,
			@PathVariable BigDecimal quantity) {
		logger.info("{}",System.currentTimeMillis());

		ConversionBean response = getFeignConversionVlue(from, to);

		logger.info("{}",System.currentTimeMillis());


		return new ConversionBean(response.getId(), from, to, response.getConversionMultiple(), quantity,
				quantity.multiply(response.getConversionMultiple()), response.getPort());
	}
	
	@Cacheable(value="conversion", key="#T(java.lang.Object).hash(#uriVariables)", condition="#uriVariables!=null&&#!uriVariables.isEmpty()", unless="#result.status==HttpStatus.OK")
	private ResponseEntity<ConversionBean> getConversionValue(Map<String, String> uriVariables) {
		logger.info("{} Entered getConversionValue");
		
		 ResponseEntity<ConversionBean> responseEntity=new RestTemplate().getForEntity("http://localhost:9091/currency-exchange/from/{from}/to/{to}",
				ConversionBean.class, uriVariables);
		
		logger.info("{} Exiting getConversionValue");
		return responseEntity;
	}

	@Cacheable()
	private ConversionBean getFeignConversionVlue(String from, String to) {
		logger.info("{} Exiting getConversionValue");

		ConversionBean conversionBean= proxy.retrieveExchangeValue(from, to);
		
		logger.info("{} Exiting getConversionValue");

		return conversionBean;
	}

	public ConversionBean findDefaultConversion(String arg1, String arg2, BigDecimal arg3) {
		ConversionBean conversionBean = new ConversionBean();
		conversionBean.setPort(8080);
		return conversionBean;
	}

}

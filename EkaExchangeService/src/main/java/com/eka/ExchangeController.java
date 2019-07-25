package com.eka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExchangeController {

	@Autowired
	private Environment environment;
	
	@Autowired
	ExchangeRepository exchangeRepository;
	
	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	  public ExchangeValue retrieveExchangeValue
	    (@PathVariable String from, @PathVariable String to){
	    
	    ExchangeValue exchangeValue = exchangeRepository.findByFromAndTo(from, to);
	    
	    exchangeValue.setPort(Integer.parseInt(environment.getProperty("local.server.port")));
	    
	    return exchangeValue;
	  }
}

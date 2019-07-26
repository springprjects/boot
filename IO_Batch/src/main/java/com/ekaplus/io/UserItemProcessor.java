package com.ekaplus.io;

import org.springframework.batch.item.ItemProcessor;

public class UserItemProcessor implements ItemProcessor<User, String> {

	@Override
	public String process(User item) throws Exception {
		System.out.println("Now Processing..."+item.toString());
		return item.getName();
	}

}

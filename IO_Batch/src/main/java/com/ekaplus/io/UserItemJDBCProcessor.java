package com.ekaplus.io;

import org.springframework.batch.item.ItemProcessor;

public class UserItemJDBCProcessor implements ItemProcessor<User, User> {

	@Override
	public User process(User item) throws Exception {
		System.out.println("Now Processing..."+item.toString());
		return item;
	}

}

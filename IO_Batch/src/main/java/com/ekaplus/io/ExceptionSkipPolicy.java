package com.ekaplus.io;

import java.util.List;

import javax.batch.api.chunk.listener.SkipProcessListener;
import javax.batch.api.chunk.listener.SkipReadListener;
import javax.batch.api.chunk.listener.SkipWriteListener;

import org.springframework.batch.core.step.skip.SkipLimitExceededException;
import org.springframework.batch.core.step.skip.SkipPolicy;

public class ExceptionSkipPolicy implements SkipPolicy,SkipProcessListener,SkipReadListener,SkipWriteListener {

	@Override
	public boolean shouldSkip(Throwable t, int skipCount) throws SkipLimitExceededException {
		System.out.println("Should Skip "+t.getMessage());
			return true;
	}

	@Override
	public void onSkipProcessItem(Object item, Exception ex) throws Exception {
		User u=(User)item;
		System.out.println("Skipp Processor"+u);
	}

	@Override
	public void onSkipReadItem(Exception ex) throws Exception {
		System.out.println("Skipping Header");
		
	}

	@Override
	public void onSkipWriteItem(List<Object> items, Exception ex) throws Exception {
		System.out.println("Skipping Header");
		
	}

}

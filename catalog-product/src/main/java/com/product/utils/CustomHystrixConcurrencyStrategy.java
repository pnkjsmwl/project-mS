package com.product.utils;

import java.util.concurrent.Callable;

import com.netflix.hystrix.strategy.HystrixPlugins;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategy;

//@Component
public class CustomHystrixConcurrencyStrategy extends HystrixConcurrencyStrategy {

	public CustomHystrixConcurrencyStrategy() {
		HystrixPlugins.getInstance().registerConcurrencyStrategy(this);
	}

	@Override
	public <T> Callable<T> wrapCallable(Callable<T> callable) {
		return new CustomCallable<>(callable, CustomThreadLocalHolder.getCorrelationId());
	}

	public static class CustomCallable<T> implements Callable<T> {
		private final Callable<T> actual;
		private final String correlationId;

		public CustomCallable(Callable<T> actual, String correlationId) {
			this.actual = actual;
			this.correlationId = correlationId;
		}
		@Override
		public T call() throws Exception {
			CustomThreadLocalHolder.setCorrelationId(correlationId);
			try{
				return actual.call();
			}finally {
				CustomThreadLocalHolder.setCorrelationId(null);
			}
		}
	}

	/*public static class CustomRunnable implements Runnable {
		@Override
		public void run() {
		}
	}*/
}

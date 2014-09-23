package com.marcoslop.jee.jaxrsutils.timer;

import java.util.concurrent.ScheduledThreadPoolExecutor;

public class ScheduledThreadPoolExecutorFactory {
	
	/**
	 * Default pool size. Number of proccesors *2
	 */
	protected final static int DEFAULT_POOL_SIZE = Runtime.getRuntime().availableProcessors() * 2;

    /**
	 * The instance used
	 */
	protected static ScheduledThreadPoolExecutor instance;
	
	static {
		int poolSize = DEFAULT_POOL_SIZE;

		instance = new ScheduledThreadPoolExecutor(poolSize);
	}

	public static ScheduledThreadPoolExecutor getInstance() {
		return instance;
	}
	
	

}

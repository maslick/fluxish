package io.maslick.fluxish;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class ExecutorConfig {
	@Bean("fixedThreadPool")
	public ExecutorService fixedThreadPool() {
		return Executors.newFixedThreadPool(15);
	}

	@Bean("cachedThreadPool")
	public ExecutorService cachedThreadPool() {
		return Executors.newCachedThreadPool();
	}
}

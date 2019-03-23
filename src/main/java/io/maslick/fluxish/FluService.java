package io.maslick.fluxish;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;

@Service
@Slf4j
public class FluService {
	private final ExecutorService executor;

	@Autowired
	public FluService(@Qualifier("fixedThreadPool") ExecutorService executor) {
		this.executor = executor;
	}

	public void start() {
		executor.submit(task());
	}

	private Runnable task() {
		return () -> {
			try {
				log.info("task started...");
				Thread.sleep(2000);
				log.info("task completed");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		};
	}
}

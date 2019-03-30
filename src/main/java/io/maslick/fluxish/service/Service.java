package io.maslick.fluxish.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Service implements IService {

	@Override
	public void run(String input) {
		log.info("Processing input: {}", input);
	}
}

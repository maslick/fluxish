package io.maslick.fluxish;

import io.maslick.fluxish.db.FeedRepo;
import io.maslick.fluxish.dto.Datus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
public class FluxishApplication {
	public static void main(String[] args) {
		SpringApplication.run(FluxishApplication.class, args);
	}

	private final FeedRepo repo;

	@PostConstruct
	public void post() {
		repo.saveAll(Arrays.asList(
				Datus.builder().feed("hello").timestamp(System.currentTimeMillis()).build(),
				Datus.builder().feed("world").timestamp(System.currentTimeMillis()).build()
		)).log().subscribe();
		repo.findAllByFeed("hello").subscribe(d -> {
			log.info("findAll - " + d);
		});
	}
}

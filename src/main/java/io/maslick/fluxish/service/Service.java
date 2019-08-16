package io.maslick.fluxish.service;

import io.maslick.fluxish.db.FeedRepo;
import io.maslick.fluxish.dto.Datus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class Service implements IService {

	private final FeedRepo repo;

	@Override
	public Mono<Datus> retrieve(Integer id) {
		log.info("Retrieving feed by id: {}", id);
		return repo.findById(id);
	}

	@Override
	public Flux<Datus> retrieve(String feed) {
		log.info("Retrieving feeds by name: {}", feed);
		return repo.findAllByFeed(feed);
	}

	@Override
	public Flux<Datus> retrieveAll() {
		log.info("Retrieving all feeds");
		return repo.findAll();
	}

	@Override
	public Mono<Datus> save(String feed) {
		log.info("Saving feed: {}", feed);
		return repo.save(new Datus(null, feed, System.currentTimeMillis()));
	}
}

package io.maslick.fluxish.db;

import io.maslick.fluxish.dto.Datus;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.r2dbc.repository.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface FeedRepo extends R2dbcRepository<Datus, Integer> {

	@Query("SELECT * FROM datus WHERE feed = $1")
	Flux<Datus> findAllByFeed(String feed);

}

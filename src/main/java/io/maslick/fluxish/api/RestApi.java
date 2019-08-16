package io.maslick.fluxish.api;

import io.maslick.fluxish.dto.Datus;
import io.maslick.fluxish.service.IService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(path = "/")
@RequiredArgsConstructor
public class RestApi {

	private final IService service;

	@GetMapping(path = "/id/{id}")
	public Publisher<Datus> getId(@PathVariable Integer id) {
		return service.retrieve(id);
	}

	@GetMapping(path = "/feed/{feed}")
	public Publisher<Datus> getFeed(@PathVariable String feed) {
		return service.retrieve(feed);
	}

	@GetMapping(path = "/feeds")
	public Publisher<Datus> getAllFeeds() {
		return service.retrieveAll();
	}

	@PostMapping(path = "/post")
	public Publisher<Datus> post(@RequestBody String feed) {
		return service.save(feed);
	}
}
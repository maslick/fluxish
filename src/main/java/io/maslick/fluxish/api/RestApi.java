package io.maslick.fluxish.api;

import io.maslick.fluxish.dto.Datus;
import io.maslick.fluxish.service.IService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_XML;
import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;
import static org.springframework.http.ResponseEntity.ok;

@Slf4j
@RestController
@RequestMapping(path = "/xml", produces = APPLICATION_XML_VALUE)
@RequiredArgsConstructor
public class RestApi {

	private final IService service;

	@GetMapping(path = "/get")
	public Publisher<ResponseEntity> get() {
		Datus data = new Datus("test");
		return Mono.just(ok().contentType(APPLICATION_XML).body(data));
	}

	@PostMapping(path = "/post", consumes = APPLICATION_XML_VALUE)
	public Publisher<ResponseEntity<Datus>> post(@RequestBody String feed) {
		service.run(feed);
		Datus data = new Datus("test");
		return Mono.just(ok().contentType(APPLICATION_XML).body(data));
	}
}
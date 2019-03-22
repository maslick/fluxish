package io.maslick.fluxish;

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

@RestController
@RequestMapping(path = "/xml", consumes = APPLICATION_XML_VALUE, produces = APPLICATION_XML_VALUE)
public class RestApi {
	@GetMapping(path = "/get")
	public Publisher<ResponseEntity> get() {
		return Mono.just(ResponseEntity.ok().contentType(APPLICATION_XML).body(new Datta("test")));
	}

	@PostMapping(path = "/post")
	public Publisher<ResponseEntity<Datta>> post(@RequestBody Datta datus) {
		datus.setTitle(datus.getTitle() + "!");
		return Mono.just(ResponseEntity.ok().contentType(APPLICATION_XML).body(datus));
	}
}
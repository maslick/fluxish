package io.maslick.fluxish;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/xml")
public class RestApi {

	private final FluService service;

	@GetMapping(path = "/get", produces = APPLICATION_XML_VALUE)
	public Datta get() {
		service.start();
		return new Datta("test");
	}

	@PostMapping(path = "/post", consumes = APPLICATION_XML_VALUE, produces = APPLICATION_XML_VALUE)
	public Datta post(@RequestBody Datta datus) {
		service.start();
		datus.setTitle(datus.getTitle() + "!");
		return datus;
	}
}
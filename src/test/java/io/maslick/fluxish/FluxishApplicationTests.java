package io.maslick.fluxish;

import io.maslick.fluxish.dto.Datus;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static java.util.Collections.singletonList;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_XML;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class FluxishApplicationTests {
	@LocalServerPort private int port;
	private TestRestTemplate restTemplate = new TestRestTemplate();

	@Test
	public void testGet() {
		String url = "http://localhost:" + port + "/xml/get";
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(singletonList(APPLICATION_XML));
		ResponseEntity<Datus> resp = restTemplate.exchange(url, GET, new HttpEntity<>(headers), Datus.class);

		Assert.assertEquals(APPLICATION_XML, resp.getHeaders().getContentType());
		Assert.assertEquals(OK, resp.getStatusCode());
		Assert.assertEquals("test", resp.getBody().getTitle());

	}

	@Test
	public void testPost() {
		Datus data = new Datus("test");
		String url = "http://localhost:" + port + "/xml/post";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(APPLICATION_XML);
		headers.setAccept(singletonList(APPLICATION_XML));
		HttpEntity<Datus> entity = new HttpEntity<>(data, headers);
		ResponseEntity<Datus> resp = restTemplate.exchange(url, POST, entity, Datus.class);

		Assert.assertEquals(APPLICATION_XML, resp.getHeaders().getContentType());
		Assert.assertEquals(OK, resp.getStatusCode());
		Assert.assertEquals("test", resp.getBody().getTitle());
	}
}

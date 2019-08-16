package io.maslick.fluxish;

import io.maslick.fluxish.db.FeedRepo;
import io.maslick.fluxish.dto.Datus;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class FluxishApplicationTests {
	@LocalServerPort private int port;
	private TestRestTemplate restTemplate = new TestRestTemplate();

	@Autowired private FeedRepo repo;

	@Before
	public void before() {
		repo.deleteAll().subscribe();
	}

	@Test
	public void test() {
		ResponseEntity<Datus> resp1 = add();
		Assert.assertEquals(APPLICATION_JSON, resp1.getHeaders().getContentType());
		Assert.assertEquals(OK, resp1.getStatusCode());

		String url = "http://localhost:" + port + "/feed/hello";
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<Datus[]> resp2 = restTemplate.exchange(url, GET, new HttpEntity<>(headers), Datus[].class);

		Assert.assertEquals(APPLICATION_JSON, resp2.getHeaders().getContentType());
		Assert.assertEquals(OK, resp2.getStatusCode());
		Assert.assertEquals("hello", resp2.getBody()[0].getFeed());
	}

	private ResponseEntity<Datus> add() {
		String data = "hello";
		String url = "http://localhost:" + port + "/post";
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<>(data, headers);
		return restTemplate.exchange(url, POST, entity, Datus.class);
	}
}

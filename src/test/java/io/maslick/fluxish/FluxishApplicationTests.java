package io.maslick.fluxish;

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
import static org.springframework.http.MediaType.parseMediaType;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class FluxishApplicationTests {
	@LocalServerPort private int port;
	private TestRestTemplate restTemplate = new TestRestTemplate();

	@Test
	public void testGet() {
		ResponseEntity<Datta> resp = sendGet();
		Assert.assertEquals(parseMediaType("application/xml;charset=UTF-8"), resp.getHeaders().getContentType());
		Assert.assertEquals(OK, resp.getStatusCode());
		Assert.assertEquals("test", resp.getBody().getTitle());
	}

	@Test
	public void testPost() {
		ResponseEntity<Datta> resp = sendPost();
		Assert.assertEquals(parseMediaType("application/xml;charset=UTF-8"), resp.getHeaders().getContentType());
		Assert.assertEquals(OK, resp.getStatusCode());
		Assert.assertEquals("test!", resp.getBody().getTitle());
	}

	@Test
	public void highLoadTestGet() throws InterruptedException {
		for (int i = 0; i < 100; i++) {
			ResponseEntity<Datta> resp = sendGet();
			Assert.assertEquals(parseMediaType("application/xml;charset=UTF-8"), resp.getHeaders().getContentType());
			Assert.assertEquals(OK, resp.getStatusCode());
			Assert.assertEquals("test", resp.getBody().getTitle());
		}
		Thread.sleep(10000);
	}

	@Test
	public void highLoadTestPost() throws InterruptedException {
		for (int i = 0; i < 100; i++) {
			ResponseEntity<Datta> resp = sendPost();
			Assert.assertEquals(parseMediaType("application/xml;charset=UTF-8"), resp.getHeaders().getContentType());
			Assert.assertEquals(OK, resp.getStatusCode());
			Assert.assertEquals("test!", resp.getBody().getTitle());
		}
		Thread.sleep(10000);
	}

	private ResponseEntity<Datta> sendGet() {
		String url = "http://localhost:" + port + "/xml/get";
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(singletonList(APPLICATION_XML));
		return restTemplate.exchange(url, GET, new HttpEntity<>(headers), Datta.class);
	}

	private ResponseEntity<Datta> sendPost() {
		Datta data = new Datta("test");
		String url = "http://localhost:" + port + "/xml/post";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(APPLICATION_XML);
		headers.setAccept(singletonList(APPLICATION_XML));
		HttpEntity<Datta> entity = new HttpEntity<>(data, headers);
		return restTemplate.exchange(url, POST, entity, Datta.class);
	}
}

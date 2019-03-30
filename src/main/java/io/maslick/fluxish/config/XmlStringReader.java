package io.maslick.fluxish.config;

import org.springframework.core.ResolvableType;
import org.springframework.http.MediaType;
import org.springframework.http.ReactiveHttpInputMessage;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Collections.singletonList;
import static org.springframework.core.io.buffer.DataBufferUtils.join;
import static org.springframework.http.MediaType.APPLICATION_XML;

@Component
public class XmlStringReader implements HttpMessageReader<String> {
	@Override
	public List<MediaType> getReadableMediaTypes() {
		return singletonList(APPLICATION_XML);
	}

	@Override
	public boolean canRead(ResolvableType elementType, MediaType mediaType) {
		return APPLICATION_XML.includes(mediaType) && ResolvableType.forClass(String.class).isAssignableFrom(elementType);
	}

	@Override
	public Flux<String> read(ResolvableType elementType, ReactiveHttpInputMessage message, Map<String, Object> hints) {
		throw new UnsupportedOperationException("Reading a Flux is not supported!");
	}

	@Override
	public Mono<String> readMono(ResolvableType elementType, ReactiveHttpInputMessage message, Map<String, Object> hints) {
		return join(message.getBody()).map(dataBuffer -> inputStreamToString(dataBuffer.asInputStream()));
	}

	private String inputStreamToString(InputStream inputStream) {
		try(ByteArrayOutputStream result = new ByteArrayOutputStream()) {
			byte[] buffer = new byte[1024];
			int length;
			while ((length = inputStream.read(buffer)) != -1) {
				result.write(buffer, 0, length);
			}

			return result.toString(UTF_8);
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}

package io.maslick.fluxish;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.reactivestreams.Publisher;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ResolvableType;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.http.codec.json.AbstractJackson2Decoder;
import org.springframework.http.codec.json.AbstractJackson2Encoder;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.lang.Nullable;
import org.springframework.util.MimeType;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Configuration
public class WebFluxConfig implements WebFluxConfigurer {

	private static final MimeType[] DEFAULT_XML_MIME_TYPES = new MimeType[]{new MimeType("application", "xml", StandardCharsets.UTF_8)};

	@Override
	public void configureHttpMessageCodecs(ServerCodecConfigurer configurer) {
		configurer.registerDefaults(false);
		ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.xml().build();
		configurer.customCodecs().encoder(new AbstractJackson2Encoder(objectMapper, DEFAULT_XML_MIME_TYPES) {});
		configurer.customCodecs().decoder(new AbstractJackson2Decoder(objectMapper, DEFAULT_XML_MIME_TYPES) {
			@Override
			public Mono<Object> decodeToMono(Publisher<DataBuffer> input, ResolvableType elementType,
											 @Nullable MimeType mimeType, @Nullable Map<String, Object> hints) {
				return DataBufferUtils.join(input).map(x -> {
					try {
						return objectMapper.readValue(x.asInputStream(), elementType.getRawClass());
					} catch (IOException e) {
						throw new IllegalArgumentException(e);
					}
				});
			}
		});
	}
}
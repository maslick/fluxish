package io.maslick.fluxish.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.http.codec.json.AbstractJackson2Encoder;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.util.MimeType;
import org.springframework.web.reactive.config.WebFluxConfigurer;

import java.nio.charset.StandardCharsets;

@Configuration
@RequiredArgsConstructor
public class WebFluxConfig implements WebFluxConfigurer {

	private static final MimeType[] DEFAULT_XML_MIME_TYPES = new MimeType[]{new MimeType("application", "xml", StandardCharsets.UTF_8)};
	private final XmlStringReader stringReader;

	@Override
	public void configureHttpMessageCodecs(ServerCodecConfigurer configurer) {
		configurer.registerDefaults(false);
		ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.xml().build();
		configurer.customCodecs().reader(stringReader);
		configurer.customCodecs().encoder(new AbstractJackson2Encoder(objectMapper, DEFAULT_XML_MIME_TYPES) {});
	}
}
package io.maslick.fluxish.db;


import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.function.DatabaseClient;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;

@Configuration
@EnableR2dbcRepositories
class DbConfig extends AbstractR2dbcConfiguration {

	@Value("${spring.data.postgres.host}") String host;
	@Value("${spring.data.postgres.port}") Integer port;
	@Value("${spring.data.postgres.database}") String database;
	@Value("${spring.data.postgres.username}") String username;
	@Value("${spring.data.postgres.password}") String password;

	@Bean
	@Override
	public ConnectionFactory connectionFactory() {
		return new PostgresqlConnectionFactory(
				PostgresqlConnectionConfiguration.builder()
						.host(host)
						.port(port)
						.database(database)
						.username(username)
						.password(password)
						.build()
		);
	}

	@Bean
	public DatabaseClient client(ConnectionFactory factory) {
		return DatabaseClient.builder().connectionFactory(factory).build();
	}
}

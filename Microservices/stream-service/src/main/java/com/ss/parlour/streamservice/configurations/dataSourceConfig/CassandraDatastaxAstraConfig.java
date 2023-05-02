package com.ss.parlour.streamservice.configurations.dataSourceConfig;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.autoconfigure.cassandra.CqlSessionBuilderCustomizer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.net.MalformedURLException;
import java.net.URL;


@ConfigurationProperties(prefix = "datastax.astra")
@Getter
@Setter
@NoArgsConstructor
@Profile("astra")
public class CassandraDatastaxAstraConfig {

    private String secureConnectBundle;

    @Bean
    public CqlSessionBuilderCustomizer sessionBuilderCustomizer(CassandraDatastaxAstraConfig cassandraDatastaxAstraConfig) throws MalformedURLException {
        String bundle = cassandraDatastaxAstraConfig.getSecureConnectBundle();
        URL bundleUrl = new URL(bundle);
        return cqlSessionBuilder -> cqlSessionBuilder.withCloudSecureConnectBundle(bundleUrl);
    }

}

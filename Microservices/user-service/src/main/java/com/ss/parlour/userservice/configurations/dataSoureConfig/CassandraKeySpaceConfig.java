package com.ss.parlour.userservice.configurations.dataSoureConfig;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.config.DriverConfigLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import software.aws.mcs.auth.SigV4AuthProvider;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.security.NoSuchAlgorithmException;

@Configuration
@Profile("keyspace")
public class CassandraKeySpaceConfig {
    File driverConfig = new File(System.getProperty("user.dir")+"/Microservices/user-service/application.conf");

    @Primary
    public @Bean
    CqlSession session() throws NoSuchAlgorithmException {
        SigV4AuthProvider provider = new SigV4AuthProvider("us-east-1");
        return CqlSession.builder().
                withConfigLoader(DriverConfigLoader.fromFile(driverConfig)).
                withAuthProvider(provider).
                withSslContext(SSLContext.getDefault()).
                withKeyspace("parlour").
                build();
    }
}

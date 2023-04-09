package com.ss.parlour.userservice.configurations.dataSoureConfig;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.config.DriverConfigLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.security.NoSuchAlgorithmException;

//@Configuration
public class CassandraKeySpaceConfig {

    private final String username = "ishara.d.wijesinghe@gmail.com-at-317082675872";
    private final String password = "BUvnF0tjIJiLkPLrNFKTI60FoGDZFnvePD4M+rWG2BY=";
    File driverConfig = new File(System.getProperty("user.dir")+"/Microservices/user-service/application.conf");

    @Primary
    public @Bean
    CqlSession session() throws NoSuchAlgorithmException {
        return CqlSession.builder().
                withConfigLoader(DriverConfigLoader.fromFile(driverConfig)).
                withAuthCredentials(username, password).
                withSslContext(SSLContext.getDefault()).
                withKeyspace("parlour").
                build();
    }
}

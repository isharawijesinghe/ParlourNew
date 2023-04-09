package com.ss.parlour.articleservice.configurations;

import com.datastax.driver.core.AuthProvider;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.config.DriverConfigLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import software.aws.mcs.auth.SigV4AuthProvider;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.security.NoSuchAlgorithmException;

//@Configuration
public class CassandraKeySpaceConfig {

    private final String username = "ishara.d.wijesinghe@gmail.com-at-317082675872";
    private final String password = "BUvnF0tjIJiLkPLrNFKTI60FoGDZFnvePD4M+rWG2BY=";
    File driverConfig = new File(System.getProperty("user.dir")+"/Microservices/article-service/application.conf");

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

//        return Cluster.builder()
//                .addContactPoint("cassandra.us-east-1.amazonaws.com")
//                .withPort(9142)
//                .withAuthProvider((AuthProvider) provider)
//                .withSSL()
//                .build()
//                .connect();

//        DriverConfigLoader loader = DriverConfigLoader.fromFile(driverConfig);
//        return CqlSession.builder()
//                .withConfigLoader(loader)
//                .withKeyspace("parlour")
//                . withSslContext(SSLContext.getDefault()).
//                .build();
    }
}

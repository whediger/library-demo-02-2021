package com.example.library3;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.net.URI;
import java.net.URISyntaxException;

@Configuration
public class DatabaseConfig {

    @Bean
    public DataSource dataSource() throws Exception {
        URI dbUri = null;
        try {
            System.out.println("System.getenv(\"DATABASE_URL\") = <" + System.getenv("DATABASE_URL") + ">");
            dbUri = new URI(System.getenv("DATABASE_URL"));
        } catch (URISyntaxException e) {
            e.printStackTrace();
            System.err.println("System.getenv(\"DATABASE_URL\") = <" + System.getenv("DATABASE_URL") + ">");
            System.out.println("System.getenv(\"DATABASE_URL\") = <" + System.getenv("DATABASE_URL") + ">");
            throw new Exception("DB URL missing");
        }

        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];
        String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();

        return DataSourceBuilder.create()
                .url(dbUrl)
                .username(username)
                .password(password)
                .build();
    }
}

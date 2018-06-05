package com.yassuda.intelipost;

import org.h2.tools.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.sql.SQLException;

@SpringBootApplication
public class JobBackendDeveloperApplication {

    public static void main(String[] args) {
        SpringApplication.run(JobBackendDeveloperApplication.class, args);
    }

    /**
     * Instância de um Server de H2 para conexão remota.
     *
     * @return H2 Server instance
     * @throws SQLException
     */
    // First App
    @Bean(initMethod = "start", destroyMethod = "stop")
    public Server h2Server() throws SQLException {
        return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9092");
    }
}

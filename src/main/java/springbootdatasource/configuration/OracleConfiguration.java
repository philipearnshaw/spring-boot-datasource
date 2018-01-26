package springbootdatasource.configuration;

import java.sql.SQLException;

import javax.sql.DataSource;
import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import lombok.Setter;
import oracle.jdbc.pool.OracleDataSource;

@Setter
@Profile("oracle")
@Configuration
@ConfigurationProperties("oracle")
public class OracleConfiguration {

    @NotNull
    private String username;
    
    @NotNull
    private String password;
    
    @NotNull
    private String url;
	
    @Bean
    DataSource dataSource() throws SQLException {
        
        final OracleDataSource dataSource = new OracleDataSource();
        dataSource.setDriverType("thin");
        dataSource.setUser(username);
        dataSource.setPassword(password);
        dataSource.setURL(url);
        dataSource.setImplicitCachingEnabled(true);
        dataSource.setFastConnectionFailoverEnabled(true);

        return dataSource;
    }

}

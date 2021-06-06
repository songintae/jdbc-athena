package example.athena;

import example.athena.implement.AthenaClientProperties;
import example.athena.implement.AthenaQueryExecutor;
import example.athena.implement.AthenaQueryExecutorImpl;
import example.athena.implement.basic.DefaultAthenaJdbcTemplate;
import example.athena.implement.expended.ExtendedAthenaJdbcTemplate;
import lombok.RequiredArgsConstructor;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@ComponentScan
@Configuration
@RequiredArgsConstructor
public class AthenaClientConfiguration {
    private final AthenaClientProperties athenaClientProperties;

    /**
     * 단순한 java.sql 스펙을 따라 만든 JdbcTemplate
     * @return
     */
    @Bean
    public AthenaQueryExecutor defaultAthenaQueryExecutor() {
        return new AthenaQueryExecutorImpl(defaultAthenaJdbcTemplate());
    }

    /**
     * Connection Pool 및 org.springframework.jdbc을 통해 확장한 AthenaJdbcTemplate
     * @return
     */
    @Bean
    public AthenaQueryExecutor extendedAthenaQueryExecutor() {
        return new AthenaQueryExecutorImpl(extendedAthenaJdbcTemplate());
    }

    /**
     * 스프링에서 제공하는 JdbcTemplate 이용하기
     * @return
     */
    @Bean
    public JdbcTemplate athenaJdbcTemplate() {
        return new JdbcTemplate(athenaDataSource());
    }

    private ExtendedAthenaJdbcTemplate extendedAthenaJdbcTemplate() {

        return new ExtendedAthenaJdbcTemplate(athenaDataSource());
    }

    private DefaultAthenaJdbcTemplate defaultAthenaJdbcTemplate() {
        return new DefaultAthenaJdbcTemplate(athenaClientProperties);
    }

    private DataSource athenaDataSource() {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName("com.simba.athena.jdbc.Driver");

        //set connection Property
        basicDataSource.setUrl(athenaClientProperties.getUrl());
        basicDataSource.addConnectionProperty("AwsCredentialsProviderClass", athenaClientProperties.getAwsCredentialsProviderClass());
        basicDataSource.addConnectionProperty("S3OutputLocation", athenaClientProperties.getOutputLocation());
        basicDataSource.addConnectionProperty("Schema", athenaClientProperties.getSchema());
        basicDataSource.addConnectionProperty("SocketTimeout", athenaClientProperties.getSocketTimeout());

        //set Connection Pool Property
        basicDataSource.setMaxTotal(100);
        basicDataSource.setMaxIdle(100);
        basicDataSource.setMinIdle(10);

        return basicDataSource;
    }
}


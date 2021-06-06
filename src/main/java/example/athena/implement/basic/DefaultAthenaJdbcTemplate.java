package example.athena.implement.basic;

import example.athena.implement.AthenaClientProperties;
import example.athena.implement.AthenaJdbcTemplate;
import example.athena.implement.PreparedStatementCallback;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;

@Slf4j
@RequiredArgsConstructor
public class DefaultAthenaJdbcTemplate implements AthenaJdbcTemplate {
    private final AthenaClientProperties athenaClientProperties;

    @Override
    public <T> T execute(String sql, PreparedStatementCallback<T> action) {
        if(Objects.isNull(sql) || sql.isEmpty()) {
            throw new AthenaQueryException("SQL 문은 필수 항목입니다.");
        }

        try {
            Class.forName("com.simba.athena.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        Properties properties = new Properties();
        properties.setProperty("AwsCredentialsProviderClass", athenaClientProperties.getAwsCredentialsProviderClass());
        properties.setProperty("S3OutputLocation", athenaClientProperties.getOutputLocation());
        properties.setProperty("Schema", athenaClientProperties.getSchema());
        properties.setProperty("SocketTimeout", athenaClientProperties.getSocketTimeout());

        try (Connection con = DriverManager.getConnection(athenaClientProperties.getUrl(), properties);
             PreparedStatement ps = con.prepareStatement(sql)) {
            return action.doInPreparedStatement(ps);
        } catch (SQLException e) {
            log.error("AthenaQueryExecutor SQLException message : {}", e.getMessage());
            throw new AthenaQueryException("데이터 조회 중에 오류가 발생했습니다.");
        }
    }
}
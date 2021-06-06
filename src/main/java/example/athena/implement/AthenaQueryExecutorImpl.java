package example.athena.implement;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
@RequiredArgsConstructor
public class AthenaQueryExecutorImpl implements AthenaQueryExecutor {
    private final AthenaJdbcTemplate athenaJdbcTemplate;

    @Override
    public <T> T execute(String sql, AthenaQueryMapper<T> mapper) {
        return athenaJdbcTemplate.execute(sql, ps -> extractResult(ps, mapper));
    }

    @Override
    public int executeUpdate(String sql) {
        return athenaJdbcTemplate.execute(sql, PreparedStatement::executeUpdate);
    }

    private <T> T extractResult(PreparedStatement ps, AthenaQueryMapper<T> mapper) throws SQLException {
        try (ResultSet rs = ps.executeQuery()) {
            return mapper.map(rs);
        }
    }
}

package example.athena.implement;

public interface AthenaJdbcTemplate {
    <T> T execute(String sql, PreparedStatementCallback<T> action);
}

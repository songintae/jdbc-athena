package example.athena.implement;

public interface AthenaQueryExecutor {
    /**
     * 데이터를 select 하기위한 용도. AthenaQueryMapper를 구현하여 ResultSet을 이용해 원하는 형식으로 데이터를 반환
     */
    <T> T execute(String sql, AthenaQueryMapper<T> mapper);

    /**
     * INSERT, UPDATE, DELETE를 위한 용도. SQL을 실행하면 영향을 받은 row수를 반환.
     */
    int executeUpdate(String sql);
}

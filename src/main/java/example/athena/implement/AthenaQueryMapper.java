package example.athena.implement;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface AthenaQueryMapper<T> {
    T map(ResultSet resultSet) throws SQLException;
}

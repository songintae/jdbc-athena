package example.athena.implement;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface PreparedStatementCallback<T> {
    T doInPreparedStatement(PreparedStatement ps) throws SQLException;
}

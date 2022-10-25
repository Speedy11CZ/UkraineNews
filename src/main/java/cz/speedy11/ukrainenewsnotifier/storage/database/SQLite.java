package cz.speedy11.ukrainenewsnotifier.storage.database;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLite {

    private static final Logger LOGGER = LogManager.getLogger(SQLite.class);

    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException exception) {
            throw new RuntimeException(exception);
        }
    }

    private final File file;

    public SQLite(File file) {
        this.file = file;
    }

    public List<Row> query(String query, Object... variables) {
        List<Row> rows = new ArrayList<>();
        Connection connection = null;
        ResultSet result = null;
        PreparedStatement preparedStatement;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + file.getAbsolutePath());
            preparedStatement = getPreparedStatement(connection.prepareStatement(query), variables);
            if (preparedStatement.execute()) {
                result = preparedStatement.getResultSet();
            }

            if (result != null) {
                ResultSetMetaData resultSetMetaData = result.getMetaData();
                int columnCount = resultSetMetaData.getColumnCount();
                while (result.next()) {
                    Row row = new Row();
                    for (int i = 0 ; i != columnCount ; i++) {
                        String columnName = resultSetMetaData.getColumnName(i + 1);
                        row.addCell(columnName, result.getObject(columnName));
                    }
                    rows.add(row);
                }
            }
        } catch (Exception exception) {
            LOGGER.error("Error while executing query: " + query, exception);
        } finally {
            try {
                if(connection != null) {
                    connection.close();
                }
            } catch (SQLException exception) {
                LOGGER.error("Error while closing connection", exception);
            }
        }
        return rows;
    }

    @Contract("_, _ -> param1")
    private PreparedStatement getPreparedStatement(PreparedStatement preparedStatement, Object @NotNull ... variables) throws SQLException {
        for (int i = 1 ; i <= variables.length ; i++) {
            Object variable = variables[i - 1];
            if(variable instanceof Blob blob) {
                preparedStatement.setBlob(i, blob);
            } else if (variable instanceof InputStream inputStream) {
                preparedStatement.setBlob(i, inputStream);
            } else if (variable instanceof byte[] bytes) {
                preparedStatement.setBytes(i, bytes);
            } else if(variable instanceof Boolean bool) {
                preparedStatement.setBoolean(i, bool);
            } else if(variable instanceof Integer) {
                preparedStatement.setInt(i, (Integer) variable);
            } else if(variable instanceof Long) {
                preparedStatement.setLong(i, (Long) variable);
            } else if(variable instanceof Double) {
                preparedStatement.setDouble(i, (Double) variable);
            } else if(variable instanceof Float) {
                preparedStatement.setFloat(i, (Float) variable);
            } else if(variable instanceof String) {
                preparedStatement.setString(i, (String) variable);
            } else {
                preparedStatement.setObject(i, variable);
            }
        }
        return preparedStatement;
    }
}

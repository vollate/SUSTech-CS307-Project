import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;

public class DB {
    public static boolean batchFlag = false;

    public static int BATCH_SIZE = 5;
    public static Connection con = null;

    public static Properties loadDBUser() {
        Properties properties = new Properties();
        try {
            properties.load(new InputStreamReader(new FileInputStream("data/dbUser1.properties")));
            return properties;
        } catch (IOException e) {
            System.err.println("can not find db user file");
            throw new RuntimeException(e);
        }
    }

    public static void openDB(Properties prop) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (Exception e) {
            System.err.println("Cannot find the Postgres driver. Check CLASSPATH.");
            System.exit(1);
        }
        String url = "jdbc:postgresql://" + prop.getProperty("host") + "/" + prop.getProperty("database");
        try {
            con = DriverManager.getConnection(url, prop);
            if (con != null) {
                System.out.println("Successfully connected to the database "
                        + prop.getProperty("database") + " as " + prop.getProperty("user"));
                if(batchFlag) con.setAutoCommit(false);
            }
        } catch (SQLException e) {
            System.err.println("Database connection failed");
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    public static void genTable() {
        String filePath = "SQL/gen-table.sql";
        exeSqlFile(filePath);
    }
    public static void sampleInsert() {
        String filePath = "SQL/sample.sql";
        exeSqlFile(filePath);
    }
    public static void dropAll() {
        String filePath = "SQL/drop-all.sql";
        exeSqlFile(filePath);
    }
    public static void exeSqlFile(String filePath){
        try {
            String sqlFileContent = new String(Files.readAllBytes(Paths.get(filePath)));
            List<String> statements = Arrays.asList(sqlFileContent.split(";"));

            for (String statement : statements) {
                if (!statement.trim().isEmpty()) {
                    //System.out.println("Executing statement: " + statement);
                    try (Statement stmt = con.createStatement()) {
                        stmt.execute(statement);
                    }
                }
            }
            //con.commit();

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
    public static void closeDB() {
        if (con != null) {
            try {
                if (InsertReply.stmt != null) {
                    InsertReply.stmt.close();
                }
                if (InsertPost.stmt != null) {
                    InsertPost.stmt.close();
                }
                con.close();
                con = null;
            } catch (Exception ignored) {
            }
        }
    }
}

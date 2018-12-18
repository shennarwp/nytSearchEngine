package dao;

import de.htwsaar.nytSearchEngine.model.Document;
import de.htwsaar.nytSearchEngine.util.Parser;

import java.io.File;
import java.sql.*;

public class DAOImpl {
    private static String url = "jdbc:sqlite:src/main/resources/nyt.sqlite";
    private final int batchSize = 10;
    private int count = 0;

    //FOR TABLE docs
    private static String docs= "CREATE TABLE docs (\n" + "    did   INTEGER PRIMARY KEY\n"
                + "                  UNIQUE\n"
                + "                  NOT NULL,\n"
                + "    title TEXT    NOT NULL,\n"
                + "    url   TEXT    NOT NULL\n" + ")";

    //FOR TABLE tfs
    private static String tfs= "CREATE TABLE tfs (\n" + "    did  INTEGER REFERENCES docs (did) ON DELETE CASCADE\n"
                + "                                       ON UPDATE CASCADE\n"
                + "                 NOT NULL,\n"
                + "    term TEXT    NOT NULL,\n"
                + "    tf   INTEGER NOT NULL\n"
                + "                 CHECK (tf >= 0)\n"
                + "                 DEFAULT (0)\n" + ")";

    //FOR TABLE dls, len is total number of term occurrences within the document
    private static String dls= "CREATE TABLE dls AS\n"
                + "SELECT DISTINCT t.did AS did,\n"
                + "\t   (SELECT COUNT(a.term) FROM tfs a WHERE a.did = t.did) AS len\n"
                + "FROM tfs t";

    //FOR TABLE dfs, df is document frequency of particular term in the collection
    private static String dfs= "CREATE TABLE dfs AS\n"
                + "SELECT DISTINCT t.term AS term,\n"
                + "\t   (SELECT COUNT(a.did) FROM tfs a WHERE a.term = t.term) AS df\n"
                + "FROM tfs t";

    //FOR TABLE d, size is the total number of documents in the collection
    private static String d="CREATE TABLE d AS\n"
                + "SELECT COUNT(DISTINCT t.did) AS size\n"
                + "FROM tfs t;";


    /**
     * note: for some(?) reasons the data inside these 3 tables does not automatically updates
     * 	  whenever new data is updated / inserted in the parent table tfs or docs (e.g. adding new term / new docs id)
     * 	  the only way to update these 3 tables is by deleting it and then execute the query CREATE TABLE ... again
     *
     * //same as 3 queries above, but one line for copy paste
     * CREATE TABLE dls AS SELECT DISTINCT t.did AS did, (SELECT COUNT(a.term) FROM tfs a WHERE a.did = t.did) AS len FROM tfs t;
     * CREATE TABLE dfs AS SELECT DISTINCT t.term AS term, (SELECT COUNT(a.did) FROM tfs a WHERE a.term = t.term) AS df FROM tfs t;
     * CREATE TABLE d AS SELECT COUNT(DISTINCT t.did) AS size FROM tfs t;
     */

    /**
     * create table in database
     */
    public static void createTable(){
        try {
            Connection connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();
            statement.execute(docs); //docs table
            statement.execute(tfs);  //tfs table
            statement.execute(dls);  //dls table
            statement.execute(dfs);  //dfs table
            statement.execute(d);    //d table
            statement.setQueryTimeout(30);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * Connect to database
     * @return the connection objects
     */
    private Connection connect() {
        // SQLite connection string
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    //insert method to docs table
    public void insertIntoDocs(long id, String title, String url) {
        String sql = "INSERT INTO docs(did,title,url) VALUES(?,?,?)";

        try (Connection conn = this.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong( 1, id);
            ps.setString(2, title);
            ps.setString(3, url);
            ps.executeUpdate();
            ps.addBatch();

            if(++count % batchSize == 0) {
                ps.executeBatch();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //insert method to tfs table
    //TODO :not done yet, should find term and tf
    public void insertIntoTfs(long id, String term, int tf){
        String sql = "INSERT INTO tfs(did, term, tf)VALUES(?,?,?)";

        try (Connection conn = this.connect();
             PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setLong(1, id);
            ps.setString(2, term);
            ps.setInt(3, tf);
            ps.executeUpdate();
            ps.addBatch();

            if(++count % batchSize == 0) {
                ps.executeBatch();
            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}

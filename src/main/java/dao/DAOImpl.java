package dao;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

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


    //
    public TreeMap<Integer,Integer> getDidByTerm(String term){
        TreeMap<Integer,Integer> treeMap = new TreeMap<>();
        ResultSet resultSet = null;
        String sql = "SELECT did, tf FROM tfs WHERE term=? ;";

        try (Connection conn = this.connect();
             PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, term);
            resultSet = ps.executeQuery();
            while (resultSet.next()){
                Integer did = resultSet.getInt("did");
                Integer tf = resultSet.getInt("tf");
                treeMap.put(did,tf);


            }

        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return treeMap;
    }


    public int getDF(String term){
        String sql = "SELECT df  FROM dfs WHERE term=? ;";
        ResultSet resultSet = null;
       Integer df = 0;

        try (Connection conn = this.connect();
             PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, term);
            resultSet = ps.executeQuery();



            while (resultSet.next()){
                String dfAsString = resultSet.getString("df");
                df = Integer.parseInt(dfAsString);

            }


        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return df;
    }


    public int getSize(){
        String sql = "SELECT COUNT(*) FROM docs;";
        ResultSet resultSet = null;
        Integer size = 0;

        try (Connection conn = this.connect();
             PreparedStatement ps = conn.prepareStatement(sql)){
            resultSet = ps.executeQuery();



            while (resultSet.next()){
                String dfAsString = resultSet.getString("COUNT(*)");
                size = Integer.parseInt(dfAsString);

            }


        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return size;
    }

    public int getLength(long did){
        String sql = "SELECT len FROM dls WHERE did=? ;";
        ResultSet resultSet = null;
        Integer size = 0;

        try (Connection conn = this.connect();
             PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setLong(1, did);
            resultSet = ps.executeQuery();



            while (resultSet.next()){
                String dfAsString = resultSet.getString("len");
                size = Integer.parseInt(dfAsString);

            }


        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return size;
    }




}

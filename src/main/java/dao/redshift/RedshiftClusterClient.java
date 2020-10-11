package dao.redshift;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class RedshiftClusterClient {
    static final String dbURL = "jdbc:redshift://redshift-cluster-1.cjvwpmimvlbm.us-east-1.redshift.amazonaws.com:5439/dev";
    static final String MasterUsername = "user";
    static final String MasterUserPassword = "password";

    public static void testConnection() {
        Connection conn = null;
        Statement stmt = null;
        try{
            Class.forName("com.amazon.redshift.jdbc.Driver");
            System.out.println("Connecting to database...");
            Properties props = new Properties();
            // props.setProperty("ssl", "true");
            props.setProperty("user", MasterUsername);
            props.setProperty("password", MasterUserPassword);
            conn = DriverManager.getConnection(dbURL, props);

            System.out.println("Listing system tables...");
            stmt = conn.createStatement();
            String sql;
            sql = "select * from information_schema.tables;";
            ResultSet rs = stmt.executeQuery(sql);

            //Get the data from the result set.
            while(rs.next()){
                // Retrieve two columns.
                String catalog = rs.getString("table_catalog");
                String name = rs.getString("table_name");

                // Display values.
                System.out.print("Catalog: " + catalog);
                System.out.println(", Name: " + name);
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch(Exception ex) {
            //For convenience, handle all errors here.
            ex.printStackTrace();
        } finally {
            //Finally block to close resources.
            try {
                if(stmt!=null)
                    stmt.close();
            } catch(Exception ex) {
            }
            try {
                if(conn!=null)
                    conn.close();
            } catch(Exception ex){
                ex.printStackTrace();
            }
        }
        System.out.println("Finished connectivity test.");
    }
}

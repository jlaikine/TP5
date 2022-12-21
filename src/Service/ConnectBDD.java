package Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import entite.Post;

public class ConnectBDD {
    private String URL = "jdbc:derby:cciDB;create=true";
    private String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    private String LOGIN = "";
    private String PWD = "";
    
    private Connection cn;
    public void ConnectionBDD() {
        try {
            Class.forName(DRIVER);
            cn = DriverManager.getConnection(URL, LOGIN, PWD);
            System.out.println( "Connection a la base de donnees");
            
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e. printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e. printStackTrace();
        }
    }
    
    public void ajoutBDD() throws SQLException {
    	Statement st = cn.createStatement();
        st.execute("create table eleve (id int, title varchar(20), body varchar(20) )") ;
        st.executeUpdate("INSERT INTO eleve VALUES (1, 'DURAND' , ' Jacques')");
        st.executeUpdate("INSERT INTO eleve VALUES (2, 'DUPOND' , 'Daniel' )");
    }
    
    public ArrayList<Post> affichage() throws SQLException {
    	Statement st = cn.createStatement();
    	ResultSet rs = st.executeQuery("SELECT * FROM eleve");
    	ArrayList<Post> ar = new ArrayList<>();
    	
    	while (rs.next()) {
    		Post p = new Post(rs.getInt("id"), rs.getString("title"), rs.getString("body"));
    		ar.add(p);
    	}

    	return ar;
    }    
    
    public static ArrayList<Post> getPost() throws IOException {
        URL url = new URL("https://jsonplaceholder.typicode.com/posts");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        ArrayList<Post> ar = new ArrayList<Post>();
        ArrayList<String> l = new ArrayList<String>();
        con.setConnectTimeout(20000);
        con.setReadTimeout(20000);
        con.setUseCaches(true);
        con.setRequestMethod("GET");
        
        con.setDefaultRequestProperty("Accept", "application/xml");
        con.setDefaultRequestProperty("Content-Type", "application/xml");
        
        int responseCode = con.getResponseCode();
        if (responseCode == 400) {
        	System.out.println("Client Error !!");
        } else if (responseCode == 500) {
        	System.out.println("Server Error !!");
        } else if (responseCode == 200) {
        	BufferedReader in = new BufferedReader (new InputStreamReader(con.getInputStream())); 
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
            	if (inputLine.contains(":")) {
            		String mots[] = inputLine.split(":");
            		l.add(mots[1]);
            		
            		if (l.size() == 4) {
            			String s = l.get(1);
            			String s2[] = s.split(",");
            			String s3[] = s2[0].split(" ");
            			
            			Post p = new Post(Integer.parseInt(s3[1]), l.get(2), l.get(3));

            			ar.add(p);
            			l.clear();
            		}
            	}
            }
            in.close();
        }
        return ar;
    }
}
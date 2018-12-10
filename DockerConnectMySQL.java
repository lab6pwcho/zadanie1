import java.sql.*;

public class DockerConnectMySQL {
   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
   static final String DB_URL = "jdbc:mysql://10.0.10.3:3306/baza";

   static final String USER = "pwozniacka";
   static final String PASS = "rootpassword";
   
   public static void main(String[] args) throws InterruptedException{
   Connection conn = null;
   Statement stmt = null;
   Boolean baseExist = false;
   Boolean login = true;
   String sql;
   
    while(login) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");            
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            login = false; 
        }catch(SQLException se){
    Thread.sleep(10000);
        }catch(Exception e){
    Thread.sleep(10000);
        }finally{
    System.out.println("Connecting to database...");
        }
    }
    
    try{        
      System.out.println("Check if table in base exist");
        DatabaseMetaData md = conn.getMetaData();
        ResultSet result = md.getTables(null, null, "Tab", null);
        while (result.next()) {
            System.out.println("Table Exist");
            baseExist = true;
        }
        result = null;  
        if(!baseExist){
          System.out.println("Creating Table");
          stmt = conn.createStatement();
          sql = "CREATE TABLE Tab(id int, kolumna1 varchar(255), kolumna2 varchar(255), kolumna3 varchar(255))";
          stmt.executeUpdate(sql);
          stmt = null;
        }     
        stmt = conn.createStatement();
        System.out.println("Inserting Data to Table");
        sql = "INSERT INTO Tab (id, kolumna1, kolumna2, kolumna3) VALUES (1, 'wartosc1', 'wartosc2', 'wartosc3'), (2, 'wartosc4', 'wartosc5', 'wartosc6'), (3, 'wartosc7', 'wartosc8', 'wartosc9')";
        stmt.executeUpdate(sql);   
        stmt = null;
     
        stmt = conn.createStatement();
        sql = "SELECT id, kolumna1, kolumna2, kolumna3 FROM Tab";
        result = stmt.executeQuery(sql);

      while(result.next()){
	int id  = result.getInt("id");
	String k1 = result.getString("kolumna1");
	String k2 = result.getString("kolumna2");
	String k3 = result.getString("kolumna3");
    
        System.out.println("ID: " + id);
	System.out.println(" Kolumna1: " + k1);
        System.out.println(" Kolumna2: " + k2);
        System.out.println(" Kolumna3: " + k3);
    
        }
        
        result.close();
        stmt.close();
        conn.close();
      
    }catch(SQLException se){
        se.printStackTrace();
    }catch(Exception e){
      e.printStackTrace();
    }finally{
    try{
          if(stmt!=null) stmt.close();
        }catch(SQLException se2){
        
        }
        try{
        if(conn!=null) conn.close();
        }catch(SQLException se){
        se.printStackTrace();
        }
    }
  }
}

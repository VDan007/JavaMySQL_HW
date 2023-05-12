import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



public class App {
    public static void main(String[] args) throws Exception {

        String sqlCheckForDatabase = "SELECT SCHEMA_NAME FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME = 'oscar'";
        String sqlCheckForTable = "SELECT TABLE_NAME FROM information_schema.tables  WHERE table_schema = 'oscar' AND table_name = 'filmek'" ;
        String sqlCreateDataBase = "CREATE DATABASE oscar CHARACTER SET utf8 COLLATE utf8_hungarian_ci";
        String sqlCreateTable = "CREATE TABLE oscar.filmek(azon VARCHAR(15) PRIMARY KEY, cim VARCHAR(255), ev INT, dij INT, jelol INT)";

        try{
          

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306","root","rootpassword");
           
            PreparedStatement ps = connection.prepareStatement(sqlCheckForDatabase);
            PreparedStatement cd = connection.prepareStatement(sqlCreateDataBase);
            PreparedStatement tableCheckStatement = connection.prepareStatement(sqlCheckForTable);
            PreparedStatement tableCreateStatement = connection.prepareStatement(sqlCreateTable);

            ResultSet rs = ps.executeQuery();
          
            
            if(rs.next()){
               
                System.out.println(" \nDatabase exists");
                ResultSet tableCheck = tableCheckStatement.executeQuery();
              //  System.out.println(tableCheck.next());
                
                if(!tableCheck.next()){
                    System.out.println("Table doesn't exists");
                    tableCreateStatement.execute();
                    System.out.println("Table created\n");
                    
                }else{
                    System.out.println("Table exists\n");
                }

            }else{
                System.out.println(" \nDatabase doesn't exist");
                cd.execute();
                System.out.println("Database created\n");
                tableCreateStatement.execute();
                System.out.println("Table created\n");
            }

          

        }catch(Exception e){
            System.out.println(e);
        }
    }
}

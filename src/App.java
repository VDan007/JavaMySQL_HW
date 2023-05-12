import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;





public class App {



    public static void main(String[] args) throws Exception {

        Db_Table_CheckerAndCreator();
        FilmekReader();

       
    }




    private static void Db_Table_CheckerAndCreator(){
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




    private static void FilmekReader(){

       
        
        try{
            File fileToRead = new File("filmek.txt");
            Scanner scanner = new Scanner(fileToRead);
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306","root","rootpassword");
            scanner.nextLine();
            while(scanner.hasNextLine()){
                String[] LineData = scanner.nextLine().split(";");
                String sqlInsertIntoDb = "insert into oscar.filmek values (" + LineData[0] +","+  LineData[1] +","+ LineData[2] +","+ LineData[3]+","+ LineData[4] + ");" ;
                System.out.println(sqlInsertIntoDb);
                PreparedStatement insertStmnt = connection.prepareStatement(sqlInsertIntoDb);
                insertStmnt.execute();
                System.out.println("line inserted");

            }
            scanner.close();

        }catch(Exception e){
            System.out.println(e);
        }

    }
}

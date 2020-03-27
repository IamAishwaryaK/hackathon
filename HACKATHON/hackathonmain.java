import java.io.*;
import org.json.simple.*;
import java.sql.*;

public class hackathonmain
{
	
	public static void readfromfile()
	{
	  
		try
		{
	        long sum=0;
	        double avg,max=0;
	        long count=0;
                long value=0;
		JSONObject json=new JSONObject();
	        JSONObject memory = new JSONObject();
	        JSONObject values = new JSONObject();
		//Read from file
                String fileName = "C:\\Users\\Aishwarya\\Documents\\memory.txt";
		File file = new File(fileName);
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		String[] memoryvalues;
		String line;
		
		
		while((line = br.readLine()) != null)
		{
			 memoryvalues=line.split("   ");
			 if(Long.parseLong(memoryvalues[1])!=value)//Remove the repeated value
			 {
			       count++;//count increased when if condition is passed
			       sum= sum+Long.parseLong(memoryvalues[1]);
			       System.out.println(memoryvalues[1]);
			       values.put(count+"s",memoryvalues[1]);//print values to json file
			       value=Long.parseLong(memoryvalues[1]);
			       
			 }
			 
			 if(max < (Long.parseLong(memoryvalues[1])))
			 {
				 max=Long.parseLong(memoryvalues[1]);
			 }
			
			   
		}
		 
		avg=sum/count;
		max=(max/1024);//to MB
		max=(double)Math.round(max*100)/100;//limit to two decimal places
		System.out.println(max);
		avg=(avg/1024);
		avg=(double)Math.round(avg*100)/100;//limit to two decimal places
		System.out.println(avg);
		br.close();
	        Double average=new Double(avg);
	        Double maximum=new Double(max);
		Object Sample="Sample";
		json.put("Usecasename", Sample);
		json.put("Values:", values);
                json.put("Averagememory(MB)", average);
                json.put("Maximummemory(MB)", maximum);
		FileWriter file1 = new FileWriter("C:\\Users\\Aishwarya\\Documents\\hackathonmemory.json");//writing it to file
		file1.write(json.toJSONString());
                file1.close();
		
           //opening DB connection
            String JdbcURL = "jdbc:mariadb://localhost:3306/demo?useSSL=false";
            String Username = "root";
            String password = "1234Aish*";
		    Connection connection = DriverManager.getConnection(JdbcURL,Username,password);
		    System.out.println("CONNECTION ESTABLISHED...");
			
			//Table creation query
			
		 Statement query=connection.createStatement();
		 String sql = "CREATE TABLE Memorydetails" +
                 "(Transactionname VARCHAR(255), " +
                 " Maximum VARCHAR(255), " + 
                 " Average VARCHAR(255))";
		 query.executeUpdate(sql);
		 System.out.println("TABLE CREATED...");
			
			//Insertion query
			
	          query.executeUpdate("INSERT INTO Memorydetails (Transactionname, Maximum, Average) "
				          +"VALUES ('Sample','" +max+ "','" +avg+ "')");
		  System.out.println("VALUES UPDATED...");
           }catch(Exception e)
           {
        	   System.out.println(e);
           }
	}
	
	public static void main(String[] args)
	{
		readfromfile();
		
	}
	
}
	



import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.*;

import org.json.simple.JSONObject;

import org.json.simple.JSONArray;
public class Mock {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try
		{
			JSONObject json=new JSONObject();
			
			JSONObject sampletransaction = new JSONObject();
			
			
			JSONObject values = new JSONObject();
	        
	         
		String fileName = "C:\\Users\\Aishwarya\\Documents\\mock.txt";
		File file = new File(fileName);
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		String[] words;
		String line;
		double avg;
		int count=0;
		double sum=0,max=0;
		
		
		while((line = br.readLine()) != null){
		    //process the line
			 words=line.split("  ");
				   
				    	//System.out.println(words.length);
		              // System.out.println(words[3]);
		               sum=sum+Double.parseDouble(words[3]);
		               count++;
		               
				   	if(Double.parseDouble(words[3])>max)
				   		max=Double.parseDouble(words[3]);
				   	values.put(count+"s",words[3]);
		}
		// maximum=(JSONObject)max;
		
		json.put("sampletransaction", sampletransaction);
		sampletransaction.put("values:", values);
		Double maximum= new Double(max);
		
		sampletransaction.put("maxcpu:",maximum);
		avg=sum/count;
		//System.out.println("maximum"+max+"average"+avg);
		Double average=new Double(avg);
	    sampletransaction.put("avgcpu", average);
		
		br.close();
		
		FileWriter file1 = new FileWriter("C:\\Users\\Aishwarya\\Documents\\response.json");
			 
            file1.write(json.toJSONString());
           
            file1.close();
            String JdbcURL = "jdbc:mariadb://localhost:3306/demo?useSSL=false";
            String Username = "root";
            String password = "1234Aish*";
		Connection connection = DriverManager.getConnection(JdbcURL,Username,password);
		System.out.println("CONNECTION ESTABLISHED...");
		Statement query=connection.createStatement();
		
             
		 String sql = "CREATE TABLE sampletransaction" +
                 "(id INTEGER not NULL, " +
                 " maximum VARCHAR(255), " + 
                 " average VARCHAR(255), " + 
                 " PRIMARY KEY ( id ))";
		 query.executeUpdate(sql);
		System.out.println("TABLE CREATED...");
				 query.executeUpdate("INSERT INTO sampletransaction (id, maximum, average) "
				          +"VALUES (1,'" +max+ "','" +avg+ "')");
				          System.out.println("VALUES UPDATED...");
				 String display = "SELECT id, maximum, average FROM sampletransaction";
			      ResultSet rs = query.executeQuery(display);
			      int id;
			      String maximumdisplay,averagedisplay;
			      while(rs.next())
			      {
			    	  System.out.println("VALUES FROM TABLE");
			       id  = rs.getInt("id");
			       maximumdisplay = rs.getString("maximum");
			        averagedisplay = rs.getString("average");
			         //System.out.println("TransactionID|Maximum|Average");
			         //System.out.println(""+id+"            |"+maximumdisplay+"  |"+averagedisplay);
			      
                
		 PrintWriter pw = new PrintWriter(new FileWriter("C:\\Users\\Aishwarya\\Documents\\responsetime.html"));
         pw.println("<TABLE BORDER><TR><TH>TRANSACTION ID<TH>MAXIMUM CPU TIME<TH>AVERAGE CPU TIME</TR>");
        
                pw.println("<TR><TD>" +id+ "<TD>" + maximumdisplay+"<TD>"+averagedisplay);
			      
         pw.println("</TABLE>");
               pw.close();
			      }
               rs.close();
      		 connection.close();     
		}catch(Exception e){
			System.out.println(e);
		}
		
	}

}

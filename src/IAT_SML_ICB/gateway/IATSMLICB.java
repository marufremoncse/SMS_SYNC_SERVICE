package IAT_SML_ICB.gateway;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

import IAT_SML_ICB.db.DBHandler;

public class IATSMLICB {
	DBHandler dbh;
	Connection db2con;
	Util util=new Util();
	public IATSMLICB() {
		dbh = new DBHandler();
		db2con=dbh.getDB2Db_con();
		process();
		// TODO Auto-generated constructor stub
	}
	
	public void process() {
		try {
			String sql_1=util.sql_SMS_AFT_AS400;
			String sql_2=util.sql_SMS_AS400;
			
			System.out.println("Sql:SMS_AFT_AS400");
			Statement st=db2con.createStatement();
			ResultSet rs=st.executeQuery(sql_1);
			ResultSetMetaData rsmd = rs.getMetaData();
			if(rs.next()){
				
				for(int i=0;i<rsmd.getColumnCount();i++) {
					System.out.println("Column Name:"+rsmd.getColumnName(i));
					System.out.println("Column Type:"+rsmd.getColumnType(i));
					System.out.println("Column Value:"+rs.getString(i));
				}
			}
			rs.close();
			System.out.println("Sql:SMS_AS400");
			rs=st.executeQuery(sql_2);
			rsmd = rs.getMetaData();
			if(rs.next()){
				
				for(int i=0;i<rsmd.getColumnCount();i++) {
					System.out.println("Column Name:"+rsmd.getColumnName(i));
					System.out.println("Column Type:"+rsmd.getColumnType(i));
					System.out.println("Column Value:"+rs.getString(i));
				}
			}
			rs.close();
			st.close();
			
		}catch (Exception e) {
			// TODO: handle exception
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new IATSMLICB();
	}

}

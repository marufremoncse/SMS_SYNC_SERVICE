package IAT_SML_ICB.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class DBHandler {
	Connection dbmysql_con;
	Connection db2_con;
	
	PreparedStatement preparedStatement;
	PreparedStatement updatePreparedStatement;
	static int noOfConnection = 0;

	public DBHandler() {

		try {

			createMySqlConnection();
			createDB2Connection();
			
		} catch (Exception e) {
			System.out.println("X X X X---------- X ---------X X X X");
			System.out.println("error in " + getClass().getName()
					+ " : error is :" + e.getMessage());
			e.printStackTrace();
		}
	}

	public void createMySqlConnection(){
		try {
                    noOfConnection++;
			System.out.println("Creating Database Connection :" + noOfConnection);
			Class.forName("com.mysql.jdbc.Driver");
			dbmysql_con = null;
			 //db_con = DriverManager.getConnection("jdbc:mysql://private-db-mysql-iatl-sms-do-user-6409164-0.b.db.ondigitalocean.com:25060/iatl_smssheba_com?autoReconnect=true&verifyServerCertificate=false&ssl-mode=REQUIRED&useSSL=false","smssheba", "Iat123!@#");
			dbmysql_con = DriverManager.getConnection("jdbc:mysql://localhost:3306/icb?allowPublicKeyRetrieval=true&useSSL=false","icb", "icb@567!@#");  //root/smlBD@123!@#
			//db_con = DriverManager.getConnection("jdbc:mysql://localhost:3306/iat_smsgw?allowPublicKeyRetrieval=true&useSSL=false&useTimezone=true&serverTimezone=GMT%2B6","iatsmsgw", "Iat123!@#");
			System.out.println("MySql Database Connected :" + noOfConnection);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	

	public void createDB2Connection(){
		try{
			 noOfConnection++;
				System.out.println("Creating Database Connection :" + noOfConnection);
			Class.forName("com.ibm.as400.access.AS400JDBCDriver");
			String client="172.22.0.17";
			String user="icopru1";
			String pass="icopru1";
			db2_con=null; 
			db2_con=DriverManager.getConnection("jdbc:as400://"+client+";naming=sql;errors=full",user,pass);
			System.out.println("DB2 Database Connected :" + noOfConnection);
			
		/*	String sql="SELECT DDPAR1.NEXTDT FROM DKuDATDEP1.DDPAR1 DDPAR1";
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery(sql);
			if(rs.next()){
				System.out.println("Data retrieved by SMARTLAB-IATL   "+rs.getString(1));
			}
			rs.close();
			st.close();
			con.close();*/
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/*
	 * public Vector getInfo(){ Vector aVector=null;
	 * preparedStatement.setString(1, userId); ResultSet resultSet=
	 * preparedStatement.executeQuery(); while(resultSet.next()){ aVector=new
	 * Vector(); // aVector.add(resultSet.getString(columnIndex)); break; }
	 * return aVector;
	 * 
	 * }
	 */
	
	public ResultSet getResultSetFromQuery(String sql) {
		try {
			PreparedStatement statement = dbmysql_con.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();
			return resultSet;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
	
	public boolean updateDB(long id, String status) {
		try {
			updatePreparedStatement.setString(1,status);
			updatePreparedStatement.setLong(2,id);
			updatePreparedStatement.executeUpdate();
			return true;
		} catch (SQLException se) {
			se.printStackTrace();
			return false;
		}
	}
	
	public Connection getMysqlDb_con() {
		return dbmysql_con;
	}
	
	public Connection getDB2Db_con() {
		return db2_con;
	}
	
    public boolean closeConnection(){
        try {
            System.out.println("Closeing Database Connection:");
            dbmysql_con.close();
            db2_con.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}

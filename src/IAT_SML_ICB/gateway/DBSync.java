package IAT_SML_ICB.gateway;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

import IAT_SML_ICB.db.DBHandler;

public class DBSync {
	DBHandler dbh;
	Connection db2con,mysqlcon;
	Util util=new Util();
	
	public DBSync() {
		dbh = new DBHandler();
		db2con=dbh.getDB2Db_con();
		mysqlcon=dbh.getMysqlDb_con();
		boolean stat=true;
		while(stat) {
			String sql=loadSql(1);
			//syncnow(getMaxTmtSeq(),sql);
			syncnow(sql);
			// TODO Auto-generated constructor stub
			IATSMSSender itsms=new IATSMSSender(dbh);
			itsms.processSMS();
			try {
				System.out.println("\n\n...Sleep for 30......");
				Thread.sleep(30000);
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		dbh.closeConnection();
	}
	
	
	public void syncnow(String sql_2) {
		try {
			//if(seq==0)
			//	seq=1;
			//String sql_1=util.sql_SMS_AFT_AS400;
			//String sql_1=util.sql_sml_part1+seq+util.sql_sml_part2;
			//String list=getlastCode(seq);
			String sql_1=sql_2;//+seq+" or (TMTRAN.TMSYSREF="+seq+" and TMTRAN.TMHOSTTXCD not in ("+list+")))"+util.newSql_2;
			System.out.println("Sql:From DB::"+sql_1);
			Statement st=db2con.createStatement();
			ResultSet rs=st.executeQuery(sql_1);
			//ResultSetMetaData rsmd = rs.getMetaData();
			int rowcnt=0;
			while(rs.next()){
				String tmsyref=rs.getString("TMSYSREF");  //TMSYREF String
				long tmtseq=rs.getLong("TMTXSEQ");  //TMTSEQ Numeric
				String tmefdat=rs.getString("TMEFFDAT");  //TMEFDAT Date 6 digit ddmmyy
				String tmtiment=rs.getString("TMTIMENT"); //TMTIMENT Time 
				String tmacctno=rs.getString("TMACCTNO");//TMACCTNO  Account Numeric
				String tmtxamt=rs.getString("TMTXAMT"); //TMTXAMT		Amount number decimal 2
				String tmglcur=rs.getString("TMGLCUR"); //TMGLCUR  currency String 3 digit
				String tmdorc=rs.getString("TMDORC");//TMDORC	Debit or Credit  D Or C 1 char
				String tmhostttxco=rs.getString("TMHOSTTXCD");//TMHOSTTTXCO	Transaction code numeric
				String desc=rs.getString("TXNDESC");//DESC	String Ki purpose
				String cfeadd=rs.getString("CFEADD").trim().replaceAll("-", "");// Mobile Number
				//CFINSC not required
				//CFSFLG not required
				//TMTELLID Not required
				String cbal=rs.getString("BALANCE");//CBAL BALANCE Balance Numeric decimal 2
			
				if(tmefdat.length()==5){
					tmefdat="0"+tmefdat;
				}
				if(cfeadd.length()==11)
					cfeadd="88"+cfeadd;
				if(cfeadd.length()==10)
					cfeadd="880"+cfeadd;
				
			/*	for(int i=1;i<=rsmd.getColumnCount();i++) {
					System.out.println("Column Name:"+rsmd.getColumnName(i));
					System.out.println("Column Type:"+rsmd.getColumnType(i));
					System.out.println("Column Value:"+rs.getString(i));
				}*/
				System.out.println("===================================");
				if(isExist(tmsyref.trim(),tmhostttxco.trim())==0) {
					String mysql="insert into iat_icb_db2_sync(cfeadd,tmtseq,tmsyref,tmefdat,tmtiment,tmacctno,tmtxamt,tmglcur,tmdorc,tmhostttxco,description,cbal)"
							+ "values('"+cfeadd.trim()+"',"+tmtseq+",'"+tmsyref.trim()+"','"+tmefdat+"','"+tmtiment+"',"+tmacctno+","+tmtxamt+",'"+tmglcur.trim()+"','"+tmdorc+"','"+tmhostttxco+"','"+desc.trim()+"',"+cbal+")";
					System.out.println("Mysql::"+mysql);
					Statement stmt=mysqlcon.createStatement();
					stmt.executeUpdate(mysql);
					stmt.close();
				}else {
					System.out.println("Skipped:"+tmsyref+"::"+tmhostttxco);
				}
				rowcnt++;
			}/*else {
				System.out.println("ICB: No record Found");
			}*/
			System.out.println("ICB: Record Imported:"+rowcnt);
			rs.close();
			st.close();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public int isExist(String ref,String code) {
		int cnt=0;
		try {
			String maxsql="SELECT count(*) as cnt FROM iat_icb_db2_sync where tmsyref='"+ref+"' and tmhostttxco='"+code+"'";
			Statement stmt=mysqlcon.createStatement();
			ResultSet rs=stmt.executeQuery(maxsql);
			if(rs.next()) {
				cnt=rs.getInt(1);
				
			}
			rs.close();
			stmt.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		//System.out.println("ICB: Record Last Sequence:"+seq);
		return cnt;
		
	}
	public long getMaxTmtSeq() {
		long seq=1;
		try {
			String maxsql="SELECT max(tmsyref) FROM iat_icb_db2_sync where ins_date>date(now())";
			Statement stmt=mysqlcon.createStatement();
			ResultSet rs=stmt.executeQuery(maxsql);
			if(rs.next()) {
				seq=rs.getLong(1);
				
			}
			rs.close();
			stmt.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println("ICB: Record Last Sequence:"+seq);
		return seq;
	}
	
	public String getlastCode(long ref) {
		long seq=0;
		String list="1";
		try {
			String maxsql="SELECT group_concat(tmhostttxco SEPARATOR ',') FROM iat_icb_db2_sync where ins_date>date(now()) and tmsyref="+ref+" group by tmsyref";
			Statement stmt=mysqlcon.createStatement();
			ResultSet rs=stmt.executeQuery(maxsql);
			if(rs.next()) {
				list=rs.getString(1);
				
			}
			rs.close();
			stmt.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println("ICB: Record Last Sequence:"+seq);
		return list;
	}
	
	public String loadSql(int id) {
		String sql="";
		try {
			String maxsql="SELECT sql_text FROM iat_db2_sql where id="+id;
			Statement stmt=mysqlcon.createStatement();
			ResultSet rs=stmt.executeQuery(maxsql);
			if(rs.next()) {
				sql=rs.getString(1);
				
			}
			rs.close();
			stmt.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println("ICB: Sql:"+sql);
		return sql;
	}
	
/*	public boolean isRun() {
		String sql="";
		try {
			String maxsql="SELECT sql_text FROM iat_db2_sql where id="+id;
			Statement stmt=mysqlcon.createStatement();
			ResultSet rs=stmt.executeQuery(maxsql);
			if(rs.next()) {
				sql=rs.getString(1);
				
			}
			rs.close();
			stmt.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println("ICB: Sql:"+sql);
		return sql;
	}*/
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new DBSync();
	}
	
}

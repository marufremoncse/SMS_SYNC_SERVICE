package IAT_SML_ICB.gateway;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import IAT_SML_ICB.db.DBHandler;


public class IATSMSSender {
	DBHandler dbh;
	Connection mysqlcon;

	 String encodeFormat="UTF-8";
	 int thid=0;
	 Route route;
	 String sender="8809617649915";
	 String phone="8801713615646";
	public IATSMSSender(DBHandler dbh) {
		// TODO Auto-generated constructor stub
		mysqlcon=dbh.getMysqlDb_con();
		
		route =new Route();
		route.setId(12);
		route.setUrl("https://labapi.smlbulksms.com/smsapiv3?");
		route.setUserIdParam("apikey");
		route.setUserIdValue("11ab0f9327b1e8e5a306d237e16a8321");
		route.setMsisdnParam("msisdn");
		//route.setMsisdnValue("");
		route.setSenderParam("sender");
		//route.setSenderValue("");
		route.setSmsTextParam("smstext");
		//route.setSmsTextValue("");
		
		
		
	}
	
	public void smslog(long id,String msisdn,String sender,String sms,String gwresponse,String status) {
		try {
			String sql="insert into sms_log(msisdn,sender_id,sms,pa_status,gw_response,send_status,ref_id)"
					+ "values('"+msisdn+"','"+sender+"','"+sms+"','push','"+gwresponse+"','"+status+"',"+id+")";
			Statement st=mysqlcon.createStatement();
			st.execute(sql);
			st.close();
			;
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void processSMS() {
		
			try {
				
				String sql_1="select id,msisdn,smstext from iat_sms_queue_final";
				String sqls="update iat_icb_db2_sync set status='S',pro_date=now() where id=";
				String sqlf="update iat_icb_db2_sync set status='F',pro_date=now() where id=";
				System.out.println("Sql:SMS DB::"+sql_1);
				Statement st=mysqlcon.createStatement();
				Statement stu=mysqlcon.createStatement();
				ResultSet rs=st.executeQuery(sql_1);
				//ResultSetMetaData rsmd = rs.getMetaData();
				int rowcnt=0;
				while(rs.next()){
					long id=rs.getLong("id");  //TMSYREF String
					String msisdn=phone;//rs.getString("msisdn");  //TMTSEQ Numeric
					String smstext=rs.getString("smstext");  //TMEFDAT Date 6 digit ddmmyy
					RequestDetails requestDetails=new RequestDetails();
					requestDetails.setId(id);
					requestDetails.setMsisdn(msisdn);
					requestDetails.setSmstext(smstext);
					requestDetails.setSmstype(1);
					Status stat=processIATL(requestDetails,sender);
					
					if(stat.getStatus()==0) {
						stu.execute(sqls+id);
						smslog(id,msisdn,sender,smstext,stat.getTrid(),"S");
					}else {
						stu.execute(sqlf+id);
						smslog(id,msisdn,sender,smstext,stat.getTrid(),"F");
					}
				}
					rs.close();
					st.close();
					stu.close();
				
				}catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
	}
	
	public Status processIATL(RequestDetails requestDetails,String sendertext){
    	//public Status process(RequestDetails requestDetails,SenderInfo senderinfo, Route route){
        System.out.print("THREAD:" + thid + "->ReqID:" + requestDetails.getId() + " MSISDN:" + requestDetails.getMsisdn());
        Status st = new Status();
        st.setGWStatus("100",route.getId());
        String SMS_RESPONSE = "";
		String status = "100";
		long tm = System.currentTimeMillis();
		String destination="";
		String messageid="";
        try{
		
        	//http://sms.ajuratech.com/api/mt/SendSMS?user=demo&password=demo123&senderid=WEBSMS&channel=Normal&DCS=0&flashsms=0&number=91989xxxxxxx&text=test messages
			//String msisdn          = requestDetails.getMsisdn();
			//String sender       =  senderinfo.getSender();
			String httpurl			= route.getUrl();
			String user			= URLEncoder.encode(route.getUserIdParam(),encodeFormat)+"="+URLEncoder.encode(route.getUserIdValue(),encodeFormat);
			//String password     = URLEncoder.encode(route.getPassParam(),encodeFormat)+"="+URLEncoder.encode(route.getPassValue(),encodeFormat);
			String msisdn		= URLEncoder.encode(route.getMsisdnParam(),encodeFormat)+"="+URLEncoder.encode(requestDetails.getMsisdn(),encodeFormat);
			String sender       = URLEncoder.encode(route.getSenderParam(),encodeFormat)+"="+URLEncoder.encode(sendertext,encodeFormat);
			String smstext		= URLEncoder.encode(route.getSmsTextParam(),encodeFormat)+"="+URLEncoder.encode(requestDetails.getSmstext(),encodeFormat);
			
			String applicationname = "IAT_SMSGW.gateway.IATSMSGW";
			
			String sms_url    = httpurl+user+"&" + msisdn + "&" + sender + "&" + smstext;
			//if(route.getPassParam().length()<2)
				//sms_url    = httpurl+user+"&" + msisdn + "&" + smstext;
			//if(route.getSenderParam().length()<5)
				//sms_url    = httpurl+user+"&"+password+"&" + msisdn + "&" + smstext;
			if(isSmsLong(requestDetails.getId(),requestDetails.getMsisdn(),requestDetails.getSmstext(),requestDetails.getSmstype()))
				sms_url=sms_url+"&type=long";
			if(requestDetails.smstype==3 || requestDetails.smstype==4)
				sms_url=sms_url+"&smsformat=8";
			
			String smsresponse = "";
			System.out.println("URL::"+sms_url);
			URL url = new URL(sms_url);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setConnectTimeout(20000);
			conn.setReadTimeout(20000);
			conn.setRequestMethod("GET");
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			String line;
			while ((line = rd.readLine()) != null) {
				smsresponse += line;
			}
			rd.close();
			try{
				JSONParser j = new JSONParser();
				 JSONObject o = (JSONObject) j.parse(smsresponse);
				JSONArray jsonArray = (JSONArray) o.get("response");
		        
		        Iterator<JSONObject> iterator = jsonArray.iterator();
				        
		        while(iterator.hasNext()) {
		            JSONObject r=iterator.next();
		           
		            status = r.get("status").toString();
		           }
		        
			}catch(Exception ex){
				ex.printStackTrace();
				status="4000";
			}
	        
			SMS_RESPONSE = smsresponse;
			System.out.println("RESPONSE: "+SMS_RESPONSE);
			
			messageid=SMS_RESPONSE.trim();
			
			
			messageid=SMS_RESPONSE.trim();
		}catch(Exception e){
			e.printStackTrace();
			SMS_RESPONSE = e.toString();
			status="2000";
			messageid=SMS_RESPONSE.trim();
			
		}
		
		long tmt = System.currentTimeMillis();

		
		
		System.out.println("Status:"+status);
		//System.out.println("Destination:"+destination);
		System.out.println("messageid:"+messageid);
		
		st.setTrid(messageid);
		st.setGWStatus(status,route.getId());
		//st.setFailReason(rreason);
		st.setTt(tmt - tm);

		return st;
        //return status;
    }
	
	
	public boolean isSmsLong(long id,String msisdn,String smstext,int sms_type){
    	int noofpart=1;
    	if(sms_type==1) {
    		int splen=spcount(smstext);
			int len=smstext.length()+splen;
			System.out.println("ID:"+id+" msisdn:"+msisdn+" Text Length:"+len+" Special Length:"+splen);
			if(len>160) {
				noofpart=(int)Math.ceil((double)len/153);
				System.out.println("ID:"+id+" msisdn:"+msisdn+" Text noofpart:"+noofpart+" DB noofpart:"+noofpart);
			}
		}
		if(sms_type==3) {
			int len=smstext.length();
			System.out.println("ID:"+id+" msisdn:"+msisdn+"Unicode Length:"+len);
			if(len>70) {
				noofpart=(int)Math.ceil((double)len/67);
				System.out.println("ID:"+id+" msisdn:"+msisdn+" Unicode noofpart:"+noofpart+" DB noofpart:"+noofpart);
			}
		}
		if(noofpart>1)
			return true;
		else
			return false;
	}
	
	public int spcount(String inp){
		char t[]=inp.toCharArray();
		int count=0;
		for(int i=0;i<t.length;i++){
			switch(t[i]){
			case '[':
			case ']':
			case '{':
			case '}':
			case '|':
			case '~':
			case '^':
			case '\\':
				count++;
				//System.out.println("Detected:"+t[i]);
				break;
			//default:
				//int st=t[i];
				//System.out.println(st);
				//break;
			}
		}
		
		return count;
		
	}
		

}

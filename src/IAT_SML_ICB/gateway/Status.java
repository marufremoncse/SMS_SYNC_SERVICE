package IAT_SML_ICB.gateway;

public class Status {
        private int status;
        private String gwstatus;
        private long tt;
        private String trid;
        private String failReason;
        private String balance;
        private boolean isBalance=false;


        public int getStatus() { return this.status; }
        public void setStatus(int status) { this.status = status; }
        public String getBalance() { return balance; }
        public void setBalance(String balance) { this.balance = balance; this.isBalance=true;}
        public boolean isBalanceExist() {return this.isBalance;}
        
        public String getGWStatus() { return this.gwstatus; }
        public void setGWStatus(String gwstatus,int routeid) { 
        	this.gwstatus = gwstatus; 
        	switch(routeid) {
        	case 1:
        	case 11:
        	case 12:
        		if(gwstatus.equalsIgnoreCase("0"))
        			this.status=0;
        		else 
        			this.status=Integer.parseInt(gwstatus);
        		break;
        	case 3:
        	case 6:
        		if(gwstatus.equalsIgnoreCase("0"))
        			this.status=0;
        		else if(gwstatus.equalsIgnoreCase("1"))
        			this.status=0;
        		else if(gwstatus.equalsIgnoreCase("2"))
        			this.status=0;
        		else
        			this.status=Integer.parseInt(gwstatus);
        		break;
        	case 2:
        	case 10:
        	case 14:
        		if(gwstatus.equalsIgnoreCase("1"))
	    			this.status=0;
	    		else if(gwstatus.equalsIgnoreCase("0"))
	    			this.status=1;
	    		else
	    			this.status=Integer.parseInt(gwstatus);
        		break;
        		
        	case 5:
        	case 51:
        	case 52:
        		if(gwstatus.equalsIgnoreCase("200"))
	    			this.status=0;
	    		else
	    			this.status=Integer.parseInt(gwstatus);
        		break;
        	case 9:
        		if(gwstatus.equalsIgnoreCase("SMS SUBMITTED")){
	    			this.status=0;
	    			this.gwstatus="0";
        		}else{
        			try{
        				this.status=Integer.parseInt(gwstatus);
        			}catch(Exception e){
        				System.out.println("Unknown response from MetroNet");
        				this.status=9000;
    	    			this.gwstatus="9000";
        			}
	    			
        		}
        		break;
        	
        	default:    //For teletalk use default due to multiple route for multiple masking  1 to 1 mapping
        		this.status=Integer.parseInt(gwstatus);
        		break;
        		
        	}
        
        }
        
        public long getTt() { return tt; }
        public void setTt(long tt) { this.tt = tt; }
        
        public String getTrid() { return trid; }
        public void setTrid(String trid) { this.trid = trid; }
        
        public String getFailReason() { return failReason; }
        public void setFailReason(String failReason) { this.failReason = failReason; }
        
        public String toString(){ return "TRID: "+getTrid()+", Status: "+getStatus()+", Reason: "+getFailReason();}
}

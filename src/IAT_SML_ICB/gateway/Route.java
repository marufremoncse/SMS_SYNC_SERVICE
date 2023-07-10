package IAT_SML_ICB.gateway;

import java.util.HashMap;

public class Route {
	int id;
	int smscType;
	String useridParam;
	String useridValue;
	String passParam;
	String passValue;
	String msisdnParam;
	String msisdnValue;
	String senderParam;
	String senderValue;
	String smstextParam;
	String smstextValue;
	String url;
	String prefixnotallow;
	 HashMap<String, String> hmprna = new HashMap<String, String>();
	 boolean allallow;
	 
	boolean isalternate;
	 boolean issetenforce;
	//long balance;

	 public Route() {
		 
	 }
	
	public Route(int id, int smscType,String useridParam, String useridValue, String passParam,String passValue,String msisdnParam, String msisdnValue, String senderParam,String senderValue,String smstextParam,String smstextValue,String url,String prefixnotallow,int isalternate,int issetenforce) {
		super();
		this.id = id;
		this.smscType = smscType;
		this.useridParam = useridParam;
		this.useridValue = useridValue;
		this.passParam = passParam;
		this.passValue = passValue;
		this.msisdnParam = msisdnParam;
		this.msisdnValue = msisdnValue;
		this.senderParam = senderParam;
		this.senderValue =senderValue;
		this.smstextParam = smstextParam;
		this.smstextValue = smstextValue;
		this.url = url;
		this.prefixnotallow=prefixnotallow;
		this.allallow=false;
		this.isalternate=false;
		this.issetenforce=false;
		if(isalternate==1)
			this.setIsalternate(true);
		if(issetenforce==1)
			this.setIssetenforce(true);
		this.processPrNotAllow();
		//this.balance = balance;
		
	}
	private void processPrNotAllow(){
		if(this.prefixnotallow.equals("N"))
			this.allallow=true;
		else{
			this.allallow=false;
			this.hmprna.clear();
			String nalist[]=this.prefixnotallow.split(";");
			for(int i=0;i<nalist.length;i++)
				this.hmprna.put(nalist[i], nalist[i]);
		}
	}
	
	public boolean isPrefixAllow(String prefix){
		if(this.allallow)
			return true;
		else{
			if(this.hmprna.containsKey(prefix))
				return false;
			else
				return true;
		}
	}
	
	public int getId() {
		return this.id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getSmscType() {
		return this.smscType;
	}
	public void setSmscType(int smscType) {
		this.smscType = smscType;
	}
	public String getUserIdParam() {
		return this.useridParam;
	}
	public void setUserIdParam(String useridParam) {
		this.useridParam = useridParam;
	}
	public String getUserIdValue() {
		return this.useridValue;
	}
	public void setUserIdValue(String useridValue) {
		this.useridValue = useridValue;
	}
	public String getPassParam() {
		return this.passParam;
	}
	public void setPassParam(String passParam) {
		this.passParam = passParam;
	}
	public String getPassValue() {
		return this.passValue;
	}
	public void setPassValue(String passValue) {
		this.passValue = passValue;
	}
	public String getMsisdnParam() {
		return this.msisdnParam;
	}
	public void setMsisdnParam(String msisdnParam) {
		this.msisdnParam = msisdnParam;
	}
	public String getMsisdnValue() {
		return this.msisdnValue;
	}
	public void setMsisdnValue(String msisdnValue) {
		this.msisdnValue = msisdnValue;
	}
	public String getSenderParam() {
		return this.senderParam;
	}
	public void setSenderParam(String senderParam) {
		this.senderParam = senderParam;
	}
	public String getSenderValue() {
		return this.senderValue;
	}
	public void setSenderValue(String senderValue) {
		this.senderValue = senderValue;
	}
	public String getSmsTextParam() {
		return this.smstextParam;
	}
	public void setSmsTextParam(String smstextParam) {
		this.smstextParam = smstextParam;
	}
	public String getSmsTextValue() {
		return this.smstextValue;
	}
	public void setSmsTextValue(String smstextValue) {
		this.smstextValue = smstextValue;
	}
	public String getUrl() {
		return this.url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
/*	public long getBalance() {
		return this.balance;
	}
	public void setBalance(long balance) {
		this.balance = balance;
	}
	*/
	
	public boolean isIsalternate() {
		return isalternate;
	}
	public void setIsalternate(boolean isalternate) {
		this.isalternate = isalternate;
	}
	public boolean isIssetenforce() {
		return issetenforce;
	}
	public void setIssetenforce(boolean issetenforce) {
		this.issetenforce = issetenforce;
	}
	public synchronized void update(int id, int smscType,String useridParam, String useridValue, String passParam,String passValue,String msisdnParam, String msisdnValue, String senderParam,String senderValue,String smstextParam,String smstextValue,String url,String prefixnotallow,int isalternate,int issetenforce) {
		
		//this.id = id;
		int i=0;
		if(this.smscType != smscType){
			System.out.println("last smscType:"+this.smscType);
			this.smscType = smscType;
			i++;
			System.out.println("Change smscType:"+this.smscType);
		}
		if(!this.useridParam.equals(useridParam)){
			System.out.println("last useridParam:"+this.useridParam);
			this.useridParam = useridParam;
			i++;
			System.out.println("Change useridParam:"+this.useridParam);
		}
		if(!this.useridValue.equals(useridValue)){
			System.out.println("last useridValue:"+this.useridValue);
			this.useridValue = useridValue;
			i++;
			System.out.println("Change useridValue:"+this.useridValue);
		}
		if(!this.passParam.equals(passParam)){
			System.out.println("last passParam:"+this.passParam);
			this.passParam = passParam;
			i++;
			System.out.println("Change passParam:"+this.passParam);
		}
		if(!this.passValue.equals(passValue)){
			System.out.println("last passValue:"+this.passValue);
			this.passValue = passValue;
			i++;
			System.out.println("Change passValue:"+this.passValue);
		}
		if(!this.msisdnParam.equals(msisdnParam)){
			System.out.println("last msisdnParam:"+this.msisdnParam);
			this.msisdnParam = msisdnParam;
			i++;
			System.out.println("Change msisdnParam:"+this.msisdnParam);
		}
		if(!this.msisdnValue.equals(msisdnValue)){
			System.out.println("last msisdnValue:"+this.msisdnValue);
			this.msisdnValue = msisdnValue;
			i++;
			System.out.println("Change msisdnValue:"+this.msisdnValue);
		}
		if(!this.senderParam.equals(senderParam)){
			System.out.println("last senderParam:"+this.senderParam);
			this.senderParam = senderParam;
			i++;
			System.out.println("Change senderParam:"+this.senderParam);
		}
		if(!this.senderValue.equals(senderValue)){
			System.out.println("last senderValue:"+this.senderValue);
			this.senderValue =senderValue;
			i++;
			System.out.println("Change senderValue:"+this.senderValue);
		}
		if(!this.smstextParam.equals(smstextParam)){
			System.out.println("last smstextParam:"+this.smstextParam);
			this.smstextParam = smstextParam;
			i++;
			System.out.println("Change smstextParam:"+this.smstextParam);
		}
		if(!this.smstextValue.equals(smstextValue)){
			System.out.println("last smstextValue:"+this.smstextValue);
			this.smstextValue = smstextValue;
			i++;
			System.out.println("Change smstextValue:"+this.smstextValue);
		}
		if(!this.url.equals(url)){
			System.out.println("last url:"+this.url);
			this.url = url;
			i++;
			System.out.println("Change url:"+this.url);
		}
		if(!this.prefixnotallow.equals(prefixnotallow)){
			this.prefixnotallow=prefixnotallow;
			this.processPrNotAllow();
			i++;
			System.out.println("Change prefixnotallow:"+this.prefixnotallow);
		}
		if(this.isIsalternate()==true && isalternate==0){
			this.setIsalternate(false);
			i++;
			System.out.println("Change alternet:"+isalternate);
		}else if(this.isIsalternate()==false && isalternate==1){
			this.setIsalternate(true);
			i++;
			System.out.println("Change alternet:"+isalternate);
		}
		
		if(this.isIssetenforce()==true && issetenforce==0){
			this.setIssetenforce(false);
			i++;
			System.out.println("Change enfore:"+issetenforce);
		}else if(this.isIssetenforce()==false && issetenforce==1){
			this.setIssetenforce(true);
			i++;
			System.out.println("Change enfore:"+issetenforce);
		}
		//this.balance = balance;
		if(i>0)
			System.out.println("Route Information updated for ID:"+id);
	}
    public String toString() {
        return "Route{" + "id=" + id + ", smsc type=" + smscType + ", url=" + url + "}";//, balance=" + balance+"}";
    }
	
}

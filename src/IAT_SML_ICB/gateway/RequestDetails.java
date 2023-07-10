package IAT_SML_ICB.gateway;

public class RequestDetails {

    long id;
    String msisdn;
    long smstextid;
    int userid;
    String smstext;
    int noofpart;
    int smstype;
    int senderid;
    int cost;
    int campaignid;
    int smsuserid;
    int groupid;
    int sendnoworschedule;//1 for sendnow, 2 for schedule
    
    public RequestDetails() {
    
    }
    
	public RequestDetails(long id, String msisdn, long smstextid, int userid, String smstext, int noofpart,int smstype,int senderid,int campaignid,int smsuserid,int groupid,int sendnoworschedule) {
        this.id = id;
        this.msisdn = msisdn;
        this.smstextid = smstextid;
        this.userid = userid;
        this.smstext = smstext;
        this.noofpart = noofpart;
        this.smstype=smstype;
        this.senderid=senderid;
        this.cost=0;
        this.campaignid=campaignid;
        this.smsuserid=smsuserid;
        this.groupid=groupid;
        this.sendnoworschedule=sendnoworschedule;
    }

 /*   public RequestDetails(int userid,String msisdn, int noofpart,int senderid){
    	this.userid = userid;
    	this.msisdn = msisdn;
    	this.noofpart = noofpart;
    }*/
    public long getId() {
		return id;
	}



	public void setId(long id) {
		this.id = id;
	}



	public String getMsisdn() {
		return msisdn;
	}



	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}



	public long getSmstextid() {
		return smstextid;
	}



	public void setSmstextid(long smstextid) {
		this.smstextid = smstextid;
	}



	public int getUserid() {
		return userid;
	}



	public void setUserid(int userid) {
		this.userid = userid;
	}



	public String getSmstext() {
		return smstext;
	}



	public void setSmstext(String smstext) {
		this.smstext = smstext;
	}



	public int getNoofpart() {
		return noofpart;
	}



	public void setNoofpart(int noofpart) {
		this.noofpart = noofpart;
	}



	public int getSmstype() {
		return smstype;
	}



	public void setSmstype(int smstype) {
		this.smstype = smstype;
	}



	public int getSenderid() {
		return senderid;
	}



	public void setSenderid(int senderid) {
		this.senderid = senderid;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public int getCampaignid() {
		return campaignid;
	}

	public void setCampaignid(int campaignid) {
		this.campaignid = campaignid;
	}

	public int getSmsuserid() {
		return smsuserid;
	}

	public void setSmsuserid(int smsuserid) {
		this.smsuserid = smsuserid;
	}
	
	public int getGroupid() {
		return groupid;
	}

	public void setGroupid(int groupid) {
		this.groupid = groupid;
	}

	public int getSendnoworschedule() {
		return sendnoworschedule;
	}

	public void setSendnoworschedule(int sendnoworschedule) {
		this.sendnoworschedule = sendnoworschedule;
	}

    @Override
    public String toString() {
        return "RequestDetails [id=" + id + ", msisdn=" + msisdn + ", smstextid="
                + smstextid + ", userid=" + userid + ", smstext=" + smstext
                + ", noofpart=" + noofpart + ", smstype=" + smstype+ ", senderid=" + senderid+ ", campaignid="+campaignid+", groupid="+groupid+", sendnow_schedule="+sendnoworschedule+"]";
    }
    

}

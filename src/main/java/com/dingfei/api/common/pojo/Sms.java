package com.dingfei.api.common.pojo;

import java.io.Serializable;

/**
 * Sms
 */
public class Sms implements Serializable {
    private static final long serialVersionUID = -1022591352440601579L;

    private String mobile;
    private String content;
    private String userAgent;
    private String clientIp;
    private int source;
    private String operator;
    private String orgId;
    private String token;
    private int isenablebk; // 是否启用备用通道[0-不启用、1-启用]
    private boolean isVerifyCode; // 是否是验证码通道
    private boolean isnew; //是否使用短信新发送方法
    private int type;


	/**
     * Constructor
     *
     * @param mobile    String
     * @param content   String
     * @param userAgent String
     * @param clientIp  String
     * @param source    int
     * @param operator  String
     * @param orgId     String
     */
    public Sms(String mobile, String content, String userAgent,
			String clientIp, int source, String operator, String orgId,
			String token, int isenablebk, boolean isVerifyCode, boolean isnew) {
		super();
		this.mobile = mobile;
		this.content = content;
		this.userAgent = userAgent;
		this.clientIp = clientIp;
		this.source = source;
		this.operator = operator;
		this.orgId = orgId;
		this.token = token;
		this.isenablebk = isenablebk;
		this.isVerifyCode = isVerifyCode;
		this.isnew = isnew;
	}
    
    /**
     * Sms
     * @param mobile
     * @param content
     * @param userAgent
     * @param clientIp
     * @param source
     * @param operator
     * @param orgId
     */
    public Sms(String mobile, String content, String userAgent, String clientIp,
            int source, String operator, String orgId) {
     this.mobile = mobile;
     this.content = content;
     this.userAgent = userAgent;
     this.clientIp = clientIp;
     this.source = source;
     this.operator = operator;
     this.orgId = orgId;
    }
    
	/**
     * getMobile
     *
     * @return String
     */
    public String getMobile() {
        return mobile;
    }


	/**
     * setMobile
     *
     * @param mobile String
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * getContent
     *
     * @return String
     */
    public String getContent() {
        return content;
    }

    /**
     * setContent
     *
     * @param content String
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * getUserAgent
     *
     * @return String
     */
    public String getUserAgent() {
        return userAgent;
    }

    /**
     * setUserAgent
     *
     * @param userAgent String
     */
    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    /**
     * getClientIp
     *
     * @return String
     */
    public String getClientIp() {
        return clientIp;
    }

    /**
     * setClientIp
     *
     * @param clientIp String
     */
    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    /**
     * getSource
     *
     * @return int
     */
    public int getSource() {
        return source;
    }

    /**
     * setSource
     *
     * @param source int
     */
    public void setSource(int source) {
        this.source = source;
    }

    /**
     * getOperator
     *
     * @return String
     */
    public String getOperator() {
        return operator;
    }

    /**
     * setOperator
     *
     * @param operator String
     */
    public void setOperator(String operator) {
        this.operator = operator;
    }

    /**
     * getOrgId
     *
     * @return String
     */
    public String getOrgId() {
        return orgId;
    }

    /**
     * setOrgId
     *
     * @param orgId String
     */
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getIsenablebk() {
		return isenablebk;
	}

	public void setIsenablebk(int isenablebk) {
		this.isenablebk = isenablebk;
	}

	public boolean getIsVerifyCode() {
		return isVerifyCode;
	}

	public void setIsVerifyCode(boolean isVerifyCode) {
		this.isVerifyCode = isVerifyCode;
	}

	public boolean getIsnew() {
		return isnew;
	}

	public void setIsnew(boolean isnew) {
		this.isnew = isnew;
	}

	@Override
	public String toString() {
		return "Sms [mobile=" + mobile + ", content=" + content
				+ ", userAgent=" + userAgent + ", clientIp=" + clientIp
				+ ", source=" + source + ", operator=" + operator + ", orgId="
				+ orgId + ", token=" + token + ", isenablebk=" + isenablebk
				+ ", isVerifyCode=" + isVerifyCode + ", isnew=" + isnew + "]";
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	 public Sms(String mobile, String content, String userAgent, String clientIp,
	            int source, String operator, String orgId,int type) {
	     this.mobile = mobile;
	     this.content = content;
	     this.userAgent = userAgent;
	     this.clientIp = clientIp;
	     this.source = source;
	     this.operator = operator;
	     this.orgId = orgId;
	     this.type = type;
	     
	    }
	
}

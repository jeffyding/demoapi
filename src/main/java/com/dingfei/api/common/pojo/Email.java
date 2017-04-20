package com.dingfei.api.common.pojo;

import java.io.Serializable;

/**
 * Email
 */
public class Email implements Serializable {
    private static final long serialVersionUID = -1322591352110601554L;

    private String subject;
    private String address;
    private String template;
    private String userAgent;
    private String clientIp;
    private int source;
    private String operator;
    private String orgId;

    /**
     * Constructor
     *
     * @param subject   String
     * @param address   String
     * @param template  String
     * @param userAgent String
     * @param clientIp  String
     * @param source    int
     * @param operator  String
     * @param orgId     String
     */
    public Email(String subject, String address, String template, String userAgent, String clientIp,
                 int source, String operator, String orgId) {
        this.subject = subject;
        this.address = address;
        this.template = template;
        this.userAgent = userAgent;
        this.clientIp = clientIp;
        this.source = source;
        this.operator = operator;
        this.orgId = orgId;
    }

    /**
     * getSubject
     *
     * @return String
     */
    public String getSubject() {
        return subject;
    }

    /**
     * setSubject
     *
     * @param subject String
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * getAddress
     *
     * @return String
     */
    public String getAddress() {
        return address;
    }

    /**
     * setAddress
     *
     * @param address String
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * getTemplate
     *
     * @return String
     */
    public String getTemplate() {
        return template;
    }

    /**
     * setTemplate
     *
     * @param template String
     */
    public void setTemplate(String template) {
        this.template = template;
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

    @Override
    public String toString() {
        return "Email [subject=" + this.subject + ", address=" + this.address + ", template=" + this.template + ", userAgent=" + this.userAgent
                + ", clientIp=" + this.clientIp + ", source=" + this.source + ", operator=" + this.operator + ", orgId=" + this.orgId + "]";
    }

}

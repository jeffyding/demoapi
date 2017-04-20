package com.dingfei.api.common;

/**
 * UserCacheInfo
 */
public class UserCacheInfo {
	private String orgId;
	private String orgName;
	private String userId;
	private String roleId;
	private String fullName;
	private String avatar;

	public UserCacheInfo(String orgId, String orgName, String userId, String roleId, String fullName, String avatar) {
		super();
		this.orgId = orgId;
		this.orgName = orgName;
		this.userId = userId;
		this.roleId = roleId;
		this.fullName = fullName;
		this.avatar = avatar;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

}

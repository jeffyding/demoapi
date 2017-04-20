package com.dingfei.api.util;

import org.springframework.http.MediaType;

/**
 * The interface Constants.
 */
public interface APIConstants {
	/**
	 * The Constant MEDIATYPE.
	 */
	public static final String MEDIATYPE = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8";

	/**
	 * The Constant HEADER_NAME_TOKEN.
	 */
	public static final String HEADER_NAME_TOKEN = "TOKEN";

	/**
	 * The Constant HEADER_NAME_SOURCE.
	 */
	public static final String HEADER_NAME_SOURCE = "SOURCE";

	/**
	 * The Constant HEADER_NAME_LOCATION.
	 */
	public static final String HEADER_NAME_LOCATION = "LOCATION";

	/**
	 * The Constant HEADER_NAME_USERAGENT.
	 */
	public static final String HEADER_NAME_USERAGENT = "USER-AGENT";

	/**
	 * The Constant RESOURCE_ACTION_GETSINGLE.
	 */
	public static final int RESOURCE_ACTION_GETSINGLE = 1;

	/**
	 * The Constant RESOURCE_ACTION_GETLIST.
	 */
	public static final int RESOURCE_ACTION_GETLIST = 2;

	/**
	 * The Constant RESOURCE_ACTION_CREATESINGLE.
	 */
	public static final int RESOURCE_ACTION_CREATESINGLE = 3;

	/**
	 * The Constant RESOURCE_ACTION_UPDATESINGLE.
	 */
	public static final int RESOURCE_ACTION_UPDATESINGLE = 5;

	/**
	 * The Constant RESOURCE_ACTION_DELETESINGLE.
	 */
	public static final int RESOURCE_ACTION_DELETESINGLE = 6;

	/**
	 * The Constant RESOURCE_ACTION_SEARCH.
	 */
	public static final int RESOURCE_ACTION_SEARCH = 7;

	/**
	 * The Constant RESOURCE_ACTION_DELETEMULTIPLE.
	 */
	public static final int RESOURCE_ACTION_DELETEMULTIPLE = 11;

	/**
	 * The Constant AUTH_TYPE_ORGADMIN.
	 */
	public static final int AUTH_TYPE_TOKEN = 1;

	/**
	 * The Constant AUTH_TYPE_AKSK.
	 */
	public static final int AUTH_TYPE_AKSK = 2;

	/**
	 * The Constant HEADER_ACCESSKEY.
	 */
	public static final String HEADER_ACCESSKEY = "accesskey";

	/**
	 * The Constant HEADER_NONCE.
	 */
	public static final String HEADER_NONCE = "nonce";

	/**
	 * The Constant HEADER_TIMESTAMP.
	 */
	public static final String HEADER_TIMESTAMP = "timestamp";

	/**
	 * The Constant HEADER_SIGNATURE.
	 */
	public static final String HEADER_SIGNATURE = "signature";

	/**
	 * The Constant TIMESTAMP_GAP.
	 */
	public static final long TIMESTAMP_GAP = 1800L;

	/**
	 * The Constant RESOURCE_OBJ_SIGN.
	 */
	public static final String RESOURCE_OBJ_SIGN = "sign";

	/**
	 * The Constant DEFAULT_LIMIT.
	 */
	public static final int DEFAULT_LIMIT = 20;

	/**
	 * The Constant PARAM_NAME_OFFSET.
	 */
	public static final String PARAM_NAME_OFFSET = "offset";

	/**
	 * The Constant PARAM_NAME_LIMIT.
	 */
	public static final String PARAM_NAME_LIMIT = "limit";

	/**
	 * The Constant PARAM_NAME_ORDERBY.
	 */
	public static final String PARAM_NAME_ORDERBY = "orderby";

	/**
	 * The Constant PARAM_NAME_DIRECTION.
	 */
	public static final String PARAM_NAME_DIRECTION = "direction";

	/**
	 * The Constant DEFAULT_TIMEOUNT.
	 */
	public static final int DEFAULT_TIMEOUNT = 8;

}

package com.dingfei.api.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.BeansException;
import org.springframework.data.domain.Page;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * BeanUtil
 * 
 */
public final class BeanUtil {
	private static final Logger LOGGER = Logger.getLogger(BeanUtil.class);
	private static final String[] EXCLUDES_FIELDS = { "" };
	private static ObjectMapper objectMapper;

	/**
	 * Hide Utility Class Constructor
	 */
	private BeanUtil() {
	}

	public static ObjectMapper getObjectMapper() {
		if (null == objectMapper) {
			objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		}
		return objectMapper;
	}

	/**
	 * copyPropertiesWithTrim
	 * 
	 * @param source Object
	 * @param target Object
	 * @param properties String[]
	 */
	public static void copyPropertiesWithTrim(Object source, Object target, String[] properties) {
		BeanWrapper src = new BeanWrapperImpl(source);
		BeanWrapper trg = new BeanWrapperImpl(target);
		Object value;
		for (String propertyName : properties) {
			try {
				value = src.getPropertyValue(propertyName);
				if (value != null && (value instanceof String)) {
					value = ((String) value).trim();
				} else if (value == null && src.getPropertyType(propertyName) == String.class) {
					value = "";
				}
				trg.setPropertyValue(propertyName, value);
			} catch (BeansException e) {
				// LOGGER.debug("bean copy fail:" + e.getMessage());
			}

		}
	}

	/**
	 * copyProperties
	 * 
	 * @param source Object
	 * @param target Object
	 * @param properties String[]
	 */
	public static void copyProperties(Object source, Object target, String[] properties) {
		BeanWrapper src = new BeanWrapperImpl(source);
		BeanWrapper trg = new BeanWrapperImpl(target);
		Object value;
		for (String propertyName : properties) {
			try {
				value = src.getPropertyValue(propertyName);
				if (value != null && (value instanceof String)) {
					value = ((String) value).trim();
				}
				trg.setPropertyValue(propertyName, value);
			} catch (BeansException e) {
			}
		}
	}

	/**
	 * copyProperties
	 * 
	 * @param source Object
	 * @param target Object
	 */
	public static void copyProperties(Object source, Object target) {
		String[] properties = getCopyFields(source.getClass(), target.getClass());
		copyProperties(source, target, properties);
	}

	/**
	 * getCopyFields
	 * 
	 * @param source Class
	 * @param target Class
	 * @return String[]
	 */
	public static String[] getCopyFields(Class<? extends Object> source, Class<? extends Object> target) {
		String[] properties;
		if (source.getDeclaredFields().length > target.getDeclaredFields().length) {
			properties = getInverseFields(target, EXCLUDES_FIELDS);
		} else {
			properties = getInverseFields(source, EXCLUDES_FIELDS);
		}
		return properties;
	}

	/**
	 * copyPropertiesWithTrim
	 * 
	 * @param source Object
	 * @param target Object
	 */
	public static void copyPropertiesWithTrim(Object source, Object target) {
		String[] properties = getCopyFields(source.getClass(), target.getClass());
		copyPropertiesWithTrim(source, target, properties);
	}

	/**
	 * getInverseFields
	 * 
	 * @param clz Class
	 * @param excludedFields String[]
	 * @return String[]
	 */
	public static String[] getInverseFields(Class<? extends Object> clz, String[] excludedFields) {
		Field[] declaredFields = clz.getDeclaredFields();
		List<String> fieldList = new ArrayList<String>();
		if (excludedFields == null || excludedFields.length == 0) {
			for (Field f : declaredFields) {
				fieldList.add(f.getName());
			}
		} else {
			List<String> excludedlist = Arrays.asList(excludedFields);
			String fieldName;
			for (Field f : declaredFields) {
				fieldName = f.getName();
				if (!excludedlist.contains(fieldName)) {
					fieldList.add(fieldName);
				}
			}
		}
		return fieldList.toArray(new String[fieldList.size()]);
	}

	/**
	 * getInverseFields
	 * 
	 * @param clz Class
	 * @param excludedFields String[]
	 * @return String[]
	 */
	public static String getFieldName(String propertyName, Class<Object> clz) {
		if (propertyName == null) {
			return null;
		}
		Field[] declaredFields = clz.getDeclaredFields();
		String fieldName = null;
		for (Field f : declaredFields) {
			fieldName = f.getName();
			if (fieldName.equalsIgnoreCase(propertyName)) {
				return fieldName;
			}
		}
		return null;
	}

	/**
	 * getInverseFields
	 * 
	 * @param beanObj Object
	 * @param excludedFields String[]
	 * @return String[]
	 */
	private static String[] getInverseFields(Object beanObj, String[] excludedFields) {
		return getInverseFields(beanObj.getClass(), excludedFields);
	}

	/**
	 * getIgnoreProperties
	 * 
	 * @param request HttpServletRequest
	 * @param clz Class
	 * @return String[]
	 */
	public static String[] getProperties(HttpServletRequest request, Class<Object> clz) {
		Map<String, Object> datas = getEntity(request);
		return getProperties(datas, clz);
	}

	/**
	 * getProperties
	 * 
	 * @param datas Map
	 * @param clz Class
	 * @return String[]
	 */
	private static String[] getProperties(Map<String, Object> datas, Class<Object> clz) {
		Field[] fields = clz.getDeclaredFields();
		List<String> propList = new ArrayList<String>();
		if (datas != null) {
			JsonProperty jsonProperty;
			JsonIgnore jsonIgnore;
			String name;
			for (Field field : fields) {
				jsonIgnore = field.getAnnotation(JsonIgnore.class);
				if (jsonIgnore == null) {
					jsonProperty = field.getAnnotation(JsonProperty.class);
					if (jsonProperty == null) {
						name = field.getName();
					} else {
						name = jsonProperty.value();
					}
					if (datas.get(name) != null) {
						propList.add(field.getName());
					}
				}
			}
		}
		return propList.toArray(new String[propList.size()]);
	}

	/**
	 * getEntity
	 * 
	 * @param request HttpServletRequest
	 * @return Map
	 */
	private static Map<String, Object> getEntity(HttpServletRequest request) {
		Map<String, Object> datas;
		try {
			datas = new ObjectMapper().readValue(getRequestBody(request), new TypeReference<Map<String, Object>>() {
			});
		} catch (IOException e) {
			datas = null;
			LOGGER.error("new ObjectMapper().readValue() error:", e);
		}
		return datas;
	}

	/**
	 * getRequestBody
	 * 
	 * @param request HttpServletRequest
	 * @return String
	 * @throws IOException
	 */
	public static String getRequestBody(HttpServletRequest request) throws IOException {
		String src;
		try {
			src = IOUtils.toString(request.getInputStream(), request.getCharacterEncoding());
		} catch (IllegalStateException ex) {
			src = IOUtils.toString(request.getReader());
			LOGGER.error("BeanHelper.getRequestBody() error:", ex);
		}
		return src;
	}

	/**
	 * bean2Json
	 * 
	 * @param bean Object
	 * @return String
	 */
	public static String bean2Json(Object bean) {
		String result = "";
		try {
			result = _bean2Json(bean);
		} catch (IOException e) {
			LOGGER.error("_bean2Json() error:", e);
		}
		return result;
	}

	private static String _bean2Json(Object bean) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		JsonGenerator jsonGenerator = objectMapper.getFactory().createGenerator(outputStream, JsonEncoding.UTF8);
		objectMapper.writeValue(jsonGenerator, bean);
		return outputStream.toString("UTF-8");
	}

	/**
	 * json2Bean
	 * 当bean存在泛型时，parameterizedTypeClass依次是泛型类 且rootClass必须有无参构造函数
	 * 
	 * @param json String
	 * @param clz Class
	 * @param <T> bean class
	 * @param parameterizedTypeClass Class[]
	 * @return bean class
	 */
	public static <T> T json2Bean(String json, Class<T> clz, Class<?>... parameterizedTypeClass) {
		T bean = null;
		if (StringUtils.isNotBlank(json)) {
			try {
				bean = _json2Bean(json, clz, parameterizedTypeClass);
			} catch (Exception e) {
				LOGGER.error("_json2Bean() error:", e);
			}
		}
		return bean;
	}

	private static <T> T _json2Bean(String json, Class<T> clz, Class<?>... parameterizedTypeClass) throws Exception {
		ObjectMapper objectMapper = getObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		int len = parameterizedTypeClass.length;
		if (len > 0) {

			JavaType javaType = objectMapper.getTypeFactory().constructParametricType(clz, parameterizedTypeClass);
			return objectMapper.readValue(json, javaType);
		} else {
			return objectMapper.readValue(json, clz);
		}
	}

	/**
	 * convertList
	 * 
	 * @param sourceList List
	 * @param sourceClass Class
	 * @param targetClass Class
	 * @param <S> source class
	 * @param <T> target class
	 * @return List
	 */
	public static <S, T> List<T> convertList(List<S> sourceList, Class<S> sourceClass, Class<T> targetClass) {
		List<T> datas = new ArrayList<T>();
		if (sourceList != null && !sourceList.isEmpty()) {
			T tmpBean;
			String[] properties = getCopyFields(sourceClass, targetClass);
			for (S entity : sourceList) {
				tmpBean = newInstance(targetClass);
				copyPropertiesWithTrim(entity, tmpBean, properties);
				datas.add(tmpBean);
			}
		}
		return datas;
	}

	/**
	 * Convert list.
	 * 
	 * @param <S> the generic type
	 * @param <T> the generic type
	 * @param sourceList the source list
	 * @param targetClass the target class
	 * @param properties the properties
	 * @return the list
	 */
	public static <S, T> List<T> convertList(List<S> sourceList, Class<T> targetClass, String[] properties) {
		List<T> datas = new ArrayList<T>();
		if (sourceList != null && !sourceList.isEmpty()) {
			T tmpBean;
			for (S entity : sourceList) {
				tmpBean = newInstance(targetClass);
				copyPropertiesWithTrim(entity, tmpBean, properties);
				datas.add(tmpBean);
			}
		}
		return datas;
	}

	private static <T> T newInstance(Class<T> targetClass) {
		T tmpBean;
		try {
			tmpBean = targetClass.newInstance();
		} catch (Exception ex) {
			LOGGER.error("targetClass.newInstance() error:", ex);
			throw new ApiException("global.ServiceInternalError");
		}
		return tmpBean;
	}

	/**
	 * convert2PagingList
	 * 
	 * @param listPage ListPage
	 * @param sourceClass Class
	 * @param targetClass Class
	 * @param <S> source class
	 * @param <T> target class
	 * @return PagingList
	 */
	public static <S, T> PagingList<T> convert2PagingListExt(Page<S> listPage, Class<S> sourceClass,
			Class<T> targetClass) {
		List<S> list = listPage.getContent();
		List<T> datas = convertList(list, sourceClass, targetClass);
		Paging paging = new Paging(listPage.getSize(), listPage.getNumber() * listPage.getSize(),
				listPage.getTotalPages(), (int) listPage.getTotalElements());
		return new PagingList<T>(datas, paging);
	}

	/**
	 * convert2PagingList
	 * 
	 * @param listPage ListPage
	 * @param sourceClass Class
	 * @param targetClass Class
	 * @param <S> source class
	 * @param <T> target class
	 * @return PagingList
	 */
	public static <T> PagingList<T> getPagingList(Page<T> listPage) {
		Paging paging = new Paging(listPage.getSize(), listPage.getNumber() * listPage.getSize(),
				listPage.getTotalPages(), (int) listPage.getTotalElements());
		return new PagingList<T>(listPage.getContent(), paging);
	}

	/**
	 * copyProperties
	 * 
	 * @param source Object
	 * @param target Object
	 * @param properties String[]
	 */
	public static void copyUpdatedProperties(Object source, Object target, String[] properties) {
		BeanWrapper src = new BeanWrapperImpl(source);
		BeanWrapper trg = new BeanWrapperImpl(target);
		Object value;
		for (String propertyName : properties) {
			try {
				value = src.getPropertyValue(propertyName);
				if (value != null && (value instanceof String)) {
					value = ((String) value).trim();
				}

				if (value != null) {
					trg.setPropertyValue(propertyName, value);
				}
			} catch (BeansException e) {
				// LOGGER.debug("bean copy fail:" + e.getMessage());
			}

		}
	}

	/**
	 * convertPaging2Map
	 * 
	 * @param page PagingList
	 * @param <T> Object
	 * @return Map
	 */
	public static <T> Map<String, Object> convertPaging2Map(PagingList<T> page) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("datas", page.getDatas());
		resultMap.put("paging", page.getPaging());
		return resultMap;
	}

	/**
	 * bean2csv
	 * 
	 * @param bean Object
	 * @return String
	 */
	public static String bean2csv(Object bean) {
		StringBuilder buffer = new StringBuilder(1024 * 8);
		Field[] declaredFields = bean.getClass().getDeclaredFields();
		Object value;
		BeanWrapper src = new BeanWrapperImpl(bean);
		for (Field f : declaredFields) {
			value = src.getPropertyValue(f.getName());
			buffer.append("\"").append(value).append("\",");
		}
		String result = "";
		if (buffer.length() > 0) {
			result = buffer.substring(0, buffer.length() - 1);
		}
		return result;
	}

	/**
	 * bean2csv
	 * 
	 * @param bean Object
	 * @param excludedFields String[]
	 * @return String
	 */
	public static String bean2csv(Object bean, String[] excludedFields) {
		StringBuilder buffer = new StringBuilder(1024 * 8);
		String[] properties = getInverseFields(bean, excludedFields);
		Object value;
		BeanWrapper src = new BeanWrapperImpl(bean);
		for (String propertyName : properties) {
			value = src.getPropertyValue(propertyName);
			buffer.append("\"").append(value).append("\",");
		}
		String result = "";
		if (buffer.length() > 0) {
			result = buffer.substring(0, buffer.length() - 1);
		}
		return result;
	}

	/**
	 * Convert a list of result to Json String with datas wrapper.
	 * 
	 * @param result List<?> list
	 * @return String of Json Result
	 */
	public static String convertToJsonData(List<?> listOfObject) {
		String resultJson = bean2Json(listOfObject);
		resultJson = "{\"datas\":" + resultJson + "}";
		return resultJson;
	}
}

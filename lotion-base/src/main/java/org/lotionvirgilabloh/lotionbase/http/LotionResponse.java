package org.lotionvirgilabloh.lotionbase.http;

import java.io.Serializable;
import java.util.*;

/**
 * 响应通用对象
 */
public final class LotionResponse implements Serializable{
	public final static String UTF8 = "UTF-8";

	public final static String DS_DATA = "data";
	/** 返回消息TAG */
	public final static String DS_MESSAGE = "message";
	/** SmartClient RestDataSource response TAG */
	public final static String DS_RESPONSE = "response";
	/** Response "status" 状态TAG */
	public final static String DS_STATUS = "status";
	/** 状态 */
	private int status;
	/** Response 数据容器 */
	private Object data;
	/** 消息 */
	private final List<String> message = new ArrayList<>();
	/** 自定义数据 */
	private Map<String, Object> extra = new LinkedHashMap<>();;

	public static final LotionResponse OK = new LotionResponse(LotionStatus.SUCCESS);
	public static final LotionResponse FAILURE = new LotionResponse(LotionStatus.FAILURE);
	public static final LotionResponse SUCCESS = OK;
	

	public LotionResponse() {
		status = 0;
	}

	public LotionResponse(Object data) {
		this();
		this.data = data;
	}

	public LotionResponse(int status, String message) {
		this.status = status;
		this.message.add(message);
	}

	public LotionResponse(LotionStatus status, String message) {
		this(status.id, message == null ? status.val : message);
	}

	public LotionResponse(LotionStatus status) {
		this(status.id, status.val);
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public LotionResponse setResponse(LotionStatus s, String mesg) {
		this.status = s.id;
		this.setMessage(mesg);
		return this;
	}
	
	public LotionResponse setResponse(int s, String mesg) {
		this.status = s;
		this.setMessage(mesg);
		return this;
	}
	
	public void setStatus(LotionStatus status) {
		this.status = status.id;
		this.message.add(status.val);
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public List<String> getMessage() {
		return message;
	}

	public void setMessage(String... mesg) {
		this.message.addAll(Arrays.asList(mesg));

	}

	public void setMessage(List<String> message) {
		if (message != null) {
			this.message.addAll(message);
		}
	}

	public Map<String, Object> getExtra() {
		return extra;
	}

	public void setExtra(Map<String, Object> extra) {
		if (extra != null) {
			this.extra.putAll(extra);
		}
	}

	public void setParam(String attr, Object value) {
		this.extra.put(attr, value);
	}
}

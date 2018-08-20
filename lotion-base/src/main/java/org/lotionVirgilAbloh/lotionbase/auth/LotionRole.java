package org.lotionvirgilabloh.lotionbase.auth;

import java.io.Serializable;

public class LotionRole implements Serializable {
	
	public final static String role_enable="1";
	public final static String role_disable="0";

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	protected String rid;
	protected String commonName;
	protected Integer type;
	protected String summary;
	protected String status;
	protected Long lastUpdate;

	public String getRid() {
		return rid;
	}

	public void setRid(String rid) {
		this.rid = rid.intern();
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getCommonName() {
		return commonName;
	}

	public void setCommonName(String commonName) {
		this.commonName = commonName.intern();
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public Long getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Long lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}

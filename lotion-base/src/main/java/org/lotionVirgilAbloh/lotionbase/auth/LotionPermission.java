package org.lotionVirgilAbloh.lotionbase.auth;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

public class LotionPermission implements Serializable {
	private static final long serialVersionUID = 111716895101604661L;


	
	protected Integer permId;
	protected String name;
	protected String resource;
	protected String type;
	protected Boolean anyRoles;
	protected Set<String> roles;
	protected Boolean authc;
	protected Boolean avaliable;
	protected Long lastUpdate;
	
	public Integer getPermId() {
		return permId;
	}

	public void setPermId(Integer permId) {
		this.permId = permId;
	}

	public Boolean isAvaliable() {
		return avaliable;
	}

	public void setAvaliable(Boolean avaliable) {
		this.avaliable = avaliable;
	}

	public Boolean isAnyRoles() {
		return anyRoles;
	}

	public void setAnyRoles(Boolean anyRoles) {
		this.anyRoles = anyRoles;
	}

	public Boolean isAuthc() {
		return authc;
	}

	public void setAuthc(Boolean authc) {
		this.authc = authc;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Set<String> getRoles() {
		return roles;
	}

	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}

	public Long getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Long lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	@Override
	public int hashCode() {
		return permId == null ? 0 : permId.hashCode();
	}



	@Override
	public String toString(){
		return toPerm();
	}
	
	public String toPerm() {
		// /mydemo/getVerifyCodeImage=anon
		// /main**=authc
		// /user/info**=authc
		// /admin/listUser**=authc,perms[admin:manage]
		if (!"URL".equalsIgnoreCase(this.type)) {
			return "";
		}
		StringBuilder b = new StringBuilder();
		b.append(resource).append('=');
		do {
			if (!authc) {
				b.append("anon");
				break;
			}

			b.append("authc");
			if (roles == null || roles.isEmpty()) {
				break;
			}
			b.append(',');
			// 当roles大于1,或标记录anyRoles时，使用anyRoles
			if (isAnyRoles() && roles.size()>1) {
				b.append("anyRoles[");
			} else {
				b.append("roles[");
			}
			for (String s : roles) {
				b.append(s).append(',');
			}
			b.setLength(b.length() - 1);
			b.append(']');
		} while (false);
		b.append('\n');
		return b.toString();
	}
}

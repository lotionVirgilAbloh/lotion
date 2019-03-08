package org.lotionvirgilabloh.lotionbase.http;

import javax.servlet.http.HttpServletResponse;

public enum LotionStatus {
	/** 成功 */
	SUCCESS(0,"操作成功！"),
	UNSET(1,"不确定的错误。"),
	/** 错误的数据 */
	FAILURE(-1,"错误数据，检查输入数据信息。"),
	/** 数据库异常 */
	DB_ERROR(-2,"数据库异常。"),
	/** 登录错误 */
	LOGIN_ERROR(-5,"登录用户名或密码错误，再试！"),
	/** 登录超过最大次数 */
	MAX_LOGIN_ATTEMPTS_EXCEEDED(-6,"登录超过最大次数。"),
	/** 需要用户登录认证 */
	LOGIN_REQUIRED(-7,"需要用户登录认证。"),
	/** 事务处理错误 */
	TRANSACTION_ERROR(-9,"事务处理错误，事务被撤销。"),
	/** 系统异常 */
	IO_ERROR(-10, "系统IO异常，读（写）文件错误。"),
	UNKNOWN_ERROR(-99,"系统未知错误。"),
	
	/** 文件未发现 */
	SC_FOUND(HttpServletResponse.SC_FOUND,"指定文件未发现"),
	SC_ACCEPTED(HttpServletResponse.SC_ACCEPTED,"已经接受。"),
	SC_BAD_GATEWAY(HttpServletResponse.SC_BAD_GATEWAY,"禁止操作。"),
	SC_CONFLICT(HttpServletResponse.SC_CONFLICT,"充突错误。"),
	SC_GATEWAY_TIMEOUT(HttpServletResponse.SC_GATEWAY_TIMEOUT,"错误请求。"),
	SC_METHOD_NOT_ALLOWED(HttpServletResponse.SC_METHOD_NOT_ALLOWED,"本方法被禁止请求。"),
	SC_NOT_ACCEPTABLE(HttpServletResponse.SC_NOT_ACCEPTABLE,"不可接受的请求。"),
	SC_BAD_REQUEST(HttpServletResponse.SC_BAD_REQUEST,"错误请求。"),
	SC_NOT_FOUND(HttpServletResponse.SC_NOT_FOUND,"未发现资源。"),
	SC_FORBIDDEN(HttpServletResponse.SC_FORBIDDEN,"禁止操作。"),
	SC_EXPECTATION_FAILED(HttpServletResponse.SC_EXPECTATION_FAILED,"因异常导致失败。"),
	;
	private LotionStatus(int stat, String val) {
		this.id = stat;
		this.val = val;
	}
	
	public final int id;
	public final String val;
}

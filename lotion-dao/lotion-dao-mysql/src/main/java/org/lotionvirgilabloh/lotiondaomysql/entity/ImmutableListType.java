package org.lotionvirgilabloh.lotiondaomysql.entity;

import com.alibaba.fastjson.JSON;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ImmutableListType<T> extends ImmutableType<T> {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 3275359207176183818L;
	static final Logger log = LoggerFactory.getLogger(ImmutableType.class);

	public ImmutableListType(Class<T> cls) {
		super(cls);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Object deepCopy(Object obj) throws HibernateException {
		if (obj == null) {
			return null;
		}
		if (!(obj instanceof List)) {
			throw new HibernateException("Not support.");
		}
		List<T> cc = new ArrayList<T>();
		try {
			List<T> orinal = (List<T>) obj;
			for (T v : orinal) {
				if (v == null) {
					cc.add(null);
				} else {
					T target = BeanUtils.instantiate(clazz);
					BeanUtils.copyProperties(v, target);
					cc.add(target);
				}
			}
		} catch (Exception e) {
			throw new HibernateException("Not support.",e);
		}
		return cc;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected Object parseObject(String nameVal) {
		if (nameVal == null //
				|| (nameVal = nameVal.trim()).length() == 0 //
				|| "null".equalsIgnoreCase(nameVal)) {
			return null;
		}
		final List<T> set = new ArrayList<T>();
		try {
			List<T> arr = null;
			if (nameVal.charAt(0) == '[') {
				arr = JSON.parseArray(nameVal, clazz);
				set.addAll(arr);
			} else if (clazz.equals(String.class)) {
				// 非标准json串
				Collection val = parseString(nameVal);
				set.addAll(val);
			}
		} catch (Exception e) {
			if (clazz.equals(String.class)) {
				Collection val = parseString(nameVal);
				set.addAll(val);
			}
			log.warn("Parse String:{} error, {}", nameVal, e);
		}
		return set.isEmpty() ? null : set;
	}

	/**
	 * Creates the custom object from the data returned by resultset
	 */
	public Object nullSafeGet(ResultSet rs, String[] names, Object owner) //
			throws HibernateException, SQLException {
		String nameVal = rs.getString(names[0]);
		return parseObject(nameVal);
	}

	@Override
	public Object nullSafeGet(ResultSet rs, String[] names,
			SharedSessionContractImplementor arg2, Object arg3)
			throws HibernateException, SQLException {
		String val = rs.getString(names[0]);
		return parseObject(val);
	}

	@Override
	public final Class<?> returnedClass() {
		return ArrayList.class;
	}
}

package org.lotionvirgilabloh.lotiondaomysql.entity;

import com.alibaba.fastjson.JSON;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.springframework.beans.BeanUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@SuppressWarnings("unchecked")
public class ImmutableSetType<T> extends ImmutableType<T> {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -2720589003425237119L;

	public ImmutableSetType(Class<T> cls) {
		super(cls);
	}

	@Override
	public Object deepCopy(Object obj) throws HibernateException {
		if (obj == null) {
			return null;
		}
		if (!(obj instanceof Set)) {
			throw new HibernateException("Not support.");
		}
		Collection<T> orinal = (Collection<T>) obj;
		Set<T> cc = new LinkedHashSet<T>(orinal.size());
		try {
			for (T v : orinal) {
				T dest = BeanUtils.instantiate(clazz);
				BeanUtils.copyProperties(v, dest);
				cc.add(dest);
			}
		} catch (Exception e) {
			throw new HibernateException("Not support.", e);
		}
		return cc;
	}

	/**
	 * Creates the custom object from the data returned by resultset
	 */
	public Object nullSafeGet(ResultSet rs, String[] names, Object owner) //
			throws HibernateException, SQLException {
		String nameVal = rs.getString(names[0]);
		return parseObject(nameVal);
	}

	@SuppressWarnings({ "rawtypes" })
	protected Object parseObject(String nameVal) {
		if (nameVal == null //
				|| (nameVal = nameVal.trim()).length() == 0 //
				|| "null".equalsIgnoreCase(nameVal))
			return null;

		final Set<T> set = new LinkedHashSet<T>();
		try {
			List<T> arr = null;
			if (nameVal.charAt(0) == '[') {
				arr = JSON.parseArray(nameVal, clazz);
				set.addAll(arr);
			} else if (clazz.equals(String.class)) {
				Collection val = parseString(nameVal);
				set.addAll(val);
			}
		} catch (Exception e) {
			if (clazz.equals(String.class)) {
				Collection val = parseString(nameVal);
				set.addAll(val);
			}
			log.warn("解析：{} 错误{}", nameVal, e);
		}
		return set.isEmpty() ? null : set;
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
		return LinkedHashSet.class;
	}
}

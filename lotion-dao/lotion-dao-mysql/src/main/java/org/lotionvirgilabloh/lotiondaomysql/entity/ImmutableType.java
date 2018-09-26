package org.lotionvirgilabloh.lotiondaomysql.entity;

import com.alibaba.fastjson.JSON;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ObjectUtils;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class ImmutableType<T> implements UserType, Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 3292832595033977155L;

	static final Logger log = LoggerFactory.getLogger(ImmutableType.class);

	final Class<T> clazz;

	public ImmutableType(Class<T> cls) {
		clazz = cls;
	}

	/**
	 * What column types to map,data type of the column
	 */
	@Override
	public int[] sqlTypes() {
		return new int[] { Types.VARCHAR };
	}

	/**
	 * Class details of object which is going to be used
	 */
	@Override
	public Class<?> returnedClass() {
		return clazz;
	}
	
	@Override
	public boolean equals(Object x, Object y) throws HibernateException {
		return ObjectUtils.nullSafeEquals(x, y);
	}
	
	@Override
	public int hashCode(Object x) throws HibernateException {
		return x == null ? 0 : x.hashCode();
	}

	/**
	 * Returns deep copy of object
	 */
	@Override
	public Object deepCopy(Object value) throws HibernateException {
		if (value == null)
			return null;
		try {
			Object dest = BeanUtils.instantiate(clazz);
			BeanUtils.copyProperties(value, dest);
			return dest;
		} catch (Exception e) {
			throw new HibernateException(e);
		}
	}

	@Override
	public boolean isMutable() {
		return false;
	}

	@Override
	public Serializable disassemble(Object value) throws HibernateException {
		Object deepCopy = deepCopy(value);

		if (!(deepCopy instanceof Serializable))
			return (Serializable) deepCopy;

		return null;
	}

	@Override
	public Object assemble(Serializable cached, Object owner)
			throws HibernateException {
		return deepCopy(cached);
	}

	@Override
	public Object replace(Object original, Object target, Object owner)
			throws HibernateException {
		return deepCopy(original);
	}

	/**
	 * role1:r2:aaa => {role1 r2 aa}
	 * 
	 * @param val
	 * @return
	 */
	protected static List<String> parseString(String val) {
		int len;
		if (val == null //
				|| (len = val.length()) == 0 //
				|| "null".equals(val)) {
			return null;
		}
		List<String> result = new ArrayList<String>();
		StringBuilder bb = new StringBuilder();
		for (int i = 0; i < len; i++) {
			char c = val.charAt(i);
			switch (c) {
			case ':':
			case ',':
			case ' ':
			case ';':
			case '[':
			case ']':
			case '|':
			case '\"':
				if (bb.length() > 0) {
					result.add(bb.toString());
					bb.setLength(0);
				}
				break;
			default:
				bb.append(c);
			}
		}
		if (bb.length()>0){
			result.add(bb.toString());
		}
		return result.isEmpty() ? null : result;
	}

	/**
	 * @since Hibernate 5.2.X
	 */
	@Override
	public Object nullSafeGet(ResultSet rs, String[] names,
			SharedSessionContractImplementor arg2, Object owner)
			throws HibernateException, SQLException {
		String val = rs.getString(names[0]);
		if (val == null //
				|| (val = val.trim()).length() == 0
				|| "null".equals(val)){
			return null;
		}
		try {
			return JSON.parseObject(val, clazz);
		} catch (Exception e) {
			log.warn("Parse Object error!{}", val, e);
		}
		return null;
	}

	/**
	 * @since Hibernate 5.2.X
	 */
	@Override
	public void nullSafeSet(PreparedStatement pstmt,//
			Object value, //
			int idx,//
			SharedSessionContractImplementor sess) throws HibernateException,
			SQLException {
		if (value == null //
				|| "null".equals(value)) {
			pstmt.setNull(idx, Types.VARCHAR);
		}
		pstmt.setString(idx, JSON.toJSONString(value));
	}
}
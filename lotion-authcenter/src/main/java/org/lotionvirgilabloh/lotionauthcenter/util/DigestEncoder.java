package org.lotionvirgilabloh.lotionauthcenter.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public final class DigestEncoder {
	static Logger logger = LoggerFactory.getLogger(DigestEncoder.class);
	
	static final String ALGM_MD5 = "MD5";
	static final String ALGM_SHA1 = "SHA1";
	static final char[] HEX = { //
			'0', '1', '2', '3', '4', '5', '6', '7',//
			'8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	public static String digestEncode(String algorithm, byte input[]) {
		if (input == null) {
			return null;
		}
		try {
			MessageDigest mesgDigest = MessageDigest.getInstance(algorithm);
			mesgDigest.update(input);
			return formatHEX(mesgDigest.digest());
		} catch (NoSuchAlgorithmException e) {
			logger.error("摘要编码错误。{}",algorithm, e);
		}
		return null;
	}

	public static String formatHEX(byte b[]) {
		StringBuilder cb = new StringBuilder(b.length * 2);
		for (int i = 0; i < b.length; i++) {
			cb.append(HEX[(b[i] & 0xF0) >> 4]);
			cb.append(HEX[b[i] & 0xf]);
		}
		return cb.toString();
	}
	
	public static String encodePassword(String username, String passwd) {
		byte []b = (username + ":" + passwd).getBytes();
		return digestEncode(ALGM_MD5, b);
	}
	
	public static void main(String []args){
		BCryptPasswordEncoder bce =new BCryptPasswordEncoder(6);
		System.out.println(bce.encode("sb"));
//		System.out.println(encodePassword("qz","sb"));
	}
}

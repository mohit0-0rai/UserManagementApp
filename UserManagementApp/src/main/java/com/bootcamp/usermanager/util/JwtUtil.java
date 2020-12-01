package com.bootcamp.usermanager.util;

import java.security.Key;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtUtil {

	public static String generateToken(Integer userId) throws Exception {
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);

		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(Constant.APP_SECRET_KEY);
		Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

		JwtBuilder builder = Jwts.builder().setId(String.valueOf(userId)).setIssuedAt(now).signWith(signatureAlgorithm,
				signingKey);

		long expMillis = nowMillis + Constant.JWT_TOKEN_EXPIRATION;
		Date exp = new Date(expMillis);
		builder.setExpiration(exp);

		return builder.compact();
	}

	public static Claims validateToken(String token) throws Exception {
		Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(Constant.APP_SECRET_KEY))
				.parseClaimsJws(token).getBody();

		return claims;
	}

	public static Integer getUserId(String header) throws Exception {
		String token = header.substring(7);
		
		return Integer.parseInt(validateToken(token).getId());
	}
}

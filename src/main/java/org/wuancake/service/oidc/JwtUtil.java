package org.wuancake.service.oidc;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.util.Base64;

/**
 * JWT工具类
 */
@Service
public class JwtUtil {
    @Value("${WUAN_POINT.JWT_SECRET}")
    private String key;

    /**
     * 解密jwt
     *
     * @param jwt
     * @return
     * @throws Exception
     */
    public Claims parseJWT(String jwt) throws Exception {
        return Jwts.parser().setSigningKey(this.generalKey()).parseClaimsJws(jwt).getBody();
    }

    /**
     * 由字符串生成加密key,用jdk8的Base64类进行解码与编码
     *
     * @return
     * @throws UnsupportedEncodingException
     */
    public SecretKey generalKey() throws UnsupportedEncodingException {
        String stringKey = Base64.getEncoder().encodeToString(key.getBytes("utf-8"));
        byte[] encodedKey = Base64.getDecoder().decode(stringKey);
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }
}

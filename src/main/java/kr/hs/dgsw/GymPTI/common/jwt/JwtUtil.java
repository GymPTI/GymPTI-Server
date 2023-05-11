package kr.hs.dgsw.GymPTI.common.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import kr.hs.dgsw.GymPTI.common.config.AppProperty;
import kr.hs.dgsw.GymPTI.common.error.ErrorCode;
import kr.hs.dgsw.GymPTI.common.exception.CustomException;
import kr.hs.dgsw.GymPTI.domain.user.entity.User;
import kr.hs.dgsw.GymPTI.domain.user.entity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtUtil {

    private static final Long ACCESS_TOKEN_EXPIRE = 1000L * 3600 * 3;  //3시간
    private static final Long REFRESH_TOKEN_EXPIRE = 1000L * 3600 * 6;  //6시간

    private final AppProperty appProperty;
    private final UserRepository userRepository;

    private Key getSignKey(String secretKey) {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateJwtToken(String email, Long expDate, TokenType tokenType) {

        Claims claims = Jwts.claims();
        claims.put("type", tokenType);
        claims.put("email", email);

        Date now = new Date();

        String secretKey = checkTokenType(tokenType);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expDate))
                .signWith(getSignKey(secretKey), SignatureAlgorithm.HS256)
                .compact();
    }

    public User verifyToken(String token) {
        String email = parseToken(token, TokenType.ACCESS).get("email").toString();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
    }

    private Claims parseToken(String token, TokenType tokenType) {

        String secretKey = checkTokenType(tokenType);

        if (token.isEmpty()) {
            throw new CustomException(ErrorCode.TOKEN_NOT_PROVIDED);
        }
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSignKey(secretKey))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new CustomException(ErrorCode.TOKEN_EXPIRED);
        } catch (IllegalArgumentException e) {
            throw new CustomException(ErrorCode.TOKEN_NOT_PROVIDED);
        } catch (UnsupportedJwtException | MalformedJwtException e) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }
    }

    public String generateAccessToken(String email) {
        return generateJwtToken(email, ACCESS_TOKEN_EXPIRE, TokenType.ACCESS);
    }

    public String generateRefreshToken(String email) {
        return generateJwtToken(email, REFRESH_TOKEN_EXPIRE, TokenType.REFRESH);
    }

    public String checkTokenType(TokenType tokenType) {
        if (tokenType == TokenType.ACCESS) {
            return appProperty.getAccessSecretKey();
        }
        else {
            return appProperty.getRefreshSecretKey();
        }
    }

}

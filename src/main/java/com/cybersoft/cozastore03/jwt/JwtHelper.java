package com.cybersoft.cozastore03.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Calendar;
import java.util.Date;

@Component
public class JwtHelper {

//    Giúp lấy giá trị khai báo bên file application
//    Lưu ý @Value chỉ hoạt động khi class được đưa lên IOC
    @Value("${token.key}")
    private String strKey;

    private int expriredTime = 8 * 60 * 60 * 1000;

    public String generateToken(String data){
        //Chuyển key đã lưu trữ từ dạng base64 về SecrectKey
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(strKey));
//        long futureMilis = Calendar.getInstance().getTimeInMillis();
        Date date = new Date();
        long futureMilis = date.getTime() + expriredTime;
        Date futureDate = new Date(futureMilis);

        return Jwts.builder().subject(data).expiration(futureDate).signWith(key).compact();
    }

    public String decodeToken(String token){
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(strKey));
        String data = null;
        try{
            data = Jwts.parser().verifyWith(key).build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();
        }catch (Exception e){
            System.out.println("Lỗi parser token " + e.getMessage());
        }

        return data;
    }

}

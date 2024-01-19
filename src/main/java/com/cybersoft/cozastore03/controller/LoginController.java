package com.cybersoft.cozastore03.controller;

import com.cybersoft.cozastore03.jwt.JwtHelper;
import com.cybersoft.cozastore03.payload.response.BaseResponse;
import com.google.gson.Gson;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;

@CrossOrigin
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtHelper jwtHelper;

    private Gson gson = new Gson();
    private Logger logger = LoggerFactory.getLogger(LoginController.class);

    @PostMapping("")
    public ResponseEntity<?> login(){
//        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        //Tạo key dùng để mã hóa cho Token
//        SecretKey key = Jwts.SIG.HS256.key().build();
//        String strKey = Encoders.BASE64.encode(key.getEncoded());
//        System.out.println("Kiemtra " + strKey);
        String data = jwtHelper.decodeToken("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJoZWxsbyBqd3QifQ.NleLgNWnFE6lsU3xYYbQ6oxPTvJ0oif5F1cS095g7AY");
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestParam String username, @RequestParam String password){
        logger.info("Request Username : " + username);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username,password);

        Authentication authentication = authenticationManager.authenticate(token);
        String json = gson.toJson(authentication.getAuthorities());
        String jwtToken = jwtHelper.generateToken(json);

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setData(jwtToken);

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

}

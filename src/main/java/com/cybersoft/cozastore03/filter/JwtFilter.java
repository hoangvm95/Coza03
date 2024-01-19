package com.cybersoft.cozastore03.filter;

import com.cybersoft.cozastore03.jwt.JwtHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Type;
import java.security.Security;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//OncePerRequestFilter : Kích hoạt khi người dùng gọi bất link nào
@Service
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtHelper jwtHelper;

    private Gson gson = new Gson();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//    Lấy gía trị token mà client truyền trên Header có key là Authorization
        System.out.println("kiemtra filter");
        String bearerToken = request.getHeader("Authorization");
        Optional<String> tokenOptional = Optional.ofNullable(bearerToken);
        /**
         * Bước 1 : Lấy token từ giá trị của Header
         * Bước 2 : Cắt chuỗi bỏ chữa Bearer đi để lấy được token
         * Bước 3 : Giải mã token ra.
         * Bước 4 : Nếu giải mã thành công tạo ra Security Context Holder
         */
//              tokenOptional.filter(data -> data.contains("Bearer ")
        if(tokenOptional.isPresent()){
//            tokenOptional.stream().map(data -> data.substring(7));
            String token = tokenOptional.get().substring(7);
            if(!token.isEmpty()){
                String data = jwtHelper.decodeToken(token);
                //tạo ra custom type để Gson hỗ trợ parse json kiểu List
                Type listType = new TypeToken<List<SimpleGrantedAuthority>>() {}.getType();

                List<GrantedAuthority> listRoles = gson.fromJson(data,listType);
                System.out.println("kiemtra role " + data + " size List role " + listRoles.size() );

                if(data != null){
                    //Tạo ContextHolder để bypass qua các filter của Security
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken("","",listRoles);
                    SecurityContext securityContext = SecurityContextHolder.getContext();
                    securityContext.setAuthentication(authenticationToken);
                }
            }
        }


        filterChain.doFilter(request,response);
    }
}

package com.cybersoft.cozastore03.security;

import com.cybersoft.cozastore03.entity.UserEntity;
import com.cybersoft.cozastore03.service.LoginService;
import com.cybersoft.cozastore03.service.imp.LoginServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomAuthenProvider implements AuthenticationProvider {

    @Autowired
//    @Qualifier("tenbean")
    private LoginServiceImp loginServiceImp; // LoginServiceImp loginServiceImp = new LoginService()

    //Nếu trả ra một authentication là đăng nhập thành công ngược lại sẽ là thất bại
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();
        UserEntity userEntity = loginServiceImp.checkLogin(username,password);
        if( userEntity != null){
            //Tạo một list nhận vào danh sách quyền theo chuẩn của Security
            List<GrantedAuthority> listRoles = new ArrayList<>();
            //Tạo ra một quyền và gán tên quyền truy vấn được từ database để add vào list role ở trên
            SimpleGrantedAuthority role = new SimpleGrantedAuthority(userEntity.getRole().getName());
            listRoles.add(role);

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken("","",listRoles);

            return authenticationToken;
        }

        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}

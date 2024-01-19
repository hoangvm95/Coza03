package com.cybersoft.cozastore03.service;

import com.cybersoft.cozastore03.entity.UserEntity;
import com.cybersoft.cozastore03.repository.UserRepository;
import com.cybersoft.cozastore03.service.imp.LoginServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService implements LoginServiceImp {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserEntity checkLogin(String username, String password) {
        UserEntity userEntity = userRepository.findByEmail(username);
        //nếu truy vấn có dữ liệu tức user tồn tại
        if(userEntity != null){
            //kiểm tra password trong database có match với password user truyền lên hay không
            if(passwordEncoder.matches(password,userEntity.getPassword())){
                return userEntity;
            }
        }

        return null;
    }


}

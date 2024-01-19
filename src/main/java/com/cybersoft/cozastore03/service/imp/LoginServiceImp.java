package com.cybersoft.cozastore03.service.imp;

import com.cybersoft.cozastore03.entity.UserEntity;

public interface LoginServiceImp {
    UserEntity checkLogin(String username, String password);
}

package com.Service;

import com.Dao.Entity.Users;

public interface UsersService {
    //用户名密码是否匹配
    Users isLoginInfoValid(String username, String pw);

    //创建新用户
    Users createNewUser(String username, String pw);

}

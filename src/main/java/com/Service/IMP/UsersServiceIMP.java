package com.Service.IMP;

import com.Dao.Entity.Users;
import com.Dao.Repository.UsersRepository;
import com.Service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service("usersService")
public class UsersServiceIMP implements UsersService
{
    private UsersRepository userRepository;

    @Autowired
    public UsersServiceIMP(UsersRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    @Override
    public Users isLoginInfoValid(String username, String pw) //登录信息是否有效
    {
        Users user = userRepository.findUserByUsernameAndPw(username, DigestUtils.md5DigestAsHex(pw.getBytes()));
        return user;
    }

    private Users createTemporaryUser(String username, String pw)  //创建临时用户
    {
        Users user = new Users();
        user.setUsername(username);
        user.setPassword(DigestUtils.md5DigestAsHex(pw.getBytes()));
        return user;
    }

    @Override
    public Users createNewUser(String username, String pw)
    {

        //规定：返回结果为null，为发生未知错误；返回结果中，id为0，为用户名重复

        Users temporaryUser = createTemporaryUser(username, pw);
        Users result;
        try
        {
            result = userRepository.save(temporaryUser);
        }
        catch (DataIntegrityViolationException e)
        {
            result = onDuplicateError();
        }
        catch (Exception e)
        {
            result = null;
        }
        return result;
    }

    private Users onDuplicateError()  //重复的错误
    {
        Users user = new Users();
        user.setUid(0);
        return user;
    }

}

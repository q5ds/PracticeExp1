package com.Controller;

import com.Dao.Entity.Users;
import com.Domain.LoginRequest;
import com.Domain.Register.RegisterRequest;
import com.Domain.Register.RegisterResult;
import com.Service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;

@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600)
@RestController
public class UsersController {
    private UsersService userService;

    @Autowired
    public UsersController(UsersService userService)
    {
        this.userService = userService;
    }

    @PostMapping("data/register")
    public RegisterResult register(@RequestBody RegisterRequest registerRequest, HttpSession httpSession)
    {
        System.out.println(registerRequest.getUsername() + " " + registerRequest.getPassword()+" ");
        Users result = userService.createNewUser(registerRequest.getUsername(), registerRequest.getPassword());
        if(result == null)
        {

            return RegisterResult.ERROR;
        }
        else if(result.getUid() == 0)
        {
            System.out.println(result.getUid());
            return RegisterResult.DUPLICATE;
        }
        else
        {

            httpSession.setAttribute("id", result.getUsername());
            return RegisterResult.SUCCESS;
        }
    }

    @GetMapping("data/identity")
    public String identity(HttpSession httpSession)
    {

        String username = (String) httpSession.getAttribute("id");
        if(username == null)
            return "";
        else
            return username;
    }

    @PostMapping("data/logout")
    public void logout(HttpSession httpSession)
    {

        httpSession.removeAttribute("id");
    }

    @PostMapping("data/login")
    public boolean login(@RequestBody LoginRequest loginRequest, HttpSession httpSession)
    {
        System.out.println("method login triggered: " + loginRequest.getUsername() + " " + loginRequest.getPassword());
        Users res = userService.isLoginInfoValid(loginRequest.getUsername(), loginRequest.getPassword());
        if(res != null && res.getUid() > 0)
        {
            httpSession.setAttribute("id", res.getUsername());
            return true;
        }
        else
            return false;
    }

    @GetMapping("data/test")
    public String test(HttpSession httpSession)
    {
        if(httpSession.getAttribute("test") != null)
            System.out.println(httpSession.getAttribute("test"));
        else
            System.out.println("null");
        httpSession.setAttribute("test", 1);
        return "hello";
    }


}

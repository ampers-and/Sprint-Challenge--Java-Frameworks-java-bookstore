package com.lambdaschool.bookstore.models;

import com.lambdaschool.bookstore.logging.Loggable;

@Loggable
public class UserLogin
{
    private String username;
    private String password;

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }
}

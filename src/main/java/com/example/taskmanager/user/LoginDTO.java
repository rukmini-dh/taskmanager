package com.example.taskmanager.user;

public class LoginDTO {

    private String registrationNumber;
    private String userName;
    public String password;

    //default constructor
    public  LoginDTO(){}
    // constructor
    public LoginDTO(String registrationNumber,String password,String userName){
        this.registrationNumber=registrationNumber;
        this.password=password; 
        this.userName= userName; 
    }

    public void setUserName(String userName){this.userName=userName;}
    public String getUserName(){return userName;};
    //setters
    public void getRegistrationNumber(String registrationNumber){this.registrationNumber=registrationNumber;}
    public void setPassword(String password){this.password=password;}

    //getters
    public String getRegistrationNumber(){return registrationNumber;}
    public String getPassword(){return password;}

}

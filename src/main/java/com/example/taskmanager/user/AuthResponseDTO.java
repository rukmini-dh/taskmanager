package com.example.taskmanager.user;

 public class AuthResponseDTO {

    private String token;
    private String message;
    private Role role;
    private String userName;
    private boolean success;
    public AuthResponseDTO(){}// default constructor

    //contructor
    public AuthResponseDTO(String token,Role role,boolean success,String message,String userName){
        this.token=token;
        this.role=role;
        this.success=success;
        this.message=message;
        this.userName=userName;
    }
    public AuthResponseDTO(boolean success,String message ,Role role,String userName,String token)
    {
        this.message= message;
        this.success=success;
        this.role=role;
        this.userName=userName;
        this.token= token;
       
    }
    public void setUserName(String userName){this.userName=userName;}
    public String getUserName(){return userName;};
    //getters
    public String  getToken(){return token;}
    public Role getRole(){return role;}
    public String getMessage(){return message;}
    public boolean getSuccess(){return success;}

    //setters
    public void  setToken(String token){this.token=token;}
    public void setRole(Role role){this.role=role;}
    public void setSuccess(boolean success){this.success=success;}
    public void setMessage(String message){this.message=message; }

}

    

package com.example.taskmanager.user;

public class RegistrationDTO {

    private String registrationNumber;

    private String firstName;

    private String lastName;

    private String password;
    private String userName;

    // default constructor
    public RegistrationDTO(){}
    // constructor
    public RegistrationDTO(String  registrationNumber,String firstName,String lastName,String password,String userName){
        this.registrationNumber=registrationNumber;
        this.firstName=firstName;
        this.lastName=lastName;
        this.password=password;
        this.userName=userName;
    } 
    public String getRegistrationNumber() {
        return registrationNumber;
    }
    public String getLastName() {
        return lastName;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setUserName(String userName){this.userName=userName;}
    public String getUserName(){return userName;}
    public String getPassword(){return password;}
   public void setRegistrationNumber(String registrationNumber) {
       this.registrationNumber = registrationNumber;
   }
   public void setLastName(String lastName) {
       this.lastName = lastName;
   }
   public void setFirstName(String firstName) {
       this.firstName = firstName;}
   public void setPassword( String password){this.password=password;}   
  

}
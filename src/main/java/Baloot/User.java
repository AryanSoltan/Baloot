package Baloot;

public class User {

    private String userName;
    private String password;
    private String email;
    private String birthDate;
    private String address;
    private double credit;
    User(String inputUserName, String inputPassword, String inputEmail,
         String inputBirthDate, String inputAddress, double inputCredit)
    {
        userName = inputUserName;
        password = inputPassword;
        email = inputEmail;
        birthDate = inputBirthDate;
        address = inputAddress;
        credit = inputCredit;
    }
}

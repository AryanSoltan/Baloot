package Baloot.DTOObjects;

import Baloot.DiscountCode;
import Baloot.User;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

import java.util.HashSet;
import java.util.Set;

public class UserDTO {


    private String username;

    private String email;
    private String birthDate;

    private String address;

    private double credit;



    public UserDTO(User user)
    {
        this.username = user.getName();
        this.address = user.getAddress();
        this.email = user.getEmail();
        this.birthDate = user.getBirthDate();
        this.credit = user.getCredit();

    }

    public String getName() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getAddress() {
        return address;
    }

    public double getCredit() {
        return credit;
    }



}

package Baloot.Exception;

import jakarta.persistence.criteria.CriteriaBuilder;

public class IncorrectPassword extends Exception{
    public IncorrectPassword(){super("Error!: password is not correct");}
}

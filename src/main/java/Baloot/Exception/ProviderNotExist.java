package Baloot.Exception;

public class ProviderNotExist extends Exception{
    public ProviderNotExist(int providerId){super("Error!: provider " + providerId + " doesn't exist");}
}

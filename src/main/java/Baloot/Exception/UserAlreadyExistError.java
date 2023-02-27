package Baloot.Exception;

public class UserAlreadyExistError extends Exception{

        public UserAlreadyExistError(String nameUser) {
            super("Error!: User with username " + nameUser + "already exist");
        }
}

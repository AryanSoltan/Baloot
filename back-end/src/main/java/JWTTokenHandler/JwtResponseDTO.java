package JWTTokenHandler;

import controllers.baloot.ReposnsePackage.Response;

public class JwtResponseDTO {


    private String userEmail;
    private String jwtToken;

    public JwtResponseDTO(String userEmail, String jwtToken)
    {
        this.userEmail = userEmail;
        this.jwtToken = jwtToken;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }
}

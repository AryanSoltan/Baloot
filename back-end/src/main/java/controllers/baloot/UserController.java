package controllers.baloot;

import Baloot.DTOObjects.*;
import Baloot.Exception.EmailAlreadyExist;
import Baloot.Exception.UserAlreadyExistError;
import Repository.BalootServerRepo;
import Baloot.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.beans.factory.annotation.Autowired;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import controllers.baloot.ReposnsePackage.Response;
import org.springframework.web.server.ResponseStatusException;
import JWTTokenHandler.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.springframework.security.authentication.AuthenticationManager;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Objects;

import static com.google.common.io.Resources.getResource;


@RestController
@CrossOrigin(origins = "*",allowedHeaders = "*")
@ComponentScan(basePackages ={"JWTTokenHandler"})
public class UserController {

//    @Autowired
//    private AuthenticationManager authenticationManager;

//    @Autowired
//    private JwtTokenUtil jwtTokenUtil;

    static String CLIENT_ID = "19aafd1365dd0bff246e";
    static String CLIENT_SECRET = "1f87cf5a63754023e8d925919c8954bb89c1eb2d";

    @RequestMapping(value="/login",method = RequestMethod.POST)
    public Response logIn (@RequestBody String userLoginInfo) throws Exception{

        try{
            var loginInfo = new ObjectMapper().readTree(userLoginInfo);
            String email= loginInfo.get("email").asText();
            String password = loginInfo.get("password").asText();

            BalootServerRepo.getInstance().logIn(email,password);
            String jwtToken = JwtTokenUtil.generateToken(email);
            return new Response(HttpStatus.OK.value(), "logged in", jwtToken);

        }
        catch( JsonProcessingException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        catch (Exception e)
        {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"user not found");
        }
    }

    @RequestMapping(value="/usernameForLogin",method = RequestMethod.POST)
    public Response getUserName (@RequestBody String userLoginInfo) throws Exception{
        System.out.println("in getting username");

        var emailInfo = new ObjectMapper().readTree(userLoginInfo);
        String email = emailInfo.get("email").asText();


        try{
            User user = BalootServerRepo.getInstance().getUserByEmail(email);
            return new Response(HttpStatus.OK.value(), "userame sent", user.getName());
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
        }
    }

    @RequestMapping(value="/isLogin", method = RequestMethod.POST)
    public Response isLogIn (@RequestBody String userLoginInfo) throws Exception{
        var loginInfo = new ObjectMapper().readTree(userLoginInfo);
        String username= loginInfo.get("username").asText();
        String password = loginInfo.get("password").asText();
        try{
            boolean isLogIn = BalootServerRepo.getInstance().userIsLoggedIn(username, password);
            return new Response(HttpStatus.OK.value(), "logged in",isLogIn);
        }
        catch (Exception e)
        {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"user not found");
        }
    }

    @RequestMapping(value="/logout",method = RequestMethod.POST)
    public Response logOut (@RequestBody String userLoginInfo) throws Exception{
        var loginInfo = new ObjectMapper().readTree(userLoginInfo);
        String username= loginInfo.get("username").asText();
        String password = loginInfo.get("password").asText();
//        try {
//            if (BalootServerRepo.getInstance().userIsLoggedIn(username, password) == false) {
//                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"user not logged in");
//
//            } else {
//                BalootServerRepo.getInstance().logOut(username, password);
                return new Response(HttpStatus.OK.value(),"logged out",null);
//            }
        }

//        catch (Exception e)
//        {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
//        }
//    }
//
    @RequestMapping(value="/users/{id}/buyList",method = RequestMethod.GET)
    public Response getBuyList (@RequestHeader(value = "Authorization") String authJWT, @PathVariable(value="id") String username ) throws Exception{
        System.out.println("in back");

        if(authJWT == null || !JwtTokenUtil.validateTokenSigneture(authJWT) || JwtTokenUtil.isTokenExpired(authJWT)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "jwt have problem");
        }

        String userEmail = JwtTokenUtil.extractUserEmail(authJWT);
        UserDTO user = BalootServerRepo.getInstance().getUserById(username);
        if(!Objects.equals(user.getEmail(), userEmail))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "jwt have problem");

        try{
            BuyListDTO buylist = BalootServerRepo.getInstance().getUserBuyList(username);
            return new Response(HttpStatus.OK.value(), "buylist sent",buylist);
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
        }
    }
//
//
    @RequestMapping(value="/users/{id}",method = RequestMethod.GET)
    public Response getUser (@RequestHeader(value = "Authorization") String authJWT, @PathVariable(value="id") String username ) throws Exception{
        if(authJWT == null || !JwtTokenUtil.validateTokenSigneture(authJWT) || JwtTokenUtil.isTokenExpired(authJWT)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "jwt have problem");
        }

        String userEmail = JwtTokenUtil.extractUserEmail(authJWT);
        UserDTO user = BalootServerRepo.getInstance().getUserById(username);
        if(!Objects.equals(user.getEmail(), userEmail))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "jwt have problem");
        try{
            return new Response(HttpStatus.OK.value(), "user sent",BalootServerRepo.getInstance().getUserById(username));
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
        }
    }

    @RequestMapping(value="/getUserInfo",method = RequestMethod.POST)
    public Response getUser (@RequestBody String jwtTokenInfo) throws Exception{
        System.out.println("ENTERED");
        var jwtInfo = new ObjectMapper().readTree(jwtTokenInfo);
        String token = jwtInfo.get("token").asText();
        System.out.println("TOKEN is" + token);
        System.out.println(BalootServerRepo.getInstance().getUserById(JwtTokenUtil.extractUserEmail(token)));
        return new Response(HttpStatus.OK.value(), "user sent",BalootServerRepo.getInstance().getUserById(JwtTokenUtil.extractUserEmail(token)));
    }

    @RequestMapping(value="/oauth-github",method = RequestMethod.POST)
    public Response oAuth (@RequestBody String reqInfo) throws Exception{
        System.out.println("Enter Oauth");
        var codeResp = new ObjectMapper().readTree(reqInfo);
        String code = codeResp.get("code").asText();
        String urlGithub = "https://github.com/login/oauth/access_token?client_id="
                + CLIENT_ID + "&client_secret=" + CLIENT_SECRET + "&code=" + code;

        String accessToken = getAccessToken(urlGithub);
        JsonObject userInfo = getUserInfoGithub(accessToken);
        String userName = userInfo.get("login").getAsString();
        System.out.println(userName);
//        String email = "git-temp@gmail.com";
//        if(!userInfo.get("email").isJsonNull())
//            email = userInfo.get("email").getAsString();
        String email = userName;
        if(!BalootServerRepo.getInstance().isUserExist(userName))
        {
            User newUser = new User(userName, accessToken, email, "1.1.2000", "", (double)0);
            BalootServerRepo.getInstance().addUser(newUser);
        }
        return new Response(HttpStatus.OK.value(), "user sent", JwtTokenUtil.generateToken(userName));
    }

    private JsonObject getUserInfoGithub(String accessToken) {
        try {
            URL url = new URL("https://api.github.com/user");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/vnd.github+json");
            conn.setRequestProperty("Authorization", "Bearer " + accessToken);
            conn.setRequestProperty("X-GitHub-Api-Version", "2022-11-28");

            int responseCode = conn.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            System.out.println(response);

            in.close();
            conn.disconnect();
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(String.valueOf(response), JsonObject.class);
            return jsonObject;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private String getAccessToken(String urlGithub)
    {
        try {
            URL url = new URL(urlGithub);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/vnd.github+json");


            int responseCode = conn.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            conn.disconnect();
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(String.valueOf(response), JsonObject.class);
            return jsonObject.get("access_token").getAsString();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "";
    }


    //
    @RequestMapping(value="/users/{id}/buyList/applyDiscount",method = RequestMethod.POST)
    public Response applyDiscount (@RequestHeader(value = "Authorization") String authJWT, @RequestBody String reqInfo,@PathVariable(value="id") String username ) throws Exception{

        if(authJWT == null || !JwtTokenUtil.validateTokenSigneture(authJWT) || JwtTokenUtil.isTokenExpired(authJWT)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "jwt have problem");
        }

        String userEmail = JwtTokenUtil.extractUserEmail(authJWT);
        UserDTO user = BalootServerRepo.getInstance().getUserById(username);
        if(!Objects.equals(user.getEmail(), userEmail))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "jwt have problem");

        try{


            var info = new ObjectMapper().readTree(reqInfo);
            String code = info.get("discountCode").asText();
            //BalootServerRepo.getInstance().applyDiscountCode(username,code);
            return new Response(HttpStatus.OK.value(), "discount added",null);
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
        }
    }

    @RequestMapping(value="/users/{id}/buyList/validateDiscount",method = RequestMethod.POST)
    public Response validateDiscount (@RequestHeader(value = "Authorization") String authJWT, @RequestBody String reqInfo,@PathVariable(value="id") String username ) throws Exception{
        if(authJWT == null || !JwtTokenUtil.validateTokenSigneture(authJWT) || JwtTokenUtil.isTokenExpired(authJWT)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "jwt have problem");
        }

        String userEmail = JwtTokenUtil.extractUserEmail(authJWT);
        UserDTO user = BalootServerRepo.getInstance().getUserById(username);
        if(!Objects.equals(user.getEmail(), userEmail))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "jwt have problem");

        try{
            var info = new ObjectMapper().readTree(reqInfo);
            String code = info.get("discountCode").asText();

            return new Response(HttpStatus.OK.value(), "discount added",BalootServerRepo.getInstance().validateDiscountCode(username,code));
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
        }
    }
//
    @RequestMapping(value="/users/{id}/buyList/submit",method = RequestMethod.POST)
    public Response submitBuyList (@RequestHeader(value = "Authorization") String authJWT, @RequestBody String reqInfo,@PathVariable(value="id") String username ) throws Exception{
        if(authJWT == null || !JwtTokenUtil.validateTokenSigneture(authJWT) || JwtTokenUtil.isTokenExpired(authJWT)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "jwt have problem");
        }

        String userEmail = JwtTokenUtil.extractUserEmail(authJWT);
        UserDTO user = BalootServerRepo.getInstance().getUserById(username);
        if(!Objects.equals(user.getEmail(), userEmail))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "jwt have problem");
        try{
            var info = new ObjectMapper().readTree(reqInfo);
            String code = info.get("discountCode").asText();
            String buylistID = info.get("buylistID").asText();
            System.out.println("in buylist handle");
            if(!code.equals("")) {
                System.out.println("error in validation");
                BalootServerRepo.getInstance().validateDiscountCode(username, code);
            }
            System.out.println("after apply discount code");
            BalootServerRepo.getInstance().handlePayment(username, code);
            return new Response(HttpStatus.OK.value(), "submitted",null);
        }
        catch (Exception e){
            return new Response(HttpStatus.NOT_FOUND.value(), "can't submit",e.getMessage());

//            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
        }
    }

    @RequestMapping(value="/users/{id}{commodityID}/remove",method = RequestMethod.POST)
    public Response removeFromBuylist (@RequestHeader(value = "Authorization") String authJWT, @RequestBody String reqInfo,@PathVariable(value="id") String username , @PathVariable(value="commodityID") String commodityID) throws Exception{
        if(authJWT == null || !JwtTokenUtil.validateTokenSigneture(authJWT) || JwtTokenUtil.isTokenExpired(authJWT)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "jwt have problem");
        }

        String userEmail = JwtTokenUtil.extractUserEmail(authJWT);
        UserDTO user = BalootServerRepo.getInstance().getUserById(username);
        if(!Objects.equals(user.getEmail(), userEmail))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "jwt have problem");
        try{
            var info = new ObjectMapper().readTree(reqInfo);

//            String username = info.get("userId").asText();

            BalootServerRepo.getInstance().updateCommodityCountInUserBuyList(username,Integer.valueOf(commodityID),-1);
            return new Response(HttpStatus.OK.value(), "suggestions sent",null);
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
        }
    }
    @RequestMapping(value="/users/{id}/{commodityID}/add",method = RequestMethod.POST)
    public Response addCommodityToBuyLst(@RequestHeader(value = "Authorization") String authJWT, @RequestBody String reqInfo, @PathVariable(value="id") String username , @PathVariable(value="commodityID") String commodityID) {
        if(authJWT == null || !JwtTokenUtil.validateTokenSigneture(authJWT) || JwtTokenUtil.isTokenExpired(authJWT)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "jwt have problem");
        }

        System.out.println("in add");

        String userEmail = JwtTokenUtil.extractUserEmail(authJWT);
        UserDTO user = BalootServerRepo.getInstance().getUserById(username);
        if(!Objects.equals(user.getEmail(), userEmail))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "jwt have problem");
        try {
            var info = new ObjectMapper().readTree(reqInfo);
//            String username = info.get("userId").asText();
            BalootServerRepo.getInstance().updateCommodityCountInUserBuyList(username , Integer.valueOf(commodityID),+1);
            return new Response(HttpStatus.OK.value(), "commodity added", null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @RequestMapping(value="/users/{id}/buyList/addCredit",method = RequestMethod.POST)
    public Response addCredit (@RequestHeader(value = "Authorization") String authJWT, @RequestBody String creditInfo ,@PathVariable(value="id") String username) throws Exception{
        if(authJWT == null || !JwtTokenUtil.validateTokenSigneture(authJWT) || JwtTokenUtil.isTokenExpired(authJWT)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "jwt have problem");
        }

        String userEmail = JwtTokenUtil.extractUserEmail(authJWT);
        UserDTO user = BalootServerRepo.getInstance().getUserById(username);
        if(!Objects.equals(user.getEmail(), userEmail))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "jwt have problem");
        try{
            var info = new ObjectMapper().readTree(creditInfo);
            String credit = info.get("credit").asText();
            BalootServerRepo.getInstance().addCredit(username,  credit);
            return new Response(HttpStatus.OK.value(), "credit added",null);
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
        }
    }

    @RequestMapping(value="/signup",method = RequestMethod.POST)
    public Response signUp(@RequestBody String userSignUpInfo) throws Exception{
        try{
            var signUpInfo = new ObjectMapper().readTree(userSignUpInfo);
            String username= signUpInfo.get("username").asText();
            String password = signUpInfo.get("password").asText();
            String birthDate = signUpInfo.get("birthDate").asText();
            String address = signUpInfo.get("address").asText();
            String email = signUpInfo.get("email").asText();
            BalootServerRepo.getInstance().addUser(new User(username, password, email, birthDate, address, 0));
            return new Response(HttpStatus.OK.value(), "sign up successfuly", JwtTokenUtil.generateToken(email));

        }
        catch( JsonProcessingException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        catch (UserAlreadyExistError e)
        {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getClass().getName());
        }
        catch (EmailAlreadyExist e)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getClass().getName());
        }
        catch (Exception e)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getClass().getName());
//            throw new ResponseStatusException(HttpStatus.,"user not found");

        }
    }
//
    @RequestMapping(value="/users/{id}/purchasedList",method = RequestMethod.GET)
    public Response getPurchasedList (@RequestHeader(value = "Authorization") String authJWT, @PathVariable(value="id") String username ) throws Exception{
        if(authJWT == null || !JwtTokenUtil.validateTokenSigneture(authJWT) || JwtTokenUtil.isTokenExpired(authJWT)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "jwt have problem");
        }

        String userEmail = JwtTokenUtil.extractUserEmail(authJWT);
        UserDTO user = BalootServerRepo.getInstance().getUserById(username);
        if(!Objects.equals(user.getEmail(), userEmail))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "jwt have problem");
        try{
            return new Response(HttpStatus.OK.value(), "purchasedlist sent",BalootServerRepo.getInstance().getUserPurchesedBuyList(username));
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
        }
    }

    @RequestMapping(value="/authorized",method = RequestMethod.POST)
    public Response getAuthorized (@RequestBody String userTokenInfo) throws Exception{
        var tokenInfo = new ObjectMapper().readTree(userTokenInfo);
        String token = tokenInfo.get("token").asText();
        try{
//            System.out.println("1");
//            System.out.println(JwtTokenUtil.isTokenExpired(token) );
//            System.out.println("2");
//            System.out.println(JwtTokenUtil.validateTokenSigneture(token));;
            if(token != null && !JwtTokenUtil.isTokenExpired(token) && JwtTokenUtil.validateTokenSigneture(token)) {
                return new Response(HttpStatus.OK.value(), "ok", "ok");
            }
            else
                return  new Response(HttpStatus.OK.value(), "not", "not");
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
        }
    }


//
    @RequestMapping(value="/users/buyListNum/{commodityId}",method = RequestMethod.POST)
    public Response getCommodityNum (@RequestHeader(value = "Authorization") String authJWT, @RequestBody String userSignUpInfo, @PathVariable(value="commodityId") String commodityId) throws Exception{
        if(authJWT == null || !JwtTokenUtil.validateTokenSigneture(authJWT) || JwtTokenUtil.isTokenExpired(authJWT)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "jwt have problem");
        }

        String userEmail = JwtTokenUtil.extractUserEmail(authJWT);
//        UserDTO user = BalootServerRepo.getInstance().getUserById(username);
//        if(!Objects.equals(user.getEmail(), userEmail))
//            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "jwt have problem");
        var signUpInfo = new ObjectMapper().readTree(userSignUpInfo);
        String username= signUpInfo.get("username").asText();

        try{
            return new Response(HttpStatus.OK.value(), "",BalootServerRepo.getInstance().getUserNumBought(username, Integer.valueOf(commodityId)));
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
        }
    }
}

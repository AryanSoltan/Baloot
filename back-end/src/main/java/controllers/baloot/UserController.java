package controllers.baloot;

import Baloot.BalootServer;
import Baloot.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import controllers.baloot.ReposnsePackage.Response;
import org.springframework.web.server.ResponseStatusException;

@RestController
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class UserController {

    @RequestMapping(value="/login",method = RequestMethod.POST)
    public Response logIn (@RequestBody String userLoginInfo) throws Exception{

        try{
            var loginInfo = new ObjectMapper().readTree(userLoginInfo);
            String username= loginInfo.get("username").asText();
            String password = loginInfo.get("password").asText();
            BalootServer.getInstance().logIn(username,password);
            return new Response(HttpStatus.OK.value(), "logged in",null);

        }
        catch( JsonProcessingException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        catch (Exception e)
        {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"user not found");
        }
    }

    @RequestMapping(value="/isLogin", method = RequestMethod.POST)
    public Response isLogIn (@RequestBody String userLoginInfo) throws Exception{

        try{
            boolean isLogIn = BalootServer.getInstance().isLogIn();
            return new Response(HttpStatus.OK.value(), "logged in",isLogIn);

        }
        catch (Exception e)
        {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"user not found");
        }
    }



    @RequestMapping(value="/logout",method = RequestMethod.POST)
    public Response logOut (@RequestBody String userLoginInfo) throws Exception{
        try {
            if (BalootServer.getInstance().getLoggedInUser() == null) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"user not logged in");

            } else {
                BalootServer.getInstance().logOut();
                return new Response(HttpStatus.OK.value(),"logged out",null);
            }
        }

        catch (Exception e)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value="/users/{id}/buyList",method = RequestMethod.GET)
    public Response getBuyList (@PathVariable(value="id") String username ) throws Exception{
        System.out.println("in back");

        try{
            return new Response(HttpStatus.OK.value(), "buylist sent",BalootServer.getInstance().getUserBuyList(username));
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
        }
    }


    @RequestMapping(value="/users/{id}",method = RequestMethod.GET)
    public Response getUser (@PathVariable(value="id") String username ) throws Exception{
        try{
            return new Response(HttpStatus.OK.value(), "user sent",BalootServer.getInstance().getUserById(username));
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
        }
    }

//    @RequestMapping(value="/users/{id}/buyList/applyDiscount",method = RequestMethod.POST)
//    public Response applyDiscount (@RequestBody String reqInfo,@PathVariable(value="id") String username ) throws Exception{
//        try{
//
//
//            var info = new ObjectMapper().readTree(reqInfo);
//            String code = info.get("discountCode").asText();
//            BalootServer.getInstance().applyDiscountCode(username,code);z
//            return new Response(HttpStatus.OK.value(), "discount added",null);
//        }
//        catch (Exception e){
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
//        }
//    }

    @RequestMapping(value="/users/{id}/buyList/validateDiscount",method = RequestMethod.POST)
    public Response validateDiscount (@RequestBody String reqInfo,@PathVariable(value="id") String username ) throws Exception{
        try{
            var info = new ObjectMapper().readTree(reqInfo);
            String code = info.get("discountCode").asText();

            return new Response(HttpStatus.OK.value(), "discount added",BalootServer.getInstance().validateDiscountCode(username,code));
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
        }
    }

    @RequestMapping(value="/users/{id}/buyList/submit",method = RequestMethod.POST)
    public Response submitBuyList (@RequestBody String reqInfo,@PathVariable(value="id") String username ) throws Exception{
        try{
            var info = new ObjectMapper().readTree(reqInfo);
            String code = info.get("discountCode").asText();
            if(!code.equals(""))
                BalootServer.getInstance().applyDiscountCode(username,code);
            BalootServer.getInstance().handlePaymentUser(username);
            return new Response(HttpStatus.OK.value(), "submitted",null);
        }
        catch (Exception e){
            return new Response(HttpStatus.NOT_FOUND.value(), "can't submit",e.getMessage());

//            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
        }
    }

    @RequestMapping(value="/users/{commodityID}/remove",method = RequestMethod.POST)
    public Response removeFromBuylist (@RequestBody String reqInfo,@PathVariable(value="commodityID") String commodityID) throws Exception{
        try{
            var info = new ObjectMapper().readTree(reqInfo);
            String username = info.get("userId").asText();

            BalootServer.getInstance().removeFromBuyList(username,Integer.valueOf(commodityID));
            return new Response(HttpStatus.OK.value(), "suggestions sent",null);
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
        }
    }
    @RequestMapping(value="/users/{commodityID}/add",method = RequestMethod.POST)
    public Response addCommodityToBuyLst(@RequestBody String reqInfo, @PathVariable(value="commodityID") String commodityID) {
        try {
            var info = new ObjectMapper().readTree(reqInfo);
            String username = info.get("userId").asText();

            BalootServer.getInstance().addCommidityToUserBuyList(username , Integer.valueOf(commodityID));

            return new Response(HttpStatus.OK.value(), "commodity added", null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @RequestMapping(value="/users/{id}/buyList/addCredit",method = RequestMethod.POST)
    public Response addCredit (@RequestBody String creditInfo ,@PathVariable(value="id") String username) throws Exception{
        try{
            var info = new ObjectMapper().readTree(creditInfo);
            String credit = info.get("credit").asText();
            BalootServer.getInstance().addCredit(username, credit);
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
            BalootServer.getInstance().addUser(new User(username, password, email, birthDate, address, 0));
            return new Response(HttpStatus.OK.value(), "sign up successfuly",null);

        }
        catch( JsonProcessingException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        catch (Exception e)
        {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"user not found");
        }
    }

    @RequestMapping(value="/users/{id}/purchasedList",method = RequestMethod.GET)
    public Response getPurchasedList (@PathVariable(value="id") String username ) throws Exception{

        try{
            return new Response(HttpStatus.OK.value(), "purchasedlist sent",BalootServer.getInstance().getUserPurchasedList(username));
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
        }
    }

    @RequestMapping(value="/users/buyListNum/{commodityId}",method = RequestMethod.POST)
    public Response getCommodityNum (@RequestBody String userSignUpInfo, @PathVariable(value="commodityId") String commodityId) throws Exception{

        var signUpInfo = new ObjectMapper().readTree(userSignUpInfo);
        String username= signUpInfo.get("username").asText();

        try{
            return new Response(HttpStatus.OK.value(), "",BalootServer.getInstance().getUserNumBought(username, Integer.valueOf(commodityId)));
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
        }
    }
}

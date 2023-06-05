package controllers.baloot;

import Baloot.DTOObjects.CommodityDTO;
import Baloot.DTOObjects.UserDTO;
import Baloot.Exception.CommodityNotExist;
import Baloot.User;
import JWTTokenHandler.JwtTokenUtil;
import Repository.BalootServerRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import controllers.baloot.ReposnsePackage.Response;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class CommodityController {

    @RequestMapping(value="/commodities",method = RequestMethod.GET)
    public Response getCommodities(@RequestHeader(value = "Authorization") String authJWT){

        if(authJWT == null || !JwtTokenUtil.validateTokenSigneture(authJWT) || JwtTokenUtil.isTokenExpired(authJWT)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "jwt have problem");
        }

        System.out.println(authJWT);
        System.out.println("in Commodities");
        List commodities = BalootServerRepo.getInstance().getAllCommodities();
        System.out.println("commodities are "+commodities);
        try{
            return new Response(HttpStatus.OK.value(), "commodities list sent", commodities);
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
        }
    }

    @RequestMapping(value="/commodities/{id}",method = RequestMethod.GET)
    public Response getCommodity(@RequestHeader(value = "Authorization") String authJWT, @PathVariable(value="id") String commodityID ){
        if(authJWT == null || !JwtTokenUtil.validateTokenSigneture(authJWT) || JwtTokenUtil.isTokenExpired(authJWT)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "jwt have problem");
        }
        try{
            return new Response(HttpStatus.OK.value(), "commodity info sent", BalootServerRepo.getInstance().getCommodityById(Integer.valueOf(commodityID)));
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
        }
    }

    @RequestMapping(value="/commodities/{id}/{userId}/suggestions",method = RequestMethod.GET)
    public Response getSuggestedCommodities(@RequestHeader(value = "Authorization") String authJWT, @PathVariable(value="id") String commodityID, @PathVariable(value="userId") String userID  ) throws Exception {
        try{
            if(authJWT == null || !JwtTokenUtil.validateTokenSigneture(authJWT) || JwtTokenUtil.isTokenExpired(authJWT)) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "jwt have problem");
            }

            String userEmail = JwtTokenUtil.extractUserEmail(authJWT);
            UserDTO user = BalootServerRepo.getInstance().getUserById(userID);
            if(!Objects.equals(user.getEmail(), userEmail))
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "jwt have problem");

            List suggestions = BalootServerRepo.getInstance().getSuggestedCommodities(Integer.valueOf(commodityID),userID);
            System.out.println("in suggxtions"+suggestions);
            return new Response(HttpStatus.OK.value(), "suggestions sent",suggestions);
        }
        catch (CommodityNotExist e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
        }
    }


    @RequestMapping(value="/commodities/{id}/{userId}/rate",method = RequestMethod.POST)
    public Response rateCommodity(@RequestHeader(value = "Authorization") String authJWT, @RequestBody String rateInfo ,@PathVariable(value="id") String commodityID,@PathVariable(value="userId") String userID ){
        try{
            if(authJWT == null || !JwtTokenUtil.validateTokenSigneture(authJWT) || JwtTokenUtil.isTokenExpired(authJWT)) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "jwt have problem");
            }
            String userEmail = JwtTokenUtil.extractUserEmail(authJWT);
            UserDTO user = BalootServerRepo.getInstance().getUserById(userID);
            if(!Objects.equals(user.getEmail(), userEmail))
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "jwt have problem");

            var info = new ObjectMapper().readTree(rateInfo);
            String rate  = info.get("rate").asText();
            BalootServerRepo.getInstance().rateCommodity(userID,Integer.valueOf(commodityID), rate);
            return new Response(HttpStatus.OK.value(), "commodity rate added",
                    BalootServerRepo.getInstance().getCommodityById(Integer.valueOf(commodityID)));
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
        }
    }
}

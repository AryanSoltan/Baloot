package controllers.baloot;

import Baloot.DTOObjects.CommodityDTO;
import Repository.BalootServerRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import controllers.baloot.ReposnsePackage.Response;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class CommodityController {

    @RequestMapping(value="/commodities",method = RequestMethod.GET)
    public Response getCommodities(@RequestHeader(value = "Authorization") String authJWT){
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
    public Response getCommodity(@PathVariable(value="id") String commodityID ){
        try{
            return new Response(HttpStatus.OK.value(), "commodity info sent", BalootServerRepo.getInstance().getCommodityById(Integer.valueOf(commodityID)));
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
        }
    }

    @RequestMapping(value="/commodities/{id}/{userId}/suggestions",method = RequestMethod.GET)
    public Response getSuggestedCommodities(@PathVariable(value="id") String commodityID, @PathVariable(value="userId") String userID  ){
        try{

            List suggestions = BalootServerRepo.getInstance().getSuggestedCommodities(Integer.valueOf(commodityID),userID);
            System.out.println("in suggxtions"+suggestions);
            return new Response(HttpStatus.OK.value(), "suggestions sent",suggestions);
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
        }
    }


    @RequestMapping(value="/commodities/{id}/{userId}/rate",method = RequestMethod.POST)
    public Response rateCommodity(@RequestBody String rateInfo ,@PathVariable(value="id") String commodityID,@PathVariable(value="userId") String userID ){
        try{
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

//package controllers.baloot;
//
//
//import Baloot.BalootServer;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import controllers.baloot.ReposnsePackage.Response;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.server.ResponseStatusException;
//
//@RestController
//@CrossOrigin(origins = "*",allowedHeaders = "*")
//public class CommodityController {
//
//    @RequestMapping(value="/commodities",method = RequestMethod.GET)
//    public Response getCommodities(){
//        try{
//            return new Response(HttpStatus.OK.value(), "commodities list sent", BalootServer.getInstance().getFilteredCommodities());
//        }
//        catch (Exception e){
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
//        }
//    }
//
//    @RequestMapping(value="/commodities/{id}",method = RequestMethod.GET)
//    public Response getCommodity(@PathVariable(value="id") String commodityID ){
//        try{
//            return new Response(HttpStatus.OK.value(), "commodity info sent", BalootServer.getInstance().getCommodityById(Integer.valueOf(commodityID)));
//        }
//        catch (Exception e){
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
//        }
//    }
//
//    @RequestMapping(value="/commodities/{id}/suggestions",method = RequestMethod.GET)
//    public Response getSuggestedCommodities(@PathVariable(value="id") String commodityID ){
//        try{
//            return new Response(HttpStatus.OK.value(), "suggestions sent",BalootServer.getInstance().getSuggestedCommodities(Integer.valueOf(commodityID)));
//        }
//        catch (Exception e){
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
//        }
//    }
//
//    @RequestMapping(value="/search",method = RequestMethod.GET)
//    public Response getFilteredCommodities(@RequestBody String filters){
//
//        try {
//            var filterDetails = new ObjectMapper().readTree(filters);
//            String action = filterDetails.get("action").asText();
//            switch (action) {
//                case "search_by_category" :
//                    String searchVal =  filterDetails.get("search").asText();
//                    BalootServer.getInstance().setSearchFilters(searchVal,"category");
//                    break;
//
//                case "search_by_name" :
//                    searchVal =  filterDetails.get("search").asText();
//                    BalootServer.getInstance().setSearchFilters(searchVal,"name");
//                    break;
//
//                case "clear" :
//                    BalootServer.getInstance().clearFilters();
//                    break;
//                case "sort_by_rate" :
//                    BalootServer.getInstance().setSortFilters("rate");
//
//                    break;
//            }
//            return new Response(HttpStatus.OK.value(), "filter set",null );
//
//        }
//        catch(Exception e){
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
//
//        }
//
//    }
//
//    @RequestMapping(value="/commodities/{id}/{userId}/rate",method = RequestMethod.POST)
//    public Response rateCommodity(@RequestBody String rateInfo ,@PathVariable(value="id") String commodityID,@PathVariable(value="userId") String userID ){
//        try{
//            var info = new ObjectMapper().readTree(rateInfo);
//            String rate  = info.get("rate").asText();
//            BalootServer.getInstance().rateCommodity(userID,Integer.valueOf(commodityID), rate);
//            return new Response(HttpStatus.OK.value(), "commodity rate added",
//                    BalootServer.getInstance().getCommodityById(Integer.valueOf(commodityID)));
//        }
//        catch (Exception e){
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
//        }
//    }
//
//
//
//}

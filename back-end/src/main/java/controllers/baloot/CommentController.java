package controllers.baloot;


import Baloot.Comment;
import Baloot.DTOObjects.CommentDTO;
import Baloot.DTOObjects.UserDTO;
import JWTTokenHandler.JwtTokenUtil;
import Repository.BalootServerRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import controllers.baloot.ReposnsePackage.Response;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@RestController
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class CommentController {



    @RequestMapping(value="/commodities/{commodityId}/comment",method = RequestMethod.POST)
    public Response addComment(@RequestHeader(value = "Authorization") String authJWT, @RequestBody String rateInfo ,@PathVariable(value="commodityId") String commodityID ){
        try{
            if(authJWT == null || !JwtTokenUtil.validateTokenSigneture(authJWT) || JwtTokenUtil.isTokenExpired(authJWT)) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "jwt have problem");
            }
            var info = new ObjectMapper().readTree(rateInfo);
            String commentText  = info.get("comment").asText();
            String userID = info.get("userId").asText();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu-MM-dd");
            LocalDate localDate = LocalDate.now();
            String currDate = dtf.format(localDate);
            CommentDTO comment = BalootServerRepo.getInstance().addComment(userID,Integer.valueOf(commodityID),commentText,currDate);
            return new Response(HttpStatus.OK.value(), "comment  added", comment );
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
        }
    }

//    @RequestMapping(value="/commodities/{id}/comments",method = RequestMethod.POST)
//    public Response getComments(@PathVariable(value="id") String commodityID ){
//        try{
//            System.out.println("in get comments");
//            List<CommentDTO> comments = BalootServerRepo.getInstance(). getCommodityComments(Integer.valueOf(commodityID));
//
//         //   Comment comment = BalootServerRepo.getInstance(). getCommodityComments(Integer.valueOf(commodityID));
//            return new Response(HttpStatus.OK.value(), "comment  added", comments);
//        }
//        catch (Exception e){
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
//        }
//    }


//
    @RequestMapping(value="/commodities/{id}/{userId}/like",method = RequestMethod.POST)
    public Response likeComment(@RequestHeader(value = "Authorization") String authJWT, @RequestBody String rateInfo ,@PathVariable(value="id") String commodityID,@PathVariable(value="userId") String userID ){

        try{
            if(authJWT == null || !JwtTokenUtil.validateTokenSigneture(authJWT) || JwtTokenUtil.isTokenExpired(authJWT)) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "jwt have problem");
            }
            String userEmail = JwtTokenUtil.extractUserEmail(authJWT);
            UserDTO user = BalootServerRepo.getInstance().getUserById(userID);
            if(!Objects.equals(user.getEmail(), userEmail))
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "jwt have problem");
            var info = new ObjectMapper().readTree(rateInfo);
            Integer commentId  = Integer.valueOf(info.get("comment_id").asText());
            System.out.println("in like");
            CommentDTO comment = BalootServerRepo.getInstance().addRatingToComment(commentId,userID,1);
            return new Response(HttpStatus.OK.value(), "comment  added", comment);
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
        }
    }
//
    @RequestMapping(value="/commodities/{id}/{userId}/dislike",method = RequestMethod.POST)
    public Response dislikeComment(@RequestHeader(value = "Authorization") String authJWT, @RequestBody String rateInfo ,@PathVariable(value="id") String commodityID,@PathVariable(value="userId") String userID ){
        try{
            if(authJWT == null || !JwtTokenUtil.validateTokenSigneture(authJWT) || JwtTokenUtil.isTokenExpired(authJWT)) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "jwt have problem");
            }
            String userEmail = JwtTokenUtil.extractUserEmail(authJWT);
            UserDTO user = BalootServerRepo.getInstance().getUserById(userID);
            if(!Objects.equals(user.getEmail(), userEmail))
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "jwt have problem");
            var info = new ObjectMapper().readTree(rateInfo);
            Integer commentId  = Integer.valueOf(info.get("comment_id").asText());

            CommentDTO comment = BalootServerRepo.getInstance().addRatingToComment(commentId,userID,-1);

            return new Response(HttpStatus.OK.value(), "comment  added", comment);
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
        }
    }


}

package controllers.baloot;

import Baloot.BalootServer;
import Baloot.Comment;
import com.fasterxml.jackson.databind.ObjectMapper;
import controllers.baloot.ReposnsePackage.Response;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class CommentController {



    @RequestMapping(value="/commodities/{id}/comment",method = RequestMethod.POST)
    public Response addComment(@RequestBody String rateInfo ,@PathVariable(value="id") String commodityID ){
        try{
            var info = new ObjectMapper().readTree(rateInfo);
            String commentText  = info.get("comment").asText();
            String userID = info.get("userId").asText();
            String useremail = BalootServer.getInstance().getLoggedInUser().getEmail();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu-MM-dd");
            LocalDate localDate = LocalDate.now();
            String currDate = dtf.format(localDate);
            Comment comment = new Comment(useremail,Integer.valueOf(commodityID),commentText,currDate);
            BalootServer.getInstance().addComment(comment);
            return new Response(HttpStatus.OK.value(), "comment  added", comment );
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
        }
    }


    @RequestMapping(value="/commodities/{id}/{userId}/like",method = RequestMethod.POST)
    public Response likeComment(@RequestBody String rateInfo ,@PathVariable(value="id") String commodityID,@PathVariable(value="userId") String userID ){
        try{
            var info = new ObjectMapper().readTree(rateInfo);
            Integer commentId  = Integer.valueOf(info.get("comment_id").asText());

            BalootServer.getInstance().addRatingToComment(commentId,userID,1);
            return new Response(HttpStatus.OK.value(), "comment  added", null);
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
        }
    }

    @RequestMapping(value="/commodities/{id}/{userId}/dislike",method = RequestMethod.POST)
    public Response dislikeComment(@RequestBody String rateInfo ,@PathVariable(value="id") String commodityID,@PathVariable(value="userId") String userID ){
        try{
            var info = new ObjectMapper().readTree(rateInfo);
            Integer commentId  = Integer.valueOf(info.get("comment_id").asText());

            BalootServer.getInstance().addRatingToComment(commentId,userID,-1);
            return new Response(HttpStatus.OK.value(), "comment  added", null);
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
        }
    }


}

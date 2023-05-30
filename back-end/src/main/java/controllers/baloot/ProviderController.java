package controllers.baloot;

import Baloot.User;
import Repository.BalootServerRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import controllers.baloot.ReposnsePackage.Response;
import org.springframework.web.server.ResponseStatusException;

@RestController
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class ProviderController {



    @RequestMapping(value="/providers/{id}",method = RequestMethod.GET)
    public Response getUser (@PathVariable(value="id") String username ) throws Exception{
        try{
            return new Response(HttpStatus.OK.value(), "user sent", BalootServerRepo.getInstance().getProviderById(Integer.valueOf(username)));
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
        }
    }

}

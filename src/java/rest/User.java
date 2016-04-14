package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import facades.UserFacade;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("demouser")
@RolesAllowed({"User", "Admin"})
public class User
{

    
    Gson gson;

    public User(Gson gson)
    {
        this.gson = new GsonBuilder().setPrettyPrinting().create();

    }

    public User()
    {
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getSomething()
    {
        return "{\"message\" : \"This message was delivered via a REST call accesible by only authenticated USERS\"}";
    }
    
    
  


}

package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import facades.UserFacade;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("demouser")
//@RolesAllowed("User")
public class User
{

    UserFacade uf = (UserFacade) security.UserFacadeFactory.getInstance();
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

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void createUser(String jsonPerson)
    {
        JsonObject jsonIn = new JsonParser().parse(jsonPerson).getAsJsonObject();
        String userName = jsonIn.get("username").getAsString();
        String password = jsonIn.get("password").getAsString();
        entity.User newUsr = new entity.User(userName, password);

        uf.saveUser(newUsr);
        System.out.println("Jeg er i createUser og jeg har lavet en person m. fields = " + newUsr.getPassword() + " og " + newUsr.getUserName());
        
    }
}

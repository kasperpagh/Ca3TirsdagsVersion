package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import facades.UserFacade;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("admin")
//@RolesAllowed("Admin")
public class Admin {

    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getSomething() {
        String now = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss").format(new Date());
        return "{\"message\" : \"This message was delivered via a REST call accesible by only authenticated ADMINS\",\n"
                + "\"serverTime\": \"" + now + "\"}";
    }

    entity.Role Role = new entity.Role();
    UserFacade uf = (UserFacade) security.UserFacadeFactory.getInstance();
    entity.User User = new entity.User();

    @DELETE
    @Path("/user/{name}")
    @Consumes(MediaType.APPLICATION_JSON)

    public void deletePerson(@PathParam("name") String name) throws Exception {
        if (name == null) {
            throw new Exception("No person with the given id exsists!");
        }

        uf.deleteUser(name);

    }

    @GET
    @Path("/users")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllPersons() {

        List<entity.User> receivedList = Role.getUsers();
        JsonArray jA = new JsonArray();
        for (entity.User per : receivedList) {
            JsonObject jO = new JsonObject();
            jO.addProperty("id", per.getUserName());
            jA.add(jO);
        }

        return gson.toJson(jA);

    }

}

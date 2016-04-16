/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import facades.UserFacade;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author dino
 * 
 */

@Path("createuser")
public class newuser {
    
    UserFacade uf = (UserFacade) security.UserFacadeFactory.getInstance();
    
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

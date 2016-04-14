/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import entity.ExchangeRates;
import facades.CurrencyHandler;
import facades.UserFacade;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author kaspe
 */
@Path("currency")
//@RolesAllowed("User")
public class DailyExchangeRates
{

    Gson gson;

    public DailyExchangeRates()
    {

        this.gson = new GsonBuilder().setPrettyPrinting().create();

    }

    
    @GET
    @Path("/dailyrates")
    @Produces(MediaType.APPLICATION_JSON)
    public String getDailyRates()
    {
        return gson.toJson(facades.CurrencyHandler.dailyRates);
    }

}

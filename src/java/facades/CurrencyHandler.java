/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entity.ExchangeRates;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author kaspe
 */
public class CurrencyHandler
{

    static ExchangeRates dailyRates;
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("PU-Local");

    public void persistExchangeRates(ExchangeRates er)
    {
        dailyRates = er;
        EntityManager em = emf.createEntityManager();
        try
        {
            em.getTransaction().begin();
            em.persist(er);
            em.getTransaction().commit();
        }
        catch (Exception e)
        {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            em.getTransaction().rollback();
        }
        finally
        {
            em.close();
        }
    }
}

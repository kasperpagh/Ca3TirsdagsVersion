/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entity.ExchangeRates;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author kaspe
 */
public class CurrencyHandler
{

    public static ExchangeRates dailyRates;
    public 
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("PU-Local");

    public void persistExchangeRates(ExchangeRates er)
    {

        EntityManager em = emf.createEntityManager();
        ExchangeRates temp;
        try
        {

            em.getTransaction().begin();
            em.persist(er);
            em.getTransaction().commit();

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH");

            Date dato = new Date();
            String nowDato = dateFormat.format(dato);
//            System.out.println("her er dato: " + nowDato);
            Integer llama = Integer.parseInt(nowDato.substring(11, 13));
            System.out.println("Her er llama " + llama);
            if (llama >= 16)
            {
                System.out.println("Jeg er størrer end 16");
                Query query = em.createNamedQuery("ExchangeRates.findByDate", ExchangeRates.class);
                System.out.println("her er dato: " + nowDato);

//            temp = em.find(ExchangeRates.class, 1);
                temp = (ExchangeRates) query.setParameter("dato", nowDato).getSingleResult();
                System.out.println("FIND: " + temp.toString());
                if (temp != null)
                {
                    dailyRates = temp;
                }
                System.out.println("her fra persist, dailyRates: " + dailyRates);
            }
            else
            {
                em.find(ExchangeRates.class, 1);
            }
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

    public ExchangeRates getDailyExchangeRates()
    {
        return dailyRates;
    }
}

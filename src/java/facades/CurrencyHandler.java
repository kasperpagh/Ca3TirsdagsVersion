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
import openshift_deploy.DeploymentConfiguration;

/**
 *
 * @author kaspe
 */
public class CurrencyHandler
{

    public static ExchangeRates dailyRates;
    public EntityManagerFactory emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);

    public void persistExchangeRates(ExchangeRates er)
    {

        EntityManager em = emf.createEntityManager();
        ExchangeRates temp;
        try
        {

            em.getTransaction().begin();
            
            em.persist(er);
            em.refresh(er);
            dailyRates = er;
            em.getTransaction().commit();


//            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH");
//
//            Date dato = new Date();
//            String nowDato = dateFormat.format(dato);
////            System.out.println("her er dato: " + nowDato);
//            Integer llama = Integer.parseInt(nowDato.substring(11, 13));
//            System.out.println("Her er llama " + llama);
//            DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
//            String now1Dato = dateFormat1.format(dato);
//            DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
//            String now2Dato = dateFormat1.format(dato);
//            String s1 = now2Dato.substring(7, 9);
//            String s2 = now2Dato.substring(0, 7);
//            Integer i1 = Integer.parseInt(s1);
//            i1 -= 1;
//
//            now2Dato = s2 + i1.toString();
//
//            if (llama >= 16)
//            {
//                System.out.println("Jeg er størrer end 16");
//                Query query = em.createNamedQuery("ExchangeRates.findByDate", ExchangeRates.class);
//                System.out.println("her er dato: " + nowDato);
//
////            temp = em.find(ExchangeRates.class, 1);
//                temp = (ExchangeRates) query.setParameter("dato", now1Dato).getSingleResult();
////                System.out.println("FIND: " + temp.toString());
//                if (temp != null)
//                {
//                    dailyRates = temp;
//
//                }
////                System.out.println("her fra persist, dailyRates: " + dailyRates);
//            }
//            else
//            {
////                System.out.println("Vi er i pers else");
//
//                Query query1 = em.createNamedQuery("ExchangeRates.findByDate", ExchangeRates.class);//em.find(ExchangeRates.class, 1);
//                dailyRates = (ExchangeRates) query1.setParameter("dato", now2Dato).getSingleResult();
//            }
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
//        System.out.println("Her er dailyRates: " + dailyRates.toString());
        EntityManager em = emf.createEntityManager();
        try
        {
            em.getTransaction().begin();
            dailyRates = em.find(ExchangeRates.class, dailyRates.getId());
            em.refresh(dailyRates);
            em.getTransaction().commit();
            return dailyRates;

        }
        catch(Exception e)
        {
            return dailyRates;
        }
        finally
        {
            em.close();
        }

    }
}

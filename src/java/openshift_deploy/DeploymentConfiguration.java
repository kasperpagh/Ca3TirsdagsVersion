package openshift_deploy;

import entity.Role;
import entity.User;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import security.PasswordStorage;
import xml.TimerStarter;
import xml.XmlReader;

@WebListener
public class DeploymentConfiguration implements ServletContextListener
{

    public static String PU_NAME = "PU-Local";
    public static Role userRole = new Role("User");
    public static Role adminRole = new Role("Admin");
//    
//    private ScheduledExecutorService scheduler;
//    Timer timer = new Timer();
//    private XmlReader xr;

//    public void firstRate()
//    {
//        new Thread(new XmlReader()).start();
//    }
    @Override
    public void contextInitialized(ServletContextEvent sce)
    {
        
//        If we are testing, then this:
        if (sce.getServletContext().getInitParameter("testEnv") != null)
        {
            PU_NAME = "PU_TEST";
        }
        Map<String, String> env = System.getenv();
        //If we are running in the OPENSHIFT environment change the pu-name 
        if (env.keySet().contains("OPENSHIFT_MYSQL_DB_HOST"))
        {
            PU_NAME = "PU_OPENSHIFT";
        }
        new Thread(new TimerStarter()).start();
        
//         timer.scheduleAtFixedRate(new TimerStarter(), 0, 86400000);
       
//        scheduler = Executors.newSingleThreadScheduledExecutor();
//        scheduler.scheduleAtFixedRate(new Thread(xr), 0, 24, TimeUnit.HOURS);
        
        
        
        
        try
        {
            ServletContext context = sce.getServletContext();
            EntityManagerFactory emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);
            EntityManager em = emf.createEntityManager();

            //This flag is set in Web.xml -- Make sure to disable for a REAL system
            boolean makeTestUsers = context.getInitParameter("makeTestUsers").toLowerCase().equals("true");
            if (!makeTestUsers
                    || (em.find(User.class, "user") != null && em.find(User.class, "admin") != null && em.find(User.class, "user_admin") != null))
            {
                return;
            }

            User user = new User("user", PasswordStorage.createHash("test"));
            User admin = new User("admin", PasswordStorage.createHash("test"));
            User both = new User("user_admin", PasswordStorage.createHash("test"));
            user.AddRole(userRole);
            admin.AddRole(adminRole);
            both.AddRole(userRole);
            both.AddRole(adminRole);
            
            

            try
            {
                em.getTransaction().begin();
                em.persist(userRole);
                em.persist(adminRole);

                em.persist(user);
                em.persist(admin);
                em.persist(both);
                em.getTransaction().commit();
//                firstRate();
            }
            finally
            {
                em.close();
            }
        }
        catch (PasswordStorage.CannotPerformOperationException ex)
        {
            Logger.getLogger(DeploymentConfiguration.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce)
    {
//        scheduler.shutdownNow();
    }
}

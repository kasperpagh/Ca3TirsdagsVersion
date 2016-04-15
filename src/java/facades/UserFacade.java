package facades;

import entity.Role;
import security.IUserFacade;
import entity.User;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import openshift_deploy.DeploymentConfiguration;
import security.IUser;
import security.PasswordStorage;

public class UserFacade implements IUserFacade
{

    EntityManagerFactory emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);

    public UserFacade()
    {

    }

    @Override
    public IUser getUserByUserId(String id)
    {
        EntityManager em = emf.createEntityManager();
        try
        {
            return em.find(User.class, id);
        }
        finally
        {
            em.close();
        }
    }
    /*
     Return the Roles if users could be authenticated, otherwise null
     */

    @Override
    /*
     Return the Roles if users could be authenticated, otherwise null
     */
    public List<String> authenticateUser(String userName, String password)
    {
        EntityManager em = emf.createEntityManager();
        try
        {
            User user = em.find(User.class, userName);
            if (user == null)
            {
                return null;
            }

            boolean authenticated;
            try
            {
                authenticated = PasswordStorage.verifyPassword(password, user.getPassword());
                return authenticated ? user.getRolesAsStrings() : null;
            }
            catch (PasswordStorage.CannotPerformOperationException | PasswordStorage.InvalidHashException ex)
            {
                Logger.getLogger(UserFacade.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
        }
        finally
        {
            em.close();
        }
    }

    public void saveUser(User newUsr)
    {
        EntityManager em = emf.createEntityManager();
        try
        {
            em.getTransaction().begin();
//            newUsr.AddRole(DeploymentConfiguration.userRole);
            newUsr.AddRole(em.find(Role.class, DeploymentConfiguration.userRole.getRoleName()));
            System.out.println("Hejsa " + newUsr.getPassword() + " - " + newUsr.getUserName() + " - " + newUsr.getRolesAsStrings());

            newUsr.setPassword(PasswordStorage.createHash(newUsr.getPassword()));

            em.persist(newUsr);
            em.getTransaction().commit();
        }
        catch (PasswordStorage.CannotPerformOperationException | org.eclipse.persistence.exceptions.DatabaseException ex)
        {
            Logger.getLogger(UserFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally
        {
            em.close();
        }
    }

    public void deleteUser(String name)
    {
        System.out.println("VI er i delUSR");
        EntityManager em = emf.createEntityManager();
        try
        {
            em.getTransaction().begin();
            User p = em.find(User.class, name);
            
            
            if (p != null)
            {
                System.out.println("User er ikke null");
                em.remove(p);
                DeploymentConfiguration.userRole = em.find(entity.Role.class, DeploymentConfiguration.userRole.getRoleName());
                em.getTransaction().commit();
            }

        }
        finally
        {
            em.close();
        }
    }

    public List<entity.User> getAllUsers()
    {
        EntityManager em = emf.createEntityManager();
        try
        {
            em.getTransaction().begin();
            entity.Role rolle = em.find(entity.Role.class, DeploymentConfiguration.userRole.getRoleName());
            System.out.println("Her er list: " + rolle.getUsers().toString());
            em.getTransaction().commit();
            System.out.println("Her er rolle: " + rolle.toString());
            
            return rolle.getUsers();
            
        }
        finally
        {
            em.close();
        }

    }

}

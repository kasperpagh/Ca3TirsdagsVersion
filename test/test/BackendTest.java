package test;

import static com.jayway.restassured.RestAssured.basePath;
import static com.jayway.restassured.RestAssured.baseURI;
import static com.jayway.restassured.RestAssured.defaultParser;
import static com.jayway.restassured.RestAssured.given;
import com.jayway.restassured.parsing.Parser;
import static com.jayway.restassured.path.json.JsonPath.from;
import entity.User;
import facades.UserFacade;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import static org.hamcrest.Matchers.equalTo;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import rest.ApplicationConfig;

/**
 *
 * @author sofus
 */
public class BackendTest {

    static Server server;
    public EntityManagerFactory emf = Persistence.createEntityManagerFactory("PU-Local");
    
    public BackendTest() {
        //ændret til en url der ikke er den samme som tomcat -.-
        baseURI = "http://localhost:8085";
        defaultParser = Parser.JSON;
        basePath = "/api";
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        server = new Server(8085);
        ServletHolder servletHolder = new ServletHolder(org.glassfish.jersey.servlet.ServletContainer.class);
        servletHolder.setInitParameter("javax.ws.rs.Application", ApplicationConfig.class.getName());
        ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        contextHandler.setContextPath("/");
        contextHandler.addServlet(servletHolder, "/api/*");
        server.setHandler(contextHandler);
        server.start();
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        server.stop();
        //waiting for all the server threads to terminate so we can exit gracefully
        server.join();
    }
    
    @Test
    public void testDBFacade()
    {
        EntityManager em = emf.createEntityManager();
        UserFacade uf = (UserFacade) security.UserFacadeFactory.getInstance();
        User usr = new User("lol", "bob");
        uf.saveUser(usr);
        Assert.assertTrue(em.find(User.class, usr.getUserName()) != null);
    }

    @Test
    public void LoginWrongUsername() {
        given().
                contentType("application/json").
                body("{'username':'john','password':'test'}").
                when().
                post("/login").
                then().
                statusCode(401).
                body("error.message", equalTo("Invalid username or password")); 
    }

    @Test
    public void LoginWrongUsernameAndPassword() {
        //wrong username and password
        given().
                contentType("application/json").
                body("{'username':'john','password':'doe'}").
                when().
                post("/login").
                then().
                statusCode(401).
                body("error.message", equalTo("Invalid username or password"));
    }

    @Test
    public void Login() {
        //Successful login
        given().
                contentType("application/json").
                body("{'username':'user','password':'test'}").
                when().
                post("/login").
                then().
                statusCode(200);

    }

    @Test
    public void testDemoUserNoLogin() {
        given().
                contentType("application/json").
                when().
                get("/demouser").
                then().
                statusCode(401);
    }

    @Test
    public void testDemoUserLogin() {
        //First, make a login to get the token for the Authorization, saving the response body in String json
        String json = given().
                contentType("application/json").
                body("{'username':'user','password':'test'}").
                when().
                post("/login").
                then().
                statusCode(200).extract().asString();

        //Then test /demouser URL with the correct token extracted from the JSON string.
        given().
                contentType("application/json").
                header("Authorization", "Bearer " + from(json).get("token")).
                when().
                get("/demouser").
                then().
                statusCode(200);
        //And test that the user cannot access /demoadmin rest service
        given().
                contentType("application/json").
                header("Authorization", "Bearer " + from(json).get("token")).
                when().
                get("/admin").
                then().
                statusCode(403).
                body("error.message", equalTo("You are not authorized to perform the requested operation"));
    }
}

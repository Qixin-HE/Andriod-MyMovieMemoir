/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restm3.service;


import java.sql.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import restm3.Credentials;
import restm3.Person;

/**
 *
 * @author zoe
 */
@Stateless
@Path("restm3.credentials")
public class CredentialsFacadeREST extends AbstractFacade<Credentials> {

    @PersistenceContext(unitName = "29217814-A1PU")
    private EntityManager em;

    public CredentialsFacadeREST() {
        super(Credentials.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_JSON})
    public void create(Credentials entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Credentials entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Credentials find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_JSON})
    public List<Credentials> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Credentials> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    //task3-a
    @GET
    @Path("findByUsername/{username}")
    @Produces({"application/json"})
    public List<Credentials> findByPostcode(@PathParam("username") String username) {
            Query query = em.createNamedQuery("Credentials.findByUsername");
            query.setParameter("username",username);
            return query.getResultList();
        }
    
    @GET
    @Path("findByPasswordhash/{passwordhash}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Credentials> findByPasswordhash(@PathParam("passwordhash") String passwordhash) {
            Query query = em.createNamedQuery("Credentials.findByPasswordhash");
            query.setParameter("passwordhash",passwordhash);
            return query.getResultList();
        }
        
    @GET
    @Path("findBySignupdate/{signupdate}")
    @Produces({"application/json"})
    public List<Credentials> findBySignupdate(@PathParam("signupdate") Date signupdate) {
            Query query = em.createNamedQuery("Credentials.findBySignupdate");
            query.setParameter("signupdate",signupdate);
            return query.getResultList();
        }
    
    //a2
    @GET
    @Path("findByUsernameAndPassword/{username}/{passwordhash}")
    @Produces({"application/json"})
    public List<Credentials> findByUsernameAndPassword(@PathParam("username") String username, @PathParam("passwordhash") String passwordhash) {
            TypedQuery<Credentials> query = em.createQuery("SELECT s FROM Credentials s "
            + "WHERE s.username = :username and s.passwordhash = :passwordhash", Credentials.class);
    query.setParameter("username",username);
    query.setParameter("passwordhash",passwordhash);
    return query.getResultList();
        }
    
    @GET
    @Path("countId")
    @Produces({"application/json"})
    public String countId() {
            String id = countREST();
    return id;
        }
    
    @GET
    @Path("getCredentialsIdByUsername/{username}")
    @Produces({"application/json"})
    public String getCredentialsIdByUsername(@PathParam("username") String username) {
            TypedQuery<Credentials> query = em.createQuery("SELECT c FROM Credentials c "
            + "WHERE c.username = :username", Credentials.class);
    query.setParameter("username",username);
    
    String credentialsid = ((Credentials)query.getSingleResult()).getId().toString();
    
    return credentialsid;
        }
}

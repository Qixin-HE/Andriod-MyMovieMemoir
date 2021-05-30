/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restm3.service;

import java.sql.Date;
import java.text.SimpleDateFormat;
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
@Path("restm3.person")
public class PersonFacadeREST extends AbstractFacade<Person> {

    @PersistenceContext(unitName = "29217814-A1PU")
    private EntityManager em;

    public PersonFacadeREST() {
        super(Person.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_JSON})
    public void create(Person entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Person entity) {
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
    public Person find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_JSON})
    public List<Person> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Person> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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

    @GET
    @Path("findByFname/{fname}")
    @Produces({"application/json"})
    public List<Person> findByCinemaid(@PathParam("fname") String fname) {
        Query query = em.createNamedQuery("Person.findByFname");
        query.setParameter("fname", fname);
        return query.getResultList();
    }

    @GET
    @Path("findByLname/{lname}")
    @Produces({"application/json"})
    public List<Person> findByLname(@PathParam("lname") String lname) {
        Query query = em.createNamedQuery("Person.findByLname");
        query.setParameter("lname", lname);
        return query.getResultList();
    }

    @GET
    @Path("findByGender/{gender}")
    @Produces({"application/json"})
    public List<Person> findByGender(@PathParam("gender") String gender1) {
        Query query = em.createNamedQuery("Person.findByGender");
        char gender = gender1.charAt(0);
        query.setParameter("gender", gender);
        return query.getResultList();
    }

    @GET
    @Path("findByDob/{dob}")
    @Produces({"application/json"})
    public List<Person> findByDob(@PathParam("dob") Date dob) {
        Query query = em.createNamedQuery("Person.findByDob");
        query.setParameter("dob", dob);
        return query.getResultList();
    }

    @GET
    @Path("findBySnumber/{snumber}")
    @Produces({"application/json"})
    public List<Person> findBySnumber(@PathParam("dob") Integer snumber) {
        Query query = em.createNamedQuery("Person.findBySnumber");
        query.setParameter("snumber", snumber);
        return query.getResultList();
    }

    @GET
    @Path("findBySname/{snumber}")
    @Produces({"application/json"})
    public List<Person> findBySname(@PathParam("sname") String sname) {
        Query query = em.createNamedQuery("Person.findBySname");
        query.setParameter("sname", sname);
        return query.getResultList();
    }

    @GET
    @Path("findByState/{state}")
    @Produces({"application/json"})
    public List<Person> findByState(@PathParam("state") String state) {
        Query query = em.createNamedQuery("Person.findByState");
        query.setParameter("state", state);
        return query.getResultList();
    }

    @GET
    @Path("findByPostcode/{postcode}")
    @Produces({"application/json"})
    public List<Person> findByPostcode(@PathParam("postcode") String postcode) {
        Query query = em.createNamedQuery("Person.findByPostcode");
        query.setParameter("postcode", postcode);
        return query.getResultList();
    }

    //Person.findByCredentialsid
    @GET
    @Path("findByCredentialsid/{credentialsid}")
    @Produces({"application/json"})
    public List<Person> findByCredentialsid(@PathParam("credentialsid") Integer credentialsid) {
        Query query = em.createNamedQuery("Person.findByCredentialsid");
        query.setParameter("credentialsid", credentialsid);
        return query.getResultList();
    }

    //task3-b
    @GET
    @Path("findByAdressANDStateANDPostcode/{snumber}/{sname}/{postcode}")
    @Produces({"application/json"})
    public List<Person> findByAdressANDStateANDPostcode(@PathParam("snumber") Integer snumber, @PathParam("sname") String sname, @PathParam("postcode") String postcode) {
        TypedQuery<Person> query = em.createQuery("SELECT s FROM Person s WHERE s.sname = :sname and s.snumber = :snumber and s.postcode = :postcode", Person.class);
        query.setParameter("snumber", snumber);
        query.setParameter("sname", sname);
        query.setParameter("postcode", postcode);
        return query.getResultList();
    }

    //a2
    @GET
    @Path("countId")
    @Produces({"application/json"})
    public String countId() {
        String id = countREST();
        return id;
    }

    @GET
    @Path("getUsernameById/{id}")
    @Produces({"application/json"})
    public String getUsernameById(@PathParam("id") Integer id) {
        Query query = em.createNamedQuery("Credentials.findById");
        query.setParameter("id", id);
       
        String username = ((Credentials) query.getSingleResult()).getUsername();
        
        return username;
    }

    @GET
    @Path("getHashById/{id}")
    @Produces({"application/json"})
    public String getHashById(@PathParam("id") Integer id) {
        Query query = em.createNamedQuery("Credentials.findById");
        query.setParameter("id", id);
       
        String username = ((Credentials) query.getSingleResult()).getPasswordhash();
        
        return username;
    }
    @GET
    @Path("getSignupdateById/{id}")
    @Produces({"application/json"})
    public String getSignupdateById(@PathParam("id") Integer id) {
        Query query = em.createNamedQuery("Credentials.findById");
        query.setParameter("id", id);
       
        String username = ((Credentials) query.getSingleResult()).getSignupdate().toString();
        
        return username;
    }
    
    @GET
    @Path("getPersonFnameByCredentialsId/{credentialsid}")
    @Produces({"application/json"})
    public String getPersonFnameByCredentialsId(@PathParam("credentialsid") Integer credentialsid) {
            Query query = em.createNamedQuery("Person.getPersonFnameByCredentialsId");
    query.setParameter("credentialsid",credentialsid);
    
    String personfname = ((Person)query.getSingleResult()).getFname().toString();
    
    return personfname;
        }
    
    
    @GET
    @Path("getPersonFnameByCredentialsId2/{credentialsid}")
    @Produces({"application/json"})
    public String getPersonFnameByCredentialsId2(@PathParam("credentialsid") Integer credentialsid) {
            
        TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p "
            + "WHERE p.credentialsid.id = :credentialsid", Person.class);
    query.setParameter("credentialsid",credentialsid);
    Person signinperson = query.getSingleResult();
    String fname = signinperson.getFname(); 
    
    return fname;
        }
    
}

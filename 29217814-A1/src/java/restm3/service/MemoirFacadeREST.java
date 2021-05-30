/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restm3.service;


import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Int;
import entities.CinemaMemoirPerson;
import java.math.BigDecimal;
import java.sql.Date;

import java.sql.Time;
import java.text.SimpleDateFormat;

import java.util.List;

import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
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
import restm3.Cinema;
import restm3.Credentials;
import restm3.Memoir;
import restm3.Person;


/**
 *
 * @author zoe
 */
@Stateless
@Path("restm3.memoir")
public class MemoirFacadeREST extends AbstractFacade<Memoir> {

    @PersistenceContext(unitName = "29217814-A1PU")
    private EntityManager em;

    public MemoirFacadeREST() {
        super(Memoir.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Memoir entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Memoir entity) {
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
    public Memoir find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Memoir> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Memoir> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    @Path("findByMname/{mname}")
    @Produces({"application/json"})
    public List<Memoir> findByMname(@PathParam("mname") String mname) {
            Query query = em.createNamedQuery("Memoir.findByMname");
            query.setParameter("mname",mname);
            return query.getResultList();
        }
    
    @GET
    @Path("findByMreleasedate/{mreleasedate}")
    @Produces({"application/json"})
    public List<Memoir> findByMreleasedate(@PathParam("mreleasedate") Date mreleasedate) {
            Query query = em.createNamedQuery("Memoir.findByMreleasedate");
            query.setParameter("mreleasedate",mreleasedate);
            return query.getResultList();
        }
    
    @GET
    @Path("findByWdate/{wdate}")
    @Produces({"application/json"})
    public List<Memoir> findByWdate(@PathParam("wdate") Date wdate) {
            Query query = em.createNamedQuery("Memoir.findByWdate");
            query.setParameter("wdate",wdate);
            return query.getResultList();
        }
    
    @GET
    @Path("findByWtime/{wtime}")
    @Produces({"application/json"})
    public List<Memoir> findByWtime(@PathParam("wtime") Time wtime) {
            Query query = em.createNamedQuery("Memoir.findByWtime");
            query.setParameter("wtime",wtime);
            return query.getResultList();
        }
    
    @GET
    @Path("findByComment/{comment}")
    @Produces({"application/json"})
    public List<Memoir> findByComment(@PathParam("comment") String comment) {
            Query query = em.createNamedQuery("Memoir.findByComment");
            query.setParameter("comment",comment);
            return query.getResultList();
        }
    
    @GET
    @Path("findByScore/{score}")
    @Produces({"application/json"})
    public List<Memoir> findByScore(@PathParam("score") Double score) {
            Query query = em.createNamedQuery("Memoir.findByScore");
            query.setParameter("score",score);
            return query.getResultList();
        }
    
    //findByPersonid
    @GET
    @Path("findByPersonid/{personid}")
    @Produces({"application/json"})
    public List<Memoir> findByPersonid(@PathParam("personid") Integer personid) {
            Query query = em.createNamedQuery("Memoir.findByPersonid");
            query.setParameter("personid",personid);
            return query.getResultList();
        }
    
    @GET
    @Path("findByCinemaid/{cinemaid}")
    @Produces({"application/json"})
    public List<Memoir> findByCinemaid(@PathParam("cinemaid") Integer id) {
            Query query = em.createNamedQuery("Memoir.findByCinemaid");
            query.setParameter("id",id);
            return query.getResultList();
        }
    
    //task3-c
    @GET
    @Path("findByMnameANDCinimaid/{id}/{mname}")
    @Produces({"application/json"})
    public List<Memoir> findByMnameANDCinemaid(@PathParam("id") Integer id, @PathParam("mname") String mname) {
    TypedQuery<Memoir> query = em.createQuery("SELECT s FROM Memoir s "
            + "WHERE s.cinemaid.id = :id and s.mname = :mname", Memoir.class);
    query.setParameter("id",id);
    query.setParameter("mname",mname);
    return query.getResultList();
 }
    
    //task3-d
    @GET
    @Path("findByMnameANDCinimaid2/{id}/{mname}")
    @Produces({"application/json"})
    public List<Memoir> findByMnameANDCinemaid2(@PathParam("id") Integer id, @PathParam("mname") String mname) {
    Query query = em.createNamedQuery("Memoir.findByMnameANDCinemaid2");
    query.setParameter("id",id);
    query.setParameter("mname",mname);
    return query.getResultList();
 }
    
    //Task4-a
    @GET
 @Path("1-countMovieWatchedPerPostcode/{personid}/{startdate}/{enddate}")
 @Produces({MediaType.APPLICATION_JSON})
 public List<CinemaMemoirPerson> countMovieWatchedPerPostcode(@PathParam("personid") Integer personid,@PathParam("sdate") Date sdate,@PathParam("edate") Date edate){
 TypedQuery<CinemaMemoirPerson> q = em.createQuery("Select count (distinct s.mname),s.cinemaid.postcode "
         + "from (select new entities.CinemaMemoirPerson(s.personid.id,s.cinemaid.postcode,s.wdate) From Memoir As s)"
         + "where s.personid.id = :personid and wdate > :sdate and wdate < :edate group by s.cinemaid.postcode ", CinemaMemoirPerson.class);
    q.setParameter("personid",personid);
    q.setParameter("sdate",sdate);
    q.setParameter("edate",edate);
 return q.getResultList();
 }
   
 //actually a1-Task 4 D
    @GET
    @Path("getSameYearWatchedMovies/{personid}")
    @Produces({"application/json"})
    public Object getSameYearWatchedMovies(@PathParam("personid") Integer personid) {
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
        
        Query query = em.createQuery("Select m FROM Memoir m " 
                + "where m.personid.id = :personid and EXTRACT (YEAR m.wdate) = EXTRACT(YEAR m.mreleasedate) ");
        query.setParameter("personid", personid);
        List<Memoir> sameyearlist = query.getResultList();
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (Memoir m :sameyearlist) {
            JsonObject yearObject = Json.createObjectBuilder()
                    .add("MovieName", m.getMname()).add("ReleaseYear", (String)yearFormat.format(m.getMreleasedate())).build();
        arrayBuilder.add(yearObject);
        
        }
        JsonArray jArray = arrayBuilder.build();
        
        return jArray;
    }
    /*
    @GET
    @Path("getSameYearWatchedMovies3/{personid}")
    @Produces({"application/json"})
    public List<Memoir> getSameYearWatchedMovies3(@PathParam("personid") Integer personid) {
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
        
        Query query = em.createQuery("Select m FROM Memoir m " 
                + "where m.personid.id = :personid and EXTRACT (YEAR m.wdate) = EXTRACT(YEAR m.mreleasedate) and EXTRACT(YEAR m.mreleasedate) = '2020'");
        query.setParameter("personid", personid);
        List<Memoir> sameyearlist = query.getResultList();
        
        return sameyearlist;
    }
    
    @GET
    @Path("getSameYearWatchedMovies2/{personid}")
    @Produces({"application/json"})
    public List<Memoir> getSameYearWatchedMovies2(@PathParam("personid") Integer personid) {
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
        
        Query query = em.createQuery("Select m FROM Memoir m where m.personid.id = :personid");
        query.setParameter("personid", personid);
        List<Memoir> sameyearlist = query.getResultList();
        
        return sameyearlist;
    }
*/
    //a3
    @GET
    @Path("get2020TopWatchedMovie/{personid}")
    @Produces({"application/json"})
    public Object get2020TopWatchedMovie(@PathParam("personid") Integer personid) {
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
        
        Query query = em.createQuery("Select m FROM Memoir m " 
                + "where m.personid.id = :personid and EXTRACT (YEAR m.wdate) = EXTRACT(YEAR m.mreleasedate) and EXTRACT(YEAR m.mreleasedate) = '2020'");
        query.setParameter("personid", personid);
        List<Memoir> sameyearlist = query.getResultList();
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (Memoir m :sameyearlist) {
            JsonObject yearObject = Json.createObjectBuilder()
                    .add("MovieName", m.getMname()).add("ReleaseYear", (String)yearFormat.format(m.getMreleasedate())).add("RatingScore",String.valueOf(m.getScore())).build();
        arrayBuilder.add(yearObject);
        
        }
        JsonArray jArray = arrayBuilder.build();
        
        return jArray;
    }
    
    //task8
    @GET
    @Path("getMoviememoir/{personid}")
    @Produces({"application/json"})
    public Object getMoviememoir(@PathParam("personid") Integer personid) {
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
        
        Query query = em.createQuery("Select m FROM Memoir m " 
                + "where m.personid.id = :personid and m.cinemaid.id = m.cinemaid.id");
        query.setParameter("personid", personid);
        List<Memoir> memoirlist = query.getResultList();
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (Memoir m :memoirlist) {
            Query query2 = em.createQuery("Select c FROM Cinema c " 
                + "where c.id = :id");
            int cid = m.getCinemaid().getId();
        query2.setParameter("id", cid);
        Cinema cinema = (Cinema)query2.getSingleResult();
            JsonObject yearObject = Json.createObjectBuilder()
                    .add("MOVIE", m.getMname())
                    .add("RDATE",(String)yearFormat.format(m.getMreleasedate()))
                    .add("WDATE",(String)yearFormat.format(m.getWdate()))
                    .add("POSTCODE",cinema.getPostcode())
                    .add("COMMENT",m.getComment())
                    .add("RatingScore",String.valueOf(m.getScore()))
                    //{"MOVIE", "RDATE", "IMG", "WDATE", "POSTCODE", "COMMENT", "STARIMG",};
                    
                    .build();
        arrayBuilder.add(yearObject);
        
        }
        JsonArray jArray = arrayBuilder.build();
        
        return jArray;
    }
    
}

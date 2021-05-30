/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;
/**
 *
 * @author zoe
 */
@XmlRootElement
public class CinemaMemoirPerson {
    String mname;
    String postcode;
    Integer id;
    Date wdate;
    
public String getMname() {
    return mname;
}

public void setMname(String mname){
    this.mname = mname;
}

public String getPostcode() {
    return postcode;
}

public void setPostcode(String postcode){
    this.postcode = postcode;
}

public Integer getId() {
    return id;
}

public void setId(Integer id){
    this.id = id;
}

public Date getWdate() {
    return wdate;
}

public void setWdate(Date wdate){
    this.wdate = wdate;
}

public CinemaMemoirPerson() {
    
}

public CinemaMemoirPerson(String mname,String postcode,Integer id,Date wdate) {
    this.mname = mname;
    this.postcode = postcode;
    this.id = id;
    this.wdate = wdate;
}

public CinemaMemoirPerson(Integer id,String postcode,Date wdate) {
    this.id = id;
    this.postcode = postcode;
    
    this.wdate = wdate;
}
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restm3;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author zoe
 */
@Entity
@Table(name = "PERSON")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Person.findAll", query = "SELECT p FROM Person p")
    , @NamedQuery(name = "Person.findById", query = "SELECT p FROM Person p WHERE p.id = :id")
    , @NamedQuery(name = "Person.findByFname", query = "SELECT p FROM Person p WHERE p.fname = :fname")
    , @NamedQuery(name = "Person.findByLname", query = "SELECT p FROM Person p WHERE p.lname = :lname")
    , @NamedQuery(name = "Person.findByGender", query = "SELECT p FROM Person p WHERE p.gender = :gender")
    , @NamedQuery(name = "Person.findByDob", query = "SELECT p FROM Person p WHERE p.dob = :dob")
    , @NamedQuery(name = "Person.findBySnumber", query = "SELECT p FROM Person p WHERE p.snumber = :snumber")
    , @NamedQuery(name = "Person.findBySname", query = "SELECT p FROM Person p WHERE p.sname = :sname")
    , @NamedQuery(name = "Person.findByState", query = "SELECT p FROM Person p WHERE p.state = :state")
    , @NamedQuery(name = "Person.findByPostcode", query = "SELECT p FROM Person p WHERE p.postcode = :postcode")
    , @NamedQuery(name = "Person.findByCredentialsid", query = "SELECT p FROM Person p WHERE p.credentialsid.id = :credentialsid")
    , @NamedQuery(name = "Person.getUsernameById", query = "SELECT s FROM Credentials s WHERE s.id = :id")
    , @NamedQuery(name = "Person.getPersonFnameByCredentialsId", query = "SELECT p FROM Person p WHERE p.credentialsid.id = :credentialsid")})
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "FNAME")
    private String fname;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "LNAME")
    private String lname;
    @Basic(optional = false)
    @NotNull
    @Column(name = "GENDER")
    private Character gender;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DOB")
    @Temporal(TemporalType.DATE)
    private Date dob;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SNUMBER")
    private int snumber;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "SNAME")
    private String sname;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "STATE")
    private String state;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "POSTCODE")
    private String postcode;
    @JoinColumn(name = "CREDENTIALSID", referencedColumnName = "ID")
    @ManyToOne
    private Credentials credentialsid;
    @OneToMany(mappedBy = "personid")
    private Collection<Memoir> memoirCollection;

    public Person() {
    }

    public Person(Integer id) {
        this.id = id;
    }

    public Person(Integer id, String fname, String lname, Character gender, Date dob, int snumber, String sname, String state, String postcode) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.gender = gender;
        this.dob = dob;
        this.snumber = snumber;
        this.sname = sname;
        this.state = state;
        this.postcode = postcode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public Character getGender() {
        return gender;
    }

    public void setGender(Character gender) {
        this.gender = gender;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public int getSnumber() {
        return snumber;
    }

    public void setSnumber(int snumber) {
        this.snumber = snumber;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public Credentials getCredentialsid() {
        return credentialsid;
    }

    public void setCredentialsid(Credentials credentialsid) {
        this.credentialsid = credentialsid;
    }

    @XmlTransient
    public Collection<Memoir> getMemoirCollection() {
        return memoirCollection;
    }

    public void setMemoirCollection(Collection<Memoir> memoirCollection) {
        this.memoirCollection = memoirCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Person)) {
            return false;
        }
        Person other = (Person) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "restm3.Person[ id=" + id + " ]";
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restm3;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author zoe
 */
@Entity
@Table(name = "MEMOIR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Memoir.findAll", query = "SELECT m FROM Memoir m")
    , @NamedQuery(name = "Memoir.findById", query = "SELECT m FROM Memoir m WHERE m.id = :id")
    , @NamedQuery(name = "Memoir.findByMname", query = "SELECT m FROM Memoir m WHERE m.mname = :mname")
    , @NamedQuery(name = "Memoir.findByMreleasedate", query = "SELECT m FROM Memoir m WHERE m.mreleasedate = :mreleasedate")
    , @NamedQuery(name = "Memoir.findByWdate", query = "SELECT m FROM Memoir m WHERE m.wdate = :wdate")
    , @NamedQuery(name = "Memoir.findByWtime", query = "SELECT m FROM Memoir m WHERE m.wtime = :wtime")
    , @NamedQuery(name = "Memoir.findByComment", query = "SELECT m FROM Memoir m WHERE m.comment = :comment")
    , @NamedQuery(name = "Memoir.findByScore", query = "SELECT m FROM Memoir m WHERE m.score = :score")
    , @NamedQuery(name = "Memoir.findByPersonid", query ="select m from Memoir m where m.personid.id = :personid")
    , @NamedQuery(name = "Memoir.findByMnameANDCinemaid2", query ="select m from Memoir m where m.cinemaid.id = :id and m.mname= :mname")
    , @NamedQuery(name = "Memoir.findByCinemaid", query ="select m from Memoir m where m.cinemaid.id = :id")
})

//Memoir.findByCinemaid
//findByMnameANDCinemaid2
public class Memoir implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "MNAME")
    private String mname;
    @Basic(optional = false)
    @NotNull
    @Column(name = "MRELEASEDATE")
    @Temporal(TemporalType.DATE)
    private Date mreleasedate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "WDATE")
    @Temporal(TemporalType.DATE)
    private Date wdate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "WTIME")
    @Temporal(TemporalType.TIME)
    private Date wtime;
    @Size(max = 300)
    @Column(name = "COMMENT")
    private String comment;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SCORE")
    private double score;
    @JoinColumn(name = "CINEMAID", referencedColumnName = "ID")
    @ManyToOne
    private Cinema cinemaid;
    @JoinColumn(name = "PERSONID", referencedColumnName = "ID")
    @ManyToOne
    private Person personid;

    public Memoir() {
    }

    public Memoir(Integer id) {
        this.id = id;
    }

    public Memoir(Integer id, String mname, Date mreleasedate, Date wdate, Date wtime, double score) {
        this.id = id;
        this.mname = mname;
        this.mreleasedate = mreleasedate;
        this.wdate = wdate;
        this.wtime = wtime;
        this.score = score;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public Date getMreleasedate() {
        return mreleasedate;
    }

    public void setMreleasedate(Date mreleasedate) {
        this.mreleasedate = mreleasedate;
    }

    public Date getWdate() {
        return wdate;
    }

    public void setWdate(Date wdate) {
        this.wdate = wdate;
    }

    public Date getWtime() {
        return wtime;
    }

    public void setWtime(Date wtime) {
        this.wtime = wtime;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public Cinema getCinemaid() {
        return cinemaid;
    }

    public void setCinemaid(Cinema cinemaid) {
        this.cinemaid = cinemaid;
    }

    public Person getPersonid() {
        return personid;
    }

    public void setPersonid(Person personid) {
        this.personid = personid;
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
        if (!(object instanceof Memoir)) {
            return false;
        }
        Memoir other = (Memoir) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "restm3.Memoir[ id=" + id + " ]";
    }
    
}

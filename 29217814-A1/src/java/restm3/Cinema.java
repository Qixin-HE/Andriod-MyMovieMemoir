/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restm3;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author zoe
 */
@Entity
@Table(name = "CINEMA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cinema.findAll", query = "SELECT c FROM Cinema c")
    , @NamedQuery(name = "Cinema.findById", query = "SELECT c FROM Cinema c WHERE c.id = :id")
    , @NamedQuery(name = "Cinema.findByName", query = "SELECT c FROM Cinema c WHERE c.name = :name")
    , @NamedQuery(name = "Cinema.findByPostcode", query = "SELECT c FROM Cinema c WHERE c.postcode = :postcode")})
public class Cinema implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "NAME")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "POSTCODE")
    private String postcode;
    @OneToMany(mappedBy = "cinemaid")
    private Collection<Memoir> memoirCollection;

    public Cinema() {
    }

    public Cinema(Integer id) {
        this.id = id;
    }

    public Cinema(Integer id, String name, String postcode) {
        this.id = id;
        this.name = name;
        this.postcode = postcode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
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
        if (!(object instanceof Cinema)) {
            return false;
        }
        Cinema other = (Cinema) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "restm3.Cinema[ id=" + id + " ]";
    }
    
}

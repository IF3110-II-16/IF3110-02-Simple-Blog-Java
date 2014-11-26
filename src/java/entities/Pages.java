/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
 * @author steve
 */
@Entity
@Table(name = "pages")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pages.findAll", query = "SELECT p FROM Pages p"),
    @NamedQuery(name = "Pages.findById", query = "SELECT p FROM Pages p WHERE p.id = :id"),
    @NamedQuery(name = "Pages.findByContent", query = "SELECT p FROM Pages p WHERE p.content = :content"),
    @NamedQuery(name = "Pages.findByTitle", query = "SELECT p FROM Pages p WHERE p.title = :title"),
    @NamedQuery(name = "Pages.findByPublish", query = "SELECT p FROM Pages p WHERE p.publish = :publish"),
    @NamedQuery(name = "Pages.findByDate", query = "SELECT p FROM Pages p WHERE p.date = :date"),
    @NamedQuery(name = "Pages.findBySoftDelete", query = "SELECT p FROM Pages p WHERE p.softDelete = :softDelete")})
public class Pages implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "content")
    private String content;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "title")
    private String title;
    @Basic(optional = false)
    @NotNull
    @Column(name = "publish")
    private boolean publish;
    @Basic(optional = false)
    @NotNull
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
    @Basic(optional = false)
    @NotNull
    @Column(name = "soft_delete")
    private boolean softDelete;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pageId")
    private Collection<Comments> commentsCollection;
    @JoinColumn(name = "owner", referencedColumnName = "username")
    @ManyToOne(optional = false)
    private User owner;

    public Pages() {
    }

    public Pages(Integer id) {
        this.id = id;
    }

    public Pages(Integer id, String content, String title, boolean publish, Date date, boolean softDelete) {
        this.id = id;
        this.content = content;
        this.title = title;
        this.publish = publish;
        this.date = date;
        this.softDelete = softDelete;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean getPublish() {
        return publish;
    }

    public void setPublish(boolean publish) {
        this.publish = publish;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean getSoftDelete() {
        return softDelete;
    }

    public void setSoftDelete(boolean softDelete) {
        this.softDelete = softDelete;
    }

    @XmlTransient
    public Collection<Comments> getCommentsCollection() {
        return commentsCollection;
    }

    public void setCommentsCollection(Collection<Comments> commentsCollection) {
        this.commentsCollection = commentsCollection;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
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
        if (!(object instanceof Pages)) {
            return false;
        }
        Pages other = (Pages) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Pages[ id=" + id + " ]";
    }
    
}

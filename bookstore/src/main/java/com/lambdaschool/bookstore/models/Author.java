package com.lambdaschool.bookstore.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lambdaschool.bookstore.logging.Loggable;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Loggable
@Entity
@Table(name = "authors")
public class Author extends Auditable
{
    @ApiModelProperty(name = "authorid", value = "primary key for Author", required = true, example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long authorid;

    @ApiModelProperty(name = "fname", value = "Author's First Name", example = "Java")
    private String fname;

    @ApiModelProperty(name = "lname", value = "Author's Last Name", example = "Spring")
    private String lname;

    @OneToMany(mappedBy = "author",
            cascade = CascadeType.ALL)
    @JsonIgnoreProperties("author")
    private List<Wrote> wrote = new ArrayList<>();

    public Author()
    {
    }

    public Author(String fname, String lname)
    {
        this.fname = fname;
        this.lname = lname;
    }

    public long getAuthorid()
    {
        return authorid;
    }

    public void setAuthorid(long authorid)
    {
        this.authorid = authorid;
    }

    public String getLastname()
    {
        return lname;
    }

    public void setLastname(String lastname)
    {
        this.lname = lastname;
    }

    public String getFirstname()
    {
        return fname;
    }

    public void setFirstname(String firstname)
    {
        this.fname = firstname;
    }

    public List<Wrote> getWrote()
    {
        return wrote;
    }

    public void setWrote(List<Wrote> wrote)
    {
        this.wrote = wrote;
    }

    @Override
    public String toString()
    {
        return "Author{" +
                "authorid=" + authorid +
                ", lastname='" + lname + '\'' +
                ", firstname='" + fname + '\'' +
                ", wrote=" + wrote +
                '}';
    }
}

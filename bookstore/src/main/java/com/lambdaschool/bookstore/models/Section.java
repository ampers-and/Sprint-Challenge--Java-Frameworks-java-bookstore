package com.lambdaschool.bookstore.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lambdaschool.bookstore.logging.Loggable;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Loggable
@Entity
@Table(name = "sections")
public class Section extends Auditable
{
    @ApiModelProperty(name = "sectionid", value = "primary key for Section", required = true, example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long sectionid;

    @ApiModelProperty(name = "sectionname", value = "Section Name", required = true, example = "Genre")
    @Column(nullable = false,
            unique = true)
    private String sectionname;

    @OneToMany(mappedBy = "section",
            cascade = CascadeType.ALL)
    @JsonIgnoreProperties("section")
    private List<Book> books = new ArrayList<>();

    public Section()
    {
    }

    public Section(String sectionname)
    {
        this.sectionname = sectionname;
    }

    public long getSectionid()
    {
        return sectionid;
    }

    public void setSectionid(long sectionid)
    {
        this.sectionid = sectionid;
    }

    public String getSectionname()
    {
        return sectionname;
    }

    public void setSectionname(String sectionname)
    {
        this.sectionname = sectionname;
    }

    public List<Book> getBooks()
    {
        return books;
    }

    public void setBooks(List<Book> books)
    {
        this.books = books;
    }

    @Override
    public String toString()
    {
        return "Section{" +
                "sectionid=" + sectionid +
                ", sectionname='" + sectionname + '\'' +
                ", books=" + books +
                '}';
    }
}

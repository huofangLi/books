package com.books.server.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * 读书表
 */
@ApiModel(description = "读书表")
@Entity
@Table(name = "reading")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Reading implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "page")
    private Integer page;

    @Column(name = "create_time")
    private ZonedDateTime createTime;

    @ManyToOne
    @JsonIgnoreProperties("readings")
    private Book bookId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPage() {
        return page;
    }

    public Reading page(Integer page) {
        this.page = page;
        return this;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public ZonedDateTime getCreateTime() {
        return createTime;
    }

    public Reading createTime(ZonedDateTime createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(ZonedDateTime createTime) {
        this.createTime = createTime;
    }

    public Book getBookId() {
        return bookId;
    }

    public Reading bookId(Book book) {
        this.bookId = book;
        return this;
    }

    public void setBookId(Book book) {
        this.bookId = book;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Reading)) {
            return false;
        }
        return id != null && id.equals(((Reading) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Reading{" +
            "id=" + getId() +
            ", page=" + getPage() +
            ", createTime='" + getCreateTime() + "'" +
            "}";
    }
}

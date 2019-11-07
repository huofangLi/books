package com.books.server.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * 书摘
 */
@ApiModel(description = "书摘")
@Entity
@Table(name = "book_summary")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BookSummary implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "comment")
    private String comment;

    @Column(name = "create_time")
    private ZonedDateTime createTime;

    @ManyToOne
    @JsonIgnoreProperties("bookSummaries")
    private Book bookId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public BookSummary comment(String comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public ZonedDateTime getCreateTime() {
        return createTime;
    }

    public BookSummary createTime(ZonedDateTime createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(ZonedDateTime createTime) {
        this.createTime = createTime;
    }

    public Book getBookId() {
        return bookId;
    }

    public BookSummary bookId(Book book) {
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
        if (!(o instanceof BookSummary)) {
            return false;
        }
        return id != null && id.equals(((BookSummary) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "BookSummary{" +
            "id=" + getId() +
            ", comment='" + getComment() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            "}";
    }
}

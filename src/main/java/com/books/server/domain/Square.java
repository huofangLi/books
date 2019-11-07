package com.books.server.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * 广场
 */
@ApiModel(description = "广场")
@Entity
@Table(name = "square")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Square implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "book_saying")
    private String bookSaying;

    @Column(name = "comment")
    private String comment;

    @Column(name = "create_time")
    private ZonedDateTime createTime;

    @ManyToOne
    @JsonIgnoreProperties("squares")
    private Square parentId;

    @ManyToOne
    @JsonIgnoreProperties("squares")
    private Book bookId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public Square userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getBookSaying() {
        return bookSaying;
    }

    public Square bookSaying(String bookSaying) {
        this.bookSaying = bookSaying;
        return this;
    }

    public void setBookSaying(String bookSaying) {
        this.bookSaying = bookSaying;
    }

    public String getComment() {
        return comment;
    }

    public Square comment(String comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public ZonedDateTime getCreateTime() {
        return createTime;
    }

    public Square createTime(ZonedDateTime createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(ZonedDateTime createTime) {
        this.createTime = createTime;
    }

    public Square getParentId() {
        return parentId;
    }

    public Square parentId(Square square) {
        this.parentId = square;
        return this;
    }

    public void setParentId(Square square) {
        this.parentId = square;
    }

    public Book getBookId() {
        return bookId;
    }

    public Square bookId(Book book) {
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
        if (!(o instanceof Square)) {
            return false;
        }
        return id != null && id.equals(((Square) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Square{" +
            "id=" + getId() +
            ", userId=" + getUserId() +
            ", bookSaying='" + getBookSaying() + "'" +
            ", comment='" + getComment() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            "}";
    }
}

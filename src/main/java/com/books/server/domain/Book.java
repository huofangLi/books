package com.books.server.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * 书籍信息
 */
@ApiModel(description = "书籍信息")
@Entity
@Table(name = "book")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "book_name")
    private String bookName;

    @Column(name = "book_image")
    private String bookImage;

    @Column(name = "book_pages")
    private Long bookPages;

    @Column(name = "is_borrow")
    private Boolean isBorrow;

    @Column(name = "create_time")
    private ZonedDateTime createTime;

    @ManyToOne
    @JsonIgnoreProperties("books")
    private BookClassification bookClassificationId;

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

    public Book userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getBookName() {
        return bookName;
    }

    public Book bookName(String bookName) {
        this.bookName = bookName;
        return this;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookImage() {
        return bookImage;
    }

    public Book bookImage(String bookImage) {
        this.bookImage = bookImage;
        return this;
    }

    public void setBookImage(String bookImage) {
        this.bookImage = bookImage;
    }

    public Long getBookPages() {
        return bookPages;
    }

    public Book bookPages(Long bookPages) {
        this.bookPages = bookPages;
        return this;
    }

    public void setBookPages(Long bookPages) {
        this.bookPages = bookPages;
    }

    public Boolean isIsBorrow() {
        return isBorrow;
    }

    public Book isBorrow(Boolean isBorrow) {
        this.isBorrow = isBorrow;
        return this;
    }

    public void setIsBorrow(Boolean isBorrow) {
        this.isBorrow = isBorrow;
    }

    public ZonedDateTime getCreateTime() {
        return createTime;
    }

    public Book createTime(ZonedDateTime createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(ZonedDateTime createTime) {
        this.createTime = createTime;
    }

    public BookClassification getBookClassificationId() {
        return bookClassificationId;
    }

    public Book bookClassificationId(BookClassification bookClassification) {
        this.bookClassificationId = bookClassification;
        return this;
    }

    public void setBookClassificationId(BookClassification bookClassification) {
        this.bookClassificationId = bookClassification;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Book)) {
            return false;
        }
        return id != null && id.equals(((Book) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Book{" +
            "id=" + getId() +
            ", userId=" + getUserId() +
            ", bookName='" + getBookName() + "'" +
            ", bookImage='" + getBookImage() + "'" +
            ", bookPages=" + getBookPages() +
            ", isBorrow='" + isIsBorrow() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            "}";
    }
}

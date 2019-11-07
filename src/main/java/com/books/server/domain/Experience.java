package com.books.server.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * 阅历
 */
@ApiModel(description = "阅历")
@Entity
@Table(name = "experience")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Experience implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "content")
    private Long content;

    @Column(name = "read_ok")
    private Integer readOk;

    @Column(name = "jhi_read")
    private Integer read;

    @Column(name = "note")
    private Integer note;

    @ManyToOne
    @JsonIgnoreProperties("experiences")
    private Reading readingId;

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

    public Experience userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getDuration() {
        return duration;
    }

    public Experience duration(Integer duration) {
        this.duration = duration;
        return this;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Long getContent() {
        return content;
    }

    public Experience content(Long content) {
        this.content = content;
        return this;
    }

    public void setContent(Long content) {
        this.content = content;
    }

    public Integer getReadOk() {
        return readOk;
    }

    public Experience readOk(Integer readOk) {
        this.readOk = readOk;
        return this;
    }

    public void setReadOk(Integer readOk) {
        this.readOk = readOk;
    }

    public Integer getRead() {
        return read;
    }

    public Experience read(Integer read) {
        this.read = read;
        return this;
    }

    public void setRead(Integer read) {
        this.read = read;
    }

    public Integer getNote() {
        return note;
    }

    public Experience note(Integer note) {
        this.note = note;
        return this;
    }

    public void setNote(Integer note) {
        this.note = note;
    }

    public Reading getReadingId() {
        return readingId;
    }

    public Experience readingId(Reading reading) {
        this.readingId = reading;
        return this;
    }

    public void setReadingId(Reading reading) {
        this.readingId = reading;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Experience)) {
            return false;
        }
        return id != null && id.equals(((Experience) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Experience{" +
            "id=" + getId() +
            ", userId=" + getUserId() +
            ", duration=" + getDuration() +
            ", content=" + getContent() +
            ", readOk=" + getReadOk() +
            ", read=" + getRead() +
            ", note=" + getNote() +
            "}";
    }
}

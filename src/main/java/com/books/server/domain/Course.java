package com.books.server.domain;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * 课程
 */
@ApiModel(description = "课程")
@Entity
@Table(name = "course")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Course implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "course_classification")
    private String courseClassification;

    @Column(name = "course_name")
    private String courseName;

    @Column(name = "course_image")
    private String courseImage;

    @Column(name = "course_describe")
    private String courseDescribe;

    @Column(name = "course_price")
    private Double coursePrice;

    @Column(name = "course_chapter")
    private String courseChapter;

    @Column(name = "course_introduction")
    private String courseIntroduction;

    @Column(name = "course_video")
    private String courseVideo;

    @Column(name = "presenter")
    private String presenter;

    @Column(name = "presenter_image")
    private String presenterImage;

    @Column(name = "presenter_introduction")
    private String presenterIntroduction;

    @Column(name = "create_time")
    private ZonedDateTime createTime;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCourseClassification() {
        return courseClassification;
    }

    public Course courseClassification(String courseClassification) {
        this.courseClassification = courseClassification;
        return this;
    }

    public void setCourseClassification(String courseClassification) {
        this.courseClassification = courseClassification;
    }

    public String getCourseName() {
        return courseName;
    }

    public Course courseName(String courseName) {
        this.courseName = courseName;
        return this;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseImage() {
        return courseImage;
    }

    public Course courseImage(String courseImage) {
        this.courseImage = courseImage;
        return this;
    }

    public void setCourseImage(String courseImage) {
        this.courseImage = courseImage;
    }

    public String getCourseDescribe() {
        return courseDescribe;
    }

    public Course courseDescribe(String courseDescribe) {
        this.courseDescribe = courseDescribe;
        return this;
    }

    public void setCourseDescribe(String courseDescribe) {
        this.courseDescribe = courseDescribe;
    }

    public Double getCoursePrice() {
        return coursePrice;
    }

    public Course coursePrice(Double coursePrice) {
        this.coursePrice = coursePrice;
        return this;
    }

    public void setCoursePrice(Double coursePrice) {
        this.coursePrice = coursePrice;
    }

    public String getCourseChapter() {
        return courseChapter;
    }

    public Course courseChapter(String courseChapter) {
        this.courseChapter = courseChapter;
        return this;
    }

    public void setCourseChapter(String courseChapter) {
        this.courseChapter = courseChapter;
    }

    public String getCourseIntroduction() {
        return courseIntroduction;
    }

    public Course courseIntroduction(String courseIntroduction) {
        this.courseIntroduction = courseIntroduction;
        return this;
    }

    public void setCourseIntroduction(String courseIntroduction) {
        this.courseIntroduction = courseIntroduction;
    }

    public String getCourseVideo() {
        return courseVideo;
    }

    public Course courseVideo(String courseVideo) {
        this.courseVideo = courseVideo;
        return this;
    }

    public void setCourseVideo(String courseVideo) {
        this.courseVideo = courseVideo;
    }

    public String getPresenter() {
        return presenter;
    }

    public Course presenter(String presenter) {
        this.presenter = presenter;
        return this;
    }

    public void setPresenter(String presenter) {
        this.presenter = presenter;
    }

    public String getPresenterImage() {
        return presenterImage;
    }

    public Course presenterImage(String presenterImage) {
        this.presenterImage = presenterImage;
        return this;
    }

    public void setPresenterImage(String presenterImage) {
        this.presenterImage = presenterImage;
    }

    public String getPresenterIntroduction() {
        return presenterIntroduction;
    }

    public Course presenterIntroduction(String presenterIntroduction) {
        this.presenterIntroduction = presenterIntroduction;
        return this;
    }

    public void setPresenterIntroduction(String presenterIntroduction) {
        this.presenterIntroduction = presenterIntroduction;
    }

    public ZonedDateTime getCreateTime() {
        return createTime;
    }

    public Course createTime(ZonedDateTime createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(ZonedDateTime createTime) {
        this.createTime = createTime;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Course)) {
            return false;
        }
        return id != null && id.equals(((Course) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Course{" +
            "id=" + getId() +
            ", courseClassification='" + getCourseClassification() + "'" +
            ", courseName='" + getCourseName() + "'" +
            ", courseImage='" + getCourseImage() + "'" +
            ", courseDescribe='" + getCourseDescribe() + "'" +
            ", coursePrice=" + getCoursePrice() +
            ", courseChapter='" + getCourseChapter() + "'" +
            ", courseIntroduction='" + getCourseIntroduction() + "'" +
            ", courseVideo='" + getCourseVideo() + "'" +
            ", presenter='" + getPresenter() + "'" +
            ", presenterImage='" + getPresenterImage() + "'" +
            ", presenterIntroduction='" + getPresenterIntroduction() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            "}";
    }
}

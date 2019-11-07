import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ICourse, Course } from 'app/shared/model/course.model';
import { CourseService } from './course.service';

@Component({
  selector: 'jhi-course-update',
  templateUrl: './course-update.component.html'
})
export class CourseUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    courseClassification: [],
    courseName: [],
    courseImage: [],
    courseDescribe: [],
    coursePrice: [],
    courseChapter: [],
    courseIntroduction: [],
    courseVideo: [],
    presenter: [],
    presenterImage: [],
    presenterIntroduction: [],
    createTime: []
  });

  constructor(protected courseService: CourseService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ course }) => {
      this.updateForm(course);
    });
  }

  updateForm(course: ICourse) {
    this.editForm.patchValue({
      id: course.id,
      courseClassification: course.courseClassification,
      courseName: course.courseName,
      courseImage: course.courseImage,
      courseDescribe: course.courseDescribe,
      coursePrice: course.coursePrice,
      courseChapter: course.courseChapter,
      courseIntroduction: course.courseIntroduction,
      courseVideo: course.courseVideo,
      presenter: course.presenter,
      presenterImage: course.presenterImage,
      presenterIntroduction: course.presenterIntroduction,
      createTime: course.createTime != null ? course.createTime.format(DATE_TIME_FORMAT) : null
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const course = this.createFromForm();
    if (course.id !== undefined) {
      this.subscribeToSaveResponse(this.courseService.update(course));
    } else {
      this.subscribeToSaveResponse(this.courseService.create(course));
    }
  }

  private createFromForm(): ICourse {
    return {
      ...new Course(),
      id: this.editForm.get(['id']).value,
      courseClassification: this.editForm.get(['courseClassification']).value,
      courseName: this.editForm.get(['courseName']).value,
      courseImage: this.editForm.get(['courseImage']).value,
      courseDescribe: this.editForm.get(['courseDescribe']).value,
      coursePrice: this.editForm.get(['coursePrice']).value,
      courseChapter: this.editForm.get(['courseChapter']).value,
      courseIntroduction: this.editForm.get(['courseIntroduction']).value,
      courseVideo: this.editForm.get(['courseVideo']).value,
      presenter: this.editForm.get(['presenter']).value,
      presenterImage: this.editForm.get(['presenterImage']).value,
      presenterIntroduction: this.editForm.get(['presenterIntroduction']).value,
      createTime:
        this.editForm.get(['createTime']).value != null ? moment(this.editForm.get(['createTime']).value, DATE_TIME_FORMAT) : undefined
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICourse>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}

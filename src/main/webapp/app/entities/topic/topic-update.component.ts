import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { ITopic, Topic } from 'app/shared/model/topic.model';
import { TopicService } from './topic.service';

@Component({
  selector: 'jhi-topic-update',
  templateUrl: './topic-update.component.html'
})
export class TopicUpdateComponent implements OnInit {
  isSaving: boolean;

  topics: ITopic[];

  editForm = this.fb.group({
    id: [],
    userId: [],
    title: [],
    content: [],
    createTime: [],
    parentId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected topicService: TopicService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ topic }) => {
      this.updateForm(topic);
    });
    this.topicService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ITopic[]>) => mayBeOk.ok),
        map((response: HttpResponse<ITopic[]>) => response.body)
      )
      .subscribe((res: ITopic[]) => (this.topics = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(topic: ITopic) {
    this.editForm.patchValue({
      id: topic.id,
      userId: topic.userId,
      title: topic.title,
      content: topic.content,
      createTime: topic.createTime != null ? topic.createTime.format(DATE_TIME_FORMAT) : null,
      parentId: topic.parentId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const topic = this.createFromForm();
    if (topic.id !== undefined) {
      this.subscribeToSaveResponse(this.topicService.update(topic));
    } else {
      this.subscribeToSaveResponse(this.topicService.create(topic));
    }
  }

  private createFromForm(): ITopic {
    return {
      ...new Topic(),
      id: this.editForm.get(['id']).value,
      userId: this.editForm.get(['userId']).value,
      title: this.editForm.get(['title']).value,
      content: this.editForm.get(['content']).value,
      createTime:
        this.editForm.get(['createTime']).value != null ? moment(this.editForm.get(['createTime']).value, DATE_TIME_FORMAT) : undefined,
      parentId: this.editForm.get(['parentId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITopic>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackTopicById(index: number, item: ITopic) {
    return item.id;
  }
}

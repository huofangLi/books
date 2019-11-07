import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ISpeciclTopic, SpeciclTopic } from 'app/shared/model/specicl-topic.model';
import { SpeciclTopicService } from './specicl-topic.service';

@Component({
  selector: 'jhi-specicl-topic-update',
  templateUrl: './specicl-topic-update.component.html'
})
export class SpeciclTopicUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    title: [],
    content: [],
    image: [],
    createTime: []
  });

  constructor(protected speciclTopicService: SpeciclTopicService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ speciclTopic }) => {
      this.updateForm(speciclTopic);
    });
  }

  updateForm(speciclTopic: ISpeciclTopic) {
    this.editForm.patchValue({
      id: speciclTopic.id,
      title: speciclTopic.title,
      content: speciclTopic.content,
      image: speciclTopic.image,
      createTime: speciclTopic.createTime != null ? speciclTopic.createTime.format(DATE_TIME_FORMAT) : null
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const speciclTopic = this.createFromForm();
    if (speciclTopic.id !== undefined) {
      this.subscribeToSaveResponse(this.speciclTopicService.update(speciclTopic));
    } else {
      this.subscribeToSaveResponse(this.speciclTopicService.create(speciclTopic));
    }
  }

  private createFromForm(): ISpeciclTopic {
    return {
      ...new SpeciclTopic(),
      id: this.editForm.get(['id']).value,
      title: this.editForm.get(['title']).value,
      content: this.editForm.get(['content']).value,
      image: this.editForm.get(['image']).value,
      createTime:
        this.editForm.get(['createTime']).value != null ? moment(this.editForm.get(['createTime']).value, DATE_TIME_FORMAT) : undefined
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISpeciclTopic>>) {
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

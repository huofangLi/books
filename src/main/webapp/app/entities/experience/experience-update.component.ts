import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IExperience, Experience } from 'app/shared/model/experience.model';
import { ExperienceService } from './experience.service';
import { IReading } from 'app/shared/model/reading.model';
import { ReadingService } from 'app/entities/reading';

@Component({
  selector: 'jhi-experience-update',
  templateUrl: './experience-update.component.html'
})
export class ExperienceUpdateComponent implements OnInit {
  isSaving: boolean;

  readings: IReading[];

  editForm = this.fb.group({
    id: [],
    userId: [],
    duration: [],
    content: [],
    readOk: [],
    read: [],
    note: [],
    readingId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected experienceService: ExperienceService,
    protected readingService: ReadingService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ experience }) => {
      this.updateForm(experience);
    });
    this.readingService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IReading[]>) => mayBeOk.ok),
        map((response: HttpResponse<IReading[]>) => response.body)
      )
      .subscribe((res: IReading[]) => (this.readings = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(experience: IExperience) {
    this.editForm.patchValue({
      id: experience.id,
      userId: experience.userId,
      duration: experience.duration,
      content: experience.content,
      readOk: experience.readOk,
      read: experience.read,
      note: experience.note,
      readingId: experience.readingId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const experience = this.createFromForm();
    if (experience.id !== undefined) {
      this.subscribeToSaveResponse(this.experienceService.update(experience));
    } else {
      this.subscribeToSaveResponse(this.experienceService.create(experience));
    }
  }

  private createFromForm(): IExperience {
    return {
      ...new Experience(),
      id: this.editForm.get(['id']).value,
      userId: this.editForm.get(['userId']).value,
      duration: this.editForm.get(['duration']).value,
      content: this.editForm.get(['content']).value,
      readOk: this.editForm.get(['readOk']).value,
      read: this.editForm.get(['read']).value,
      note: this.editForm.get(['note']).value,
      readingId: this.editForm.get(['readingId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IExperience>>) {
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

  trackReadingById(index: number, item: IReading) {
    return item.id;
  }
}

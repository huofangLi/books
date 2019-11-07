import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IBookClassification, BookClassification } from 'app/shared/model/book-classification.model';
import { BookClassificationService } from './book-classification.service';

@Component({
  selector: 'jhi-book-classification-update',
  templateUrl: './book-classification-update.component.html'
})
export class BookClassificationUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    name: []
  });

  constructor(
    protected bookClassificationService: BookClassificationService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ bookClassification }) => {
      this.updateForm(bookClassification);
    });
  }

  updateForm(bookClassification: IBookClassification) {
    this.editForm.patchValue({
      id: bookClassification.id,
      name: bookClassification.name
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const bookClassification = this.createFromForm();
    if (bookClassification.id !== undefined) {
      this.subscribeToSaveResponse(this.bookClassificationService.update(bookClassification));
    } else {
      this.subscribeToSaveResponse(this.bookClassificationService.create(bookClassification));
    }
  }

  private createFromForm(): IBookClassification {
    return {
      ...new BookClassification(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBookClassification>>) {
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

import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IReading, Reading } from 'app/shared/model/reading.model';
import { ReadingService } from './reading.service';
import { IBook } from 'app/shared/model/book.model';
import { BookService } from 'app/entities/book';

@Component({
  selector: 'jhi-reading-update',
  templateUrl: './reading-update.component.html'
})
export class ReadingUpdateComponent implements OnInit {
  isSaving: boolean;

  books: IBook[];

  editForm = this.fb.group({
    id: [],
    page: [],
    createTime: [],
    bookId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected readingService: ReadingService,
    protected bookService: BookService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ reading }) => {
      this.updateForm(reading);
    });
    this.bookService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IBook[]>) => mayBeOk.ok),
        map((response: HttpResponse<IBook[]>) => response.body)
      )
      .subscribe((res: IBook[]) => (this.books = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(reading: IReading) {
    this.editForm.patchValue({
      id: reading.id,
      page: reading.page,
      createTime: reading.createTime != null ? reading.createTime.format(DATE_TIME_FORMAT) : null,
      bookId: reading.bookId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const reading = this.createFromForm();
    if (reading.id !== undefined) {
      this.subscribeToSaveResponse(this.readingService.update(reading));
    } else {
      this.subscribeToSaveResponse(this.readingService.create(reading));
    }
  }

  private createFromForm(): IReading {
    return {
      ...new Reading(),
      id: this.editForm.get(['id']).value,
      page: this.editForm.get(['page']).value,
      createTime:
        this.editForm.get(['createTime']).value != null ? moment(this.editForm.get(['createTime']).value, DATE_TIME_FORMAT) : undefined,
      bookId: this.editForm.get(['bookId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IReading>>) {
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

  trackBookById(index: number, item: IBook) {
    return item.id;
  }
}

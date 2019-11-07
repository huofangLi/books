import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IBookSummary, BookSummary } from 'app/shared/model/book-summary.model';
import { BookSummaryService } from './book-summary.service';
import { IBook } from 'app/shared/model/book.model';
import { BookService } from 'app/entities/book';

@Component({
  selector: 'jhi-book-summary-update',
  templateUrl: './book-summary-update.component.html'
})
export class BookSummaryUpdateComponent implements OnInit {
  isSaving: boolean;

  books: IBook[];

  editForm = this.fb.group({
    id: [],
    comment: [],
    createTime: [],
    bookId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected bookSummaryService: BookSummaryService,
    protected bookService: BookService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ bookSummary }) => {
      this.updateForm(bookSummary);
    });
    this.bookService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IBook[]>) => mayBeOk.ok),
        map((response: HttpResponse<IBook[]>) => response.body)
      )
      .subscribe((res: IBook[]) => (this.books = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(bookSummary: IBookSummary) {
    this.editForm.patchValue({
      id: bookSummary.id,
      comment: bookSummary.comment,
      createTime: bookSummary.createTime != null ? bookSummary.createTime.format(DATE_TIME_FORMAT) : null,
      bookId: bookSummary.bookId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const bookSummary = this.createFromForm();
    if (bookSummary.id !== undefined) {
      this.subscribeToSaveResponse(this.bookSummaryService.update(bookSummary));
    } else {
      this.subscribeToSaveResponse(this.bookSummaryService.create(bookSummary));
    }
  }

  private createFromForm(): IBookSummary {
    return {
      ...new BookSummary(),
      id: this.editForm.get(['id']).value,
      comment: this.editForm.get(['comment']).value,
      createTime:
        this.editForm.get(['createTime']).value != null ? moment(this.editForm.get(['createTime']).value, DATE_TIME_FORMAT) : undefined,
      bookId: this.editForm.get(['bookId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBookSummary>>) {
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

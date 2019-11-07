import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { ISquare, Square } from 'app/shared/model/square.model';
import { SquareService } from './square.service';
import { IBook } from 'app/shared/model/book.model';
import { BookService } from 'app/entities/book';

@Component({
  selector: 'jhi-square-update',
  templateUrl: './square-update.component.html'
})
export class SquareUpdateComponent implements OnInit {
  isSaving: boolean;

  squares: ISquare[];

  books: IBook[];

  editForm = this.fb.group({
    id: [],
    userId: [],
    bookSaying: [],
    comment: [],
    createTime: [],
    parentId: [],
    bookId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected squareService: SquareService,
    protected bookService: BookService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ square }) => {
      this.updateForm(square);
    });
    this.squareService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ISquare[]>) => mayBeOk.ok),
        map((response: HttpResponse<ISquare[]>) => response.body)
      )
      .subscribe((res: ISquare[]) => (this.squares = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.bookService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IBook[]>) => mayBeOk.ok),
        map((response: HttpResponse<IBook[]>) => response.body)
      )
      .subscribe((res: IBook[]) => (this.books = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(square: ISquare) {
    this.editForm.patchValue({
      id: square.id,
      userId: square.userId,
      bookSaying: square.bookSaying,
      comment: square.comment,
      createTime: square.createTime != null ? square.createTime.format(DATE_TIME_FORMAT) : null,
      parentId: square.parentId,
      bookId: square.bookId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const square = this.createFromForm();
    if (square.id !== undefined) {
      this.subscribeToSaveResponse(this.squareService.update(square));
    } else {
      this.subscribeToSaveResponse(this.squareService.create(square));
    }
  }

  private createFromForm(): ISquare {
    return {
      ...new Square(),
      id: this.editForm.get(['id']).value,
      userId: this.editForm.get(['userId']).value,
      bookSaying: this.editForm.get(['bookSaying']).value,
      comment: this.editForm.get(['comment']).value,
      createTime:
        this.editForm.get(['createTime']).value != null ? moment(this.editForm.get(['createTime']).value, DATE_TIME_FORMAT) : undefined,
      parentId: this.editForm.get(['parentId']).value,
      bookId: this.editForm.get(['bookId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISquare>>) {
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

  trackSquareById(index: number, item: ISquare) {
    return item.id;
  }

  trackBookById(index: number, item: IBook) {
    return item.id;
  }
}

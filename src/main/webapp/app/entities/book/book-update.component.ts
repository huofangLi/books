import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IBook, Book } from 'app/shared/model/book.model';
import { BookService } from './book.service';
import { IBookClassification } from 'app/shared/model/book-classification.model';
import { BookClassificationService } from 'app/entities/book-classification';

@Component({
  selector: 'jhi-book-update',
  templateUrl: './book-update.component.html'
})
export class BookUpdateComponent implements OnInit {
  isSaving: boolean;

  bookclassifications: IBookClassification[];

  editForm = this.fb.group({
    id: [],
    userId: [],
    bookName: [],
    bookImage: [],
    bookPages: [],
    isBorrow: [],
    createTime: [],
    bookClassificationId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected bookService: BookService,
    protected bookClassificationService: BookClassificationService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ book }) => {
      this.updateForm(book);
    });
    this.bookClassificationService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IBookClassification[]>) => mayBeOk.ok),
        map((response: HttpResponse<IBookClassification[]>) => response.body)
      )
      .subscribe((res: IBookClassification[]) => (this.bookclassifications = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(book: IBook) {
    this.editForm.patchValue({
      id: book.id,
      userId: book.userId,
      bookName: book.bookName,
      bookImage: book.bookImage,
      bookPages: book.bookPages,
      isBorrow: book.isBorrow,
      createTime: book.createTime != null ? book.createTime.format(DATE_TIME_FORMAT) : null,
      bookClassificationId: book.bookClassificationId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const book = this.createFromForm();
    if (book.id !== undefined) {
      this.subscribeToSaveResponse(this.bookService.update(book));
    } else {
      this.subscribeToSaveResponse(this.bookService.create(book));
    }
  }

  private createFromForm(): IBook {
    return {
      ...new Book(),
      id: this.editForm.get(['id']).value,
      userId: this.editForm.get(['userId']).value,
      bookName: this.editForm.get(['bookName']).value,
      bookImage: this.editForm.get(['bookImage']).value,
      bookPages: this.editForm.get(['bookPages']).value,
      isBorrow: this.editForm.get(['isBorrow']).value,
      createTime:
        this.editForm.get(['createTime']).value != null ? moment(this.editForm.get(['createTime']).value, DATE_TIME_FORMAT) : undefined,
      bookClassificationId: this.editForm.get(['bookClassificationId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBook>>) {
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

  trackBookClassificationById(index: number, item: IBookClassification) {
    return item.id;
  }
}

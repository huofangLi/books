import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IWishList, WishList } from 'app/shared/model/wish-list.model';
import { WishListService } from './wish-list.service';
import { IBook } from 'app/shared/model/book.model';
import { BookService } from 'app/entities/book';

@Component({
  selector: 'jhi-wish-list-update',
  templateUrl: './wish-list-update.component.html'
})
export class WishListUpdateComponent implements OnInit {
  isSaving: boolean;

  books: IBook[];

  editForm = this.fb.group({
    id: [],
    userId: [],
    createTime: [],
    bookId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected wishListService: WishListService,
    protected bookService: BookService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ wishList }) => {
      this.updateForm(wishList);
    });
    this.bookService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IBook[]>) => mayBeOk.ok),
        map((response: HttpResponse<IBook[]>) => response.body)
      )
      .subscribe((res: IBook[]) => (this.books = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(wishList: IWishList) {
    this.editForm.patchValue({
      id: wishList.id,
      userId: wishList.userId,
      createTime: wishList.createTime != null ? wishList.createTime.format(DATE_TIME_FORMAT) : null,
      bookId: wishList.bookId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const wishList = this.createFromForm();
    if (wishList.id !== undefined) {
      this.subscribeToSaveResponse(this.wishListService.update(wishList));
    } else {
      this.subscribeToSaveResponse(this.wishListService.create(wishList));
    }
  }

  private createFromForm(): IWishList {
    return {
      ...new WishList(),
      id: this.editForm.get(['id']).value,
      userId: this.editForm.get(['userId']).value,
      createTime:
        this.editForm.get(['createTime']).value != null ? moment(this.editForm.get(['createTime']).value, DATE_TIME_FORMAT) : undefined,
      bookId: this.editForm.get(['bookId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IWishList>>) {
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

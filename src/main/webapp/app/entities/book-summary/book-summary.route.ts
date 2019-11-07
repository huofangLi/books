import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { BookSummary } from 'app/shared/model/book-summary.model';
import { BookSummaryService } from './book-summary.service';
import { BookSummaryComponent } from './book-summary.component';
import { BookSummaryDetailComponent } from './book-summary-detail.component';
import { BookSummaryUpdateComponent } from './book-summary-update.component';
import { BookSummaryDeletePopupComponent } from './book-summary-delete-dialog.component';
import { IBookSummary } from 'app/shared/model/book-summary.model';

@Injectable({ providedIn: 'root' })
export class BookSummaryResolve implements Resolve<IBookSummary> {
  constructor(private service: BookSummaryService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IBookSummary> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<BookSummary>) => response.ok),
        map((bookSummary: HttpResponse<BookSummary>) => bookSummary.body)
      );
    }
    return of(new BookSummary());
  }
}

export const bookSummaryRoute: Routes = [
  {
    path: '',
    component: BookSummaryComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'booksApp.bookSummary.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: BookSummaryDetailComponent,
    resolve: {
      bookSummary: BookSummaryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'booksApp.bookSummary.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: BookSummaryUpdateComponent,
    resolve: {
      bookSummary: BookSummaryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'booksApp.bookSummary.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: BookSummaryUpdateComponent,
    resolve: {
      bookSummary: BookSummaryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'booksApp.bookSummary.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const bookSummaryPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: BookSummaryDeletePopupComponent,
    resolve: {
      bookSummary: BookSummaryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'booksApp.bookSummary.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];

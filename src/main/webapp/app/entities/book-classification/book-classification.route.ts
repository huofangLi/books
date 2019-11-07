import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { BookClassification } from 'app/shared/model/book-classification.model';
import { BookClassificationService } from './book-classification.service';
import { BookClassificationComponent } from './book-classification.component';
import { BookClassificationDetailComponent } from './book-classification-detail.component';
import { BookClassificationUpdateComponent } from './book-classification-update.component';
import { BookClassificationDeletePopupComponent } from './book-classification-delete-dialog.component';
import { IBookClassification } from 'app/shared/model/book-classification.model';

@Injectable({ providedIn: 'root' })
export class BookClassificationResolve implements Resolve<IBookClassification> {
  constructor(private service: BookClassificationService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IBookClassification> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<BookClassification>) => response.ok),
        map((bookClassification: HttpResponse<BookClassification>) => bookClassification.body)
      );
    }
    return of(new BookClassification());
  }
}

export const bookClassificationRoute: Routes = [
  {
    path: '',
    component: BookClassificationComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'booksApp.bookClassification.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: BookClassificationDetailComponent,
    resolve: {
      bookClassification: BookClassificationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'booksApp.bookClassification.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: BookClassificationUpdateComponent,
    resolve: {
      bookClassification: BookClassificationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'booksApp.bookClassification.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: BookClassificationUpdateComponent,
    resolve: {
      bookClassification: BookClassificationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'booksApp.bookClassification.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const bookClassificationPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: BookClassificationDeletePopupComponent,
    resolve: {
      bookClassification: BookClassificationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'booksApp.bookClassification.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];

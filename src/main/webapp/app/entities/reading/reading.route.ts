import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Reading } from 'app/shared/model/reading.model';
import { ReadingService } from './reading.service';
import { ReadingComponent } from './reading.component';
import { ReadingDetailComponent } from './reading-detail.component';
import { ReadingUpdateComponent } from './reading-update.component';
import { ReadingDeletePopupComponent } from './reading-delete-dialog.component';
import { IReading } from 'app/shared/model/reading.model';

@Injectable({ providedIn: 'root' })
export class ReadingResolve implements Resolve<IReading> {
  constructor(private service: ReadingService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IReading> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Reading>) => response.ok),
        map((reading: HttpResponse<Reading>) => reading.body)
      );
    }
    return of(new Reading());
  }
}

export const readingRoute: Routes = [
  {
    path: '',
    component: ReadingComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'booksApp.reading.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ReadingDetailComponent,
    resolve: {
      reading: ReadingResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'booksApp.reading.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ReadingUpdateComponent,
    resolve: {
      reading: ReadingResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'booksApp.reading.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ReadingUpdateComponent,
    resolve: {
      reading: ReadingResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'booksApp.reading.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const readingPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ReadingDeletePopupComponent,
    resolve: {
      reading: ReadingResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'booksApp.reading.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];

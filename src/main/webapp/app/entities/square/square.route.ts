import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Square } from 'app/shared/model/square.model';
import { SquareService } from './square.service';
import { SquareComponent } from './square.component';
import { SquareDetailComponent } from './square-detail.component';
import { SquareUpdateComponent } from './square-update.component';
import { SquareDeletePopupComponent } from './square-delete-dialog.component';
import { ISquare } from 'app/shared/model/square.model';

@Injectable({ providedIn: 'root' })
export class SquareResolve implements Resolve<ISquare> {
  constructor(private service: SquareService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISquare> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Square>) => response.ok),
        map((square: HttpResponse<Square>) => square.body)
      );
    }
    return of(new Square());
  }
}

export const squareRoute: Routes = [
  {
    path: '',
    component: SquareComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'booksApp.square.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SquareDetailComponent,
    resolve: {
      square: SquareResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'booksApp.square.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SquareUpdateComponent,
    resolve: {
      square: SquareResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'booksApp.square.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SquareUpdateComponent,
    resolve: {
      square: SquareResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'booksApp.square.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const squarePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: SquareDeletePopupComponent,
    resolve: {
      square: SquareResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'booksApp.square.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];

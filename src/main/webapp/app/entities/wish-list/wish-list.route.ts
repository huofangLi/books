import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { WishList } from 'app/shared/model/wish-list.model';
import { WishListService } from './wish-list.service';
import { WishListComponent } from './wish-list.component';
import { WishListDetailComponent } from './wish-list-detail.component';
import { WishListUpdateComponent } from './wish-list-update.component';
import { WishListDeletePopupComponent } from './wish-list-delete-dialog.component';
import { IWishList } from 'app/shared/model/wish-list.model';

@Injectable({ providedIn: 'root' })
export class WishListResolve implements Resolve<IWishList> {
  constructor(private service: WishListService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IWishList> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<WishList>) => response.ok),
        map((wishList: HttpResponse<WishList>) => wishList.body)
      );
    }
    return of(new WishList());
  }
}

export const wishListRoute: Routes = [
  {
    path: '',
    component: WishListComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'booksApp.wishList.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: WishListDetailComponent,
    resolve: {
      wishList: WishListResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'booksApp.wishList.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: WishListUpdateComponent,
    resolve: {
      wishList: WishListResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'booksApp.wishList.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: WishListUpdateComponent,
    resolve: {
      wishList: WishListResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'booksApp.wishList.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const wishListPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: WishListDeletePopupComponent,
    resolve: {
      wishList: WishListResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'booksApp.wishList.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];

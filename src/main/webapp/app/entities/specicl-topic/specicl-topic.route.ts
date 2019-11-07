import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { SpeciclTopic } from 'app/shared/model/specicl-topic.model';
import { SpeciclTopicService } from './specicl-topic.service';
import { SpeciclTopicComponent } from './specicl-topic.component';
import { SpeciclTopicDetailComponent } from './specicl-topic-detail.component';
import { SpeciclTopicUpdateComponent } from './specicl-topic-update.component';
import { SpeciclTopicDeletePopupComponent } from './specicl-topic-delete-dialog.component';
import { ISpeciclTopic } from 'app/shared/model/specicl-topic.model';

@Injectable({ providedIn: 'root' })
export class SpeciclTopicResolve implements Resolve<ISpeciclTopic> {
  constructor(private service: SpeciclTopicService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISpeciclTopic> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<SpeciclTopic>) => response.ok),
        map((speciclTopic: HttpResponse<SpeciclTopic>) => speciclTopic.body)
      );
    }
    return of(new SpeciclTopic());
  }
}

export const speciclTopicRoute: Routes = [
  {
    path: '',
    component: SpeciclTopicComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'booksApp.speciclTopic.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SpeciclTopicDetailComponent,
    resolve: {
      speciclTopic: SpeciclTopicResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'booksApp.speciclTopic.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SpeciclTopicUpdateComponent,
    resolve: {
      speciclTopic: SpeciclTopicResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'booksApp.speciclTopic.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SpeciclTopicUpdateComponent,
    resolve: {
      speciclTopic: SpeciclTopicResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'booksApp.speciclTopic.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const speciclTopicPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: SpeciclTopicDeletePopupComponent,
    resolve: {
      speciclTopic: SpeciclTopicResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'booksApp.speciclTopic.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];

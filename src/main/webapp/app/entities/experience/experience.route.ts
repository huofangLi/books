import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Experience } from 'app/shared/model/experience.model';
import { ExperienceService } from './experience.service';
import { ExperienceComponent } from './experience.component';
import { ExperienceDetailComponent } from './experience-detail.component';
import { ExperienceUpdateComponent } from './experience-update.component';
import { ExperienceDeletePopupComponent } from './experience-delete-dialog.component';
import { IExperience } from 'app/shared/model/experience.model';

@Injectable({ providedIn: 'root' })
export class ExperienceResolve implements Resolve<IExperience> {
  constructor(private service: ExperienceService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IExperience> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Experience>) => response.ok),
        map((experience: HttpResponse<Experience>) => experience.body)
      );
    }
    return of(new Experience());
  }
}

export const experienceRoute: Routes = [
  {
    path: '',
    component: ExperienceComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'booksApp.experience.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ExperienceDetailComponent,
    resolve: {
      experience: ExperienceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'booksApp.experience.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ExperienceUpdateComponent,
    resolve: {
      experience: ExperienceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'booksApp.experience.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ExperienceUpdateComponent,
    resolve: {
      experience: ExperienceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'booksApp.experience.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const experiencePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ExperienceDeletePopupComponent,
    resolve: {
      experience: ExperienceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'booksApp.experience.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];

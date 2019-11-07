import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { BooksSharedModule } from 'app/shared';
import {
  SpeciclTopicComponent,
  SpeciclTopicDetailComponent,
  SpeciclTopicUpdateComponent,
  SpeciclTopicDeletePopupComponent,
  SpeciclTopicDeleteDialogComponent,
  speciclTopicRoute,
  speciclTopicPopupRoute
} from './';

const ENTITY_STATES = [...speciclTopicRoute, ...speciclTopicPopupRoute];

@NgModule({
  imports: [BooksSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    SpeciclTopicComponent,
    SpeciclTopicDetailComponent,
    SpeciclTopicUpdateComponent,
    SpeciclTopicDeleteDialogComponent,
    SpeciclTopicDeletePopupComponent
  ],
  entryComponents: [
    SpeciclTopicComponent,
    SpeciclTopicUpdateComponent,
    SpeciclTopicDeleteDialogComponent,
    SpeciclTopicDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BooksSpeciclTopicModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}

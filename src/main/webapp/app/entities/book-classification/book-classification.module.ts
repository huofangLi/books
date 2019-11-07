import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { BooksSharedModule } from 'app/shared';
import {
  BookClassificationComponent,
  BookClassificationDetailComponent,
  BookClassificationUpdateComponent,
  BookClassificationDeletePopupComponent,
  BookClassificationDeleteDialogComponent,
  bookClassificationRoute,
  bookClassificationPopupRoute
} from './';

const ENTITY_STATES = [...bookClassificationRoute, ...bookClassificationPopupRoute];

@NgModule({
  imports: [BooksSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    BookClassificationComponent,
    BookClassificationDetailComponent,
    BookClassificationUpdateComponent,
    BookClassificationDeleteDialogComponent,
    BookClassificationDeletePopupComponent
  ],
  entryComponents: [
    BookClassificationComponent,
    BookClassificationUpdateComponent,
    BookClassificationDeleteDialogComponent,
    BookClassificationDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BooksBookClassificationModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}

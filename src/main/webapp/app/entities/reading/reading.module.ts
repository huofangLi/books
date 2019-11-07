import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { BooksSharedModule } from 'app/shared';
import {
  ReadingComponent,
  ReadingDetailComponent,
  ReadingUpdateComponent,
  ReadingDeletePopupComponent,
  ReadingDeleteDialogComponent,
  readingRoute,
  readingPopupRoute
} from './';

const ENTITY_STATES = [...readingRoute, ...readingPopupRoute];

@NgModule({
  imports: [BooksSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ReadingComponent,
    ReadingDetailComponent,
    ReadingUpdateComponent,
    ReadingDeleteDialogComponent,
    ReadingDeletePopupComponent
  ],
  entryComponents: [ReadingComponent, ReadingUpdateComponent, ReadingDeleteDialogComponent, ReadingDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BooksReadingModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}

import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { BooksSharedModule } from 'app/shared';
import {
  BookSummaryComponent,
  BookSummaryDetailComponent,
  BookSummaryUpdateComponent,
  BookSummaryDeletePopupComponent,
  BookSummaryDeleteDialogComponent,
  bookSummaryRoute,
  bookSummaryPopupRoute
} from './';

const ENTITY_STATES = [...bookSummaryRoute, ...bookSummaryPopupRoute];

@NgModule({
  imports: [BooksSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    BookSummaryComponent,
    BookSummaryDetailComponent,
    BookSummaryUpdateComponent,
    BookSummaryDeleteDialogComponent,
    BookSummaryDeletePopupComponent
  ],
  entryComponents: [BookSummaryComponent, BookSummaryUpdateComponent, BookSummaryDeleteDialogComponent, BookSummaryDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BooksBookSummaryModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}

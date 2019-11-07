import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { BooksSharedModule } from 'app/shared';
import {
  SquareComponent,
  SquareDetailComponent,
  SquareUpdateComponent,
  SquareDeletePopupComponent,
  SquareDeleteDialogComponent,
  squareRoute,
  squarePopupRoute
} from './';

const ENTITY_STATES = [...squareRoute, ...squarePopupRoute];

@NgModule({
  imports: [BooksSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [SquareComponent, SquareDetailComponent, SquareUpdateComponent, SquareDeleteDialogComponent, SquareDeletePopupComponent],
  entryComponents: [SquareComponent, SquareUpdateComponent, SquareDeleteDialogComponent, SquareDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BooksSquareModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}

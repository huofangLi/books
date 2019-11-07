import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { BooksSharedModule } from 'app/shared';
import {
  FileComponent,
  FileDetailComponent,
  FileUpdateComponent,
  FileDeletePopupComponent,
  FileDeleteDialogComponent,
  fileRoute,
  filePopupRoute
} from './';

const ENTITY_STATES = [...fileRoute, ...filePopupRoute];

@NgModule({
  imports: [BooksSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [FileComponent, FileDetailComponent, FileUpdateComponent, FileDeleteDialogComponent, FileDeletePopupComponent],
  entryComponents: [FileComponent, FileUpdateComponent, FileDeleteDialogComponent, FileDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BooksFileModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}

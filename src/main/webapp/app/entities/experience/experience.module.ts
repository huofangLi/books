import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { BooksSharedModule } from 'app/shared';
import {
  ExperienceComponent,
  ExperienceDetailComponent,
  ExperienceUpdateComponent,
  ExperienceDeletePopupComponent,
  ExperienceDeleteDialogComponent,
  experienceRoute,
  experiencePopupRoute
} from './';

const ENTITY_STATES = [...experienceRoute, ...experiencePopupRoute];

@NgModule({
  imports: [BooksSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ExperienceComponent,
    ExperienceDetailComponent,
    ExperienceUpdateComponent,
    ExperienceDeleteDialogComponent,
    ExperienceDeletePopupComponent
  ],
  entryComponents: [ExperienceComponent, ExperienceUpdateComponent, ExperienceDeleteDialogComponent, ExperienceDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BooksExperienceModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}

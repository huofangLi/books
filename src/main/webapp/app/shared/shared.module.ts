import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { BooksSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [BooksSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [BooksSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BooksSharedModule {
  static forRoot() {
    return {
      ngModule: BooksSharedModule
    };
  }
}

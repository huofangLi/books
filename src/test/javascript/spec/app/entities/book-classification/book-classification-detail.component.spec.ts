/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BooksTestModule } from '../../../test.module';
import { BookClassificationDetailComponent } from 'app/entities/book-classification/book-classification-detail.component';
import { BookClassification } from 'app/shared/model/book-classification.model';

describe('Component Tests', () => {
  describe('BookClassification Management Detail Component', () => {
    let comp: BookClassificationDetailComponent;
    let fixture: ComponentFixture<BookClassificationDetailComponent>;
    const route = ({ data: of({ bookClassification: new BookClassification(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BooksTestModule],
        declarations: [BookClassificationDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(BookClassificationDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BookClassificationDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.bookClassification).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

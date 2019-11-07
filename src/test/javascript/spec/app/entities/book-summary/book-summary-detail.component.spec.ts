/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BooksTestModule } from '../../../test.module';
import { BookSummaryDetailComponent } from 'app/entities/book-summary/book-summary-detail.component';
import { BookSummary } from 'app/shared/model/book-summary.model';

describe('Component Tests', () => {
  describe('BookSummary Management Detail Component', () => {
    let comp: BookSummaryDetailComponent;
    let fixture: ComponentFixture<BookSummaryDetailComponent>;
    const route = ({ data: of({ bookSummary: new BookSummary(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BooksTestModule],
        declarations: [BookSummaryDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(BookSummaryDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BookSummaryDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.bookSummary).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

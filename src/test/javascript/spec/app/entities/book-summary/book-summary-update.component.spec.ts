/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { BooksTestModule } from '../../../test.module';
import { BookSummaryUpdateComponent } from 'app/entities/book-summary/book-summary-update.component';
import { BookSummaryService } from 'app/entities/book-summary/book-summary.service';
import { BookSummary } from 'app/shared/model/book-summary.model';

describe('Component Tests', () => {
  describe('BookSummary Management Update Component', () => {
    let comp: BookSummaryUpdateComponent;
    let fixture: ComponentFixture<BookSummaryUpdateComponent>;
    let service: BookSummaryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BooksTestModule],
        declarations: [BookSummaryUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(BookSummaryUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BookSummaryUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BookSummaryService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new BookSummary(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new BookSummary();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});

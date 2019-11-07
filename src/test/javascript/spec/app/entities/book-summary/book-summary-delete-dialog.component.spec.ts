/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { BooksTestModule } from '../../../test.module';
import { BookSummaryDeleteDialogComponent } from 'app/entities/book-summary/book-summary-delete-dialog.component';
import { BookSummaryService } from 'app/entities/book-summary/book-summary.service';

describe('Component Tests', () => {
  describe('BookSummary Management Delete Component', () => {
    let comp: BookSummaryDeleteDialogComponent;
    let fixture: ComponentFixture<BookSummaryDeleteDialogComponent>;
    let service: BookSummaryService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BooksTestModule],
        declarations: [BookSummaryDeleteDialogComponent]
      })
        .overrideTemplate(BookSummaryDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BookSummaryDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BookSummaryService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});

/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { BooksTestModule } from '../../../test.module';
import { BookClassificationDeleteDialogComponent } from 'app/entities/book-classification/book-classification-delete-dialog.component';
import { BookClassificationService } from 'app/entities/book-classification/book-classification.service';

describe('Component Tests', () => {
  describe('BookClassification Management Delete Component', () => {
    let comp: BookClassificationDeleteDialogComponent;
    let fixture: ComponentFixture<BookClassificationDeleteDialogComponent>;
    let service: BookClassificationService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BooksTestModule],
        declarations: [BookClassificationDeleteDialogComponent]
      })
        .overrideTemplate(BookClassificationDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BookClassificationDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BookClassificationService);
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

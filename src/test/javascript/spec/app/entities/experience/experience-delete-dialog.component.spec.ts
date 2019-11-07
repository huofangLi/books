/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { BooksTestModule } from '../../../test.module';
import { ExperienceDeleteDialogComponent } from 'app/entities/experience/experience-delete-dialog.component';
import { ExperienceService } from 'app/entities/experience/experience.service';

describe('Component Tests', () => {
  describe('Experience Management Delete Component', () => {
    let comp: ExperienceDeleteDialogComponent;
    let fixture: ComponentFixture<ExperienceDeleteDialogComponent>;
    let service: ExperienceService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BooksTestModule],
        declarations: [ExperienceDeleteDialogComponent]
      })
        .overrideTemplate(ExperienceDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ExperienceDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ExperienceService);
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

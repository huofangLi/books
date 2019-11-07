/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { BooksTestModule } from '../../../test.module';
import { ReadingUpdateComponent } from 'app/entities/reading/reading-update.component';
import { ReadingService } from 'app/entities/reading/reading.service';
import { Reading } from 'app/shared/model/reading.model';

describe('Component Tests', () => {
  describe('Reading Management Update Component', () => {
    let comp: ReadingUpdateComponent;
    let fixture: ComponentFixture<ReadingUpdateComponent>;
    let service: ReadingService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BooksTestModule],
        declarations: [ReadingUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ReadingUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ReadingUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ReadingService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Reading(123);
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
        const entity = new Reading();
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

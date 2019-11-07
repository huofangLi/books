/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { BooksTestModule } from '../../../test.module';
import { SquareUpdateComponent } from 'app/entities/square/square-update.component';
import { SquareService } from 'app/entities/square/square.service';
import { Square } from 'app/shared/model/square.model';

describe('Component Tests', () => {
  describe('Square Management Update Component', () => {
    let comp: SquareUpdateComponent;
    let fixture: ComponentFixture<SquareUpdateComponent>;
    let service: SquareService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BooksTestModule],
        declarations: [SquareUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SquareUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SquareUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SquareService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Square(123);
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
        const entity = new Square();
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

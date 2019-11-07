/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { BooksTestModule } from '../../../test.module';
import { SpeciclTopicUpdateComponent } from 'app/entities/specicl-topic/specicl-topic-update.component';
import { SpeciclTopicService } from 'app/entities/specicl-topic/specicl-topic.service';
import { SpeciclTopic } from 'app/shared/model/specicl-topic.model';

describe('Component Tests', () => {
  describe('SpeciclTopic Management Update Component', () => {
    let comp: SpeciclTopicUpdateComponent;
    let fixture: ComponentFixture<SpeciclTopicUpdateComponent>;
    let service: SpeciclTopicService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BooksTestModule],
        declarations: [SpeciclTopicUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SpeciclTopicUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SpeciclTopicUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SpeciclTopicService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SpeciclTopic(123);
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
        const entity = new SpeciclTopic();
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

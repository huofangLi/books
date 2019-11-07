/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BooksTestModule } from '../../../test.module';
import { SpeciclTopicDetailComponent } from 'app/entities/specicl-topic/specicl-topic-detail.component';
import { SpeciclTopic } from 'app/shared/model/specicl-topic.model';

describe('Component Tests', () => {
  describe('SpeciclTopic Management Detail Component', () => {
    let comp: SpeciclTopicDetailComponent;
    let fixture: ComponentFixture<SpeciclTopicDetailComponent>;
    const route = ({ data: of({ speciclTopic: new SpeciclTopic(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BooksTestModule],
        declarations: [SpeciclTopicDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SpeciclTopicDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SpeciclTopicDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.speciclTopic).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

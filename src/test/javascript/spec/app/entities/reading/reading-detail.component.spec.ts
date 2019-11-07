/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BooksTestModule } from '../../../test.module';
import { ReadingDetailComponent } from 'app/entities/reading/reading-detail.component';
import { Reading } from 'app/shared/model/reading.model';

describe('Component Tests', () => {
  describe('Reading Management Detail Component', () => {
    let comp: ReadingDetailComponent;
    let fixture: ComponentFixture<ReadingDetailComponent>;
    const route = ({ data: of({ reading: new Reading(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BooksTestModule],
        declarations: [ReadingDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ReadingDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ReadingDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.reading).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

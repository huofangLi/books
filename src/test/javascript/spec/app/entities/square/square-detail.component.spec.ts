/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BooksTestModule } from '../../../test.module';
import { SquareDetailComponent } from 'app/entities/square/square-detail.component';
import { Square } from 'app/shared/model/square.model';

describe('Component Tests', () => {
  describe('Square Management Detail Component', () => {
    let comp: SquareDetailComponent;
    let fixture: ComponentFixture<SquareDetailComponent>;
    const route = ({ data: of({ square: new Square(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BooksTestModule],
        declarations: [SquareDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SquareDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SquareDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.square).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

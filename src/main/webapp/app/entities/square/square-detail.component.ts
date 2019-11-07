import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISquare } from 'app/shared/model/square.model';

@Component({
  selector: 'jhi-square-detail',
  templateUrl: './square-detail.component.html'
})
export class SquareDetailComponent implements OnInit {
  square: ISquare;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ square }) => {
      this.square = square;
    });
  }

  previousState() {
    window.history.back();
  }
}

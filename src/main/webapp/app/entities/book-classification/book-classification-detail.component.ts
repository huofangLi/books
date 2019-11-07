import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBookClassification } from 'app/shared/model/book-classification.model';

@Component({
  selector: 'jhi-book-classification-detail',
  templateUrl: './book-classification-detail.component.html'
})
export class BookClassificationDetailComponent implements OnInit {
  bookClassification: IBookClassification;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ bookClassification }) => {
      this.bookClassification = bookClassification;
    });
  }

  previousState() {
    window.history.back();
  }
}

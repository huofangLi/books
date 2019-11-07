import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBookSummary } from 'app/shared/model/book-summary.model';

@Component({
  selector: 'jhi-book-summary-detail',
  templateUrl: './book-summary-detail.component.html'
})
export class BookSummaryDetailComponent implements OnInit {
  bookSummary: IBookSummary;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ bookSummary }) => {
      this.bookSummary = bookSummary;
    });
  }

  previousState() {
    window.history.back();
  }
}

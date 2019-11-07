import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IReading } from 'app/shared/model/reading.model';

@Component({
  selector: 'jhi-reading-detail',
  templateUrl: './reading-detail.component.html'
})
export class ReadingDetailComponent implements OnInit {
  reading: IReading;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ reading }) => {
      this.reading = reading;
    });
  }

  previousState() {
    window.history.back();
  }
}

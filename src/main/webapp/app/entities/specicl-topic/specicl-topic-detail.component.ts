import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISpeciclTopic } from 'app/shared/model/specicl-topic.model';

@Component({
  selector: 'jhi-specicl-topic-detail',
  templateUrl: './specicl-topic-detail.component.html'
})
export class SpeciclTopicDetailComponent implements OnInit {
  speciclTopic: ISpeciclTopic;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ speciclTopic }) => {
      this.speciclTopic = speciclTopic;
    });
  }

  previousState() {
    window.history.back();
  }
}

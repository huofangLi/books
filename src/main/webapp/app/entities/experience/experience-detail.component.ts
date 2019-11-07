import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IExperience } from 'app/shared/model/experience.model';

@Component({
  selector: 'jhi-experience-detail',
  templateUrl: './experience-detail.component.html'
})
export class ExperienceDetailComponent implements OnInit {
  experience: IExperience;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ experience }) => {
      this.experience = experience;
    });
  }

  previousState() {
    window.history.back();
  }
}

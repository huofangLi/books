import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IExperience } from 'app/shared/model/experience.model';
import { ExperienceService } from './experience.service';

@Component({
  selector: 'jhi-experience-delete-dialog',
  templateUrl: './experience-delete-dialog.component.html'
})
export class ExperienceDeleteDialogComponent {
  experience: IExperience;

  constructor(
    protected experienceService: ExperienceService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.experienceService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'experienceListModification',
        content: 'Deleted an experience'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-experience-delete-popup',
  template: ''
})
export class ExperienceDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ experience }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ExperienceDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.experience = experience;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/experience', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/experience', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}

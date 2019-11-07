import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISpeciclTopic } from 'app/shared/model/specicl-topic.model';
import { SpeciclTopicService } from './specicl-topic.service';

@Component({
  selector: 'jhi-specicl-topic-delete-dialog',
  templateUrl: './specicl-topic-delete-dialog.component.html'
})
export class SpeciclTopicDeleteDialogComponent {
  speciclTopic: ISpeciclTopic;

  constructor(
    protected speciclTopicService: SpeciclTopicService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.speciclTopicService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'speciclTopicListModification',
        content: 'Deleted an speciclTopic'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-specicl-topic-delete-popup',
  template: ''
})
export class SpeciclTopicDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ speciclTopic }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(SpeciclTopicDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.speciclTopic = speciclTopic;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/specicl-topic', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/specicl-topic', { outlets: { popup: null } }]);
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

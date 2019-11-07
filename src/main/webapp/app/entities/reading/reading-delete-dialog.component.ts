import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IReading } from 'app/shared/model/reading.model';
import { ReadingService } from './reading.service';

@Component({
  selector: 'jhi-reading-delete-dialog',
  templateUrl: './reading-delete-dialog.component.html'
})
export class ReadingDeleteDialogComponent {
  reading: IReading;

  constructor(protected readingService: ReadingService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.readingService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'readingListModification',
        content: 'Deleted an reading'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-reading-delete-popup',
  template: ''
})
export class ReadingDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ reading }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ReadingDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.reading = reading;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/reading', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/reading', { outlets: { popup: null } }]);
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

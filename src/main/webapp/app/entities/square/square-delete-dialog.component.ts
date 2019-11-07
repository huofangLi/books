import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISquare } from 'app/shared/model/square.model';
import { SquareService } from './square.service';

@Component({
  selector: 'jhi-square-delete-dialog',
  templateUrl: './square-delete-dialog.component.html'
})
export class SquareDeleteDialogComponent {
  square: ISquare;

  constructor(protected squareService: SquareService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.squareService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'squareListModification',
        content: 'Deleted an square'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-square-delete-popup',
  template: ''
})
export class SquareDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ square }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(SquareDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.square = square;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/square', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/square', { outlets: { popup: null } }]);
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

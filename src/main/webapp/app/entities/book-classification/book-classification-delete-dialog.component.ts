import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBookClassification } from 'app/shared/model/book-classification.model';
import { BookClassificationService } from './book-classification.service';

@Component({
  selector: 'jhi-book-classification-delete-dialog',
  templateUrl: './book-classification-delete-dialog.component.html'
})
export class BookClassificationDeleteDialogComponent {
  bookClassification: IBookClassification;

  constructor(
    protected bookClassificationService: BookClassificationService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.bookClassificationService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'bookClassificationListModification',
        content: 'Deleted an bookClassification'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-book-classification-delete-popup',
  template: ''
})
export class BookClassificationDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ bookClassification }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(BookClassificationDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.bookClassification = bookClassification;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/book-classification', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/book-classification', { outlets: { popup: null } }]);
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

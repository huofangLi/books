import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBookSummary } from 'app/shared/model/book-summary.model';
import { BookSummaryService } from './book-summary.service';

@Component({
  selector: 'jhi-book-summary-delete-dialog',
  templateUrl: './book-summary-delete-dialog.component.html'
})
export class BookSummaryDeleteDialogComponent {
  bookSummary: IBookSummary;

  constructor(
    protected bookSummaryService: BookSummaryService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.bookSummaryService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'bookSummaryListModification',
        content: 'Deleted an bookSummary'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-book-summary-delete-popup',
  template: ''
})
export class BookSummaryDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ bookSummary }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(BookSummaryDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.bookSummary = bookSummary;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/book-summary', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/book-summary', { outlets: { popup: null } }]);
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

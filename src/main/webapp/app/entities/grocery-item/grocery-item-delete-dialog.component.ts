import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IGroceryItem } from 'app/shared/model/grocery-item.model';
import { GroceryItemService } from './grocery-item.service';

@Component({
    selector: 'jhi-grocery-item-delete-dialog',
    templateUrl: './grocery-item-delete-dialog.component.html'
})
export class GroceryItemDeleteDialogComponent {
    groceryItem: IGroceryItem;

    constructor(
        private groceryItemService: GroceryItemService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.groceryItemService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'groceryItemListModification',
                content: 'Deleted an groceryItem'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-grocery-item-delete-popup',
    template: ''
})
export class GroceryItemDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ groceryItem }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(GroceryItemDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.groceryItem = groceryItem;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
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

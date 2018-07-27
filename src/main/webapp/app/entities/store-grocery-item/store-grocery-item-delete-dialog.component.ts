import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IStoreGroceryItem } from 'app/shared/model/store-grocery-item.model';
import { StoreGroceryItemService } from './store-grocery-item.service';

@Component({
    selector: 'jhi-store-grocery-item-delete-dialog',
    templateUrl: './store-grocery-item-delete-dialog.component.html'
})
export class StoreGroceryItemDeleteDialogComponent {
    storeGroceryItem: IStoreGroceryItem;

    constructor(
        private storeGroceryItemService: StoreGroceryItemService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.storeGroceryItemService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'storeGroceryItemListModification',
                content: 'Deleted an storeGroceryItem'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-store-grocery-item-delete-popup',
    template: ''
})
export class StoreGroceryItemDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ storeGroceryItem }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(StoreGroceryItemDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.storeGroceryItem = storeGroceryItem;
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

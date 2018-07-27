import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IStoreGroceryItem } from 'app/shared/model/store-grocery-item.model';
import { Principal } from 'app/core';
import { StoreGroceryItemService } from './store-grocery-item.service';

@Component({
    selector: 'jhi-store-grocery-item',
    templateUrl: './store-grocery-item.component.html'
})
export class StoreGroceryItemComponent implements OnInit, OnDestroy {
    storeGroceryItems: IStoreGroceryItem[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private storeGroceryItemService: StoreGroceryItemService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.storeGroceryItemService.query().subscribe(
            (res: HttpResponse<IStoreGroceryItem[]>) => {
                this.storeGroceryItems = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInStoreGroceryItems();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IStoreGroceryItem) {
        return item.id;
    }

    registerChangeInStoreGroceryItems() {
        this.eventSubscriber = this.eventManager.subscribe('storeGroceryItemListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}

import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IGroceryItem } from 'app/shared/model/grocery-item.model';
import { Principal } from 'app/core';
import { GroceryItemService } from './grocery-item.service';

@Component({
    selector: 'jhi-grocery-item',
    templateUrl: './grocery-item.component.html'
})
export class GroceryItemComponent implements OnInit, OnDestroy {
    groceryItems: IGroceryItem[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private groceryItemService: GroceryItemService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.groceryItemService.query().subscribe(
            (res: HttpResponse<IGroceryItem[]>) => {
                this.groceryItems = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInGroceryItems();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IGroceryItem) {
        return item.id;
    }

    registerChangeInGroceryItems() {
        this.eventSubscriber = this.eventManager.subscribe('groceryItemListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}

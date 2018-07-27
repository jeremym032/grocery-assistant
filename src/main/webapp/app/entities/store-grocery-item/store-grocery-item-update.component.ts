import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IStoreGroceryItem } from 'app/shared/model/store-grocery-item.model';
import { StoreGroceryItemService } from './store-grocery-item.service';
import { IStore } from 'app/shared/model/store.model';
import { StoreService } from 'app/entities/store';
import { IItem } from 'app/shared/model/item.model';
import { ItemService } from 'app/entities/item';

@Component({
    selector: 'jhi-store-grocery-item-update',
    templateUrl: './store-grocery-item-update.component.html'
})
export class StoreGroceryItemUpdateComponent implements OnInit {
    private _storeGroceryItem: IStoreGroceryItem;
    isSaving: boolean;

    stores: IStore[];

    items: IItem[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private storeGroceryItemService: StoreGroceryItemService,
        private storeService: StoreService,
        private itemService: ItemService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ storeGroceryItem }) => {
            this.storeGroceryItem = storeGroceryItem;
        });
        this.storeService.query().subscribe(
            (res: HttpResponse<IStore[]>) => {
                this.stores = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.itemService.query().subscribe(
            (res: HttpResponse<IItem[]>) => {
                this.items = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.storeGroceryItem.id !== undefined) {
            this.subscribeToSaveResponse(this.storeGroceryItemService.update(this.storeGroceryItem));
        } else {
            this.subscribeToSaveResponse(this.storeGroceryItemService.create(this.storeGroceryItem));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IStoreGroceryItem>>) {
        result.subscribe((res: HttpResponse<IStoreGroceryItem>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackStoreById(index: number, item: IStore) {
        return item.id;
    }

    trackItemById(index: number, item: IItem) {
        return item.id;
    }
    get storeGroceryItem() {
        return this._storeGroceryItem;
    }

    set storeGroceryItem(storeGroceryItem: IStoreGroceryItem) {
        this._storeGroceryItem = storeGroceryItem;
    }
}

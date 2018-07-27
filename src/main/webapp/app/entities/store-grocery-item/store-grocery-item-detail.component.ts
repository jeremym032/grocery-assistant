import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IStoreGroceryItem } from 'app/shared/model/store-grocery-item.model';

@Component({
    selector: 'jhi-store-grocery-item-detail',
    templateUrl: './store-grocery-item-detail.component.html'
})
export class StoreGroceryItemDetailComponent implements OnInit {
    storeGroceryItem: IStoreGroceryItem;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ storeGroceryItem }) => {
            this.storeGroceryItem = storeGroceryItem;
        });
    }

    previousState() {
        window.history.back();
    }
}

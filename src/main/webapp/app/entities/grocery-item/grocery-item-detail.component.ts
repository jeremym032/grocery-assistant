import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGroceryItem } from 'app/shared/model/grocery-item.model';

@Component({
    selector: 'jhi-grocery-item-detail',
    templateUrl: './grocery-item-detail.component.html'
})
export class GroceryItemDetailComponent implements OnInit {
    groceryItem: IGroceryItem;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ groceryItem }) => {
            this.groceryItem = groceryItem;
        });
    }

    previousState() {
        window.history.back();
    }
}

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IGroceryItem } from 'app/shared/model/grocery-item.model';
import { GroceryItemService } from './grocery-item.service';

@Component({
    selector: 'jhi-grocery-item-update',
    templateUrl: './grocery-item-update.component.html'
})
export class GroceryItemUpdateComponent implements OnInit {
    private _groceryItem: IGroceryItem;
    isSaving: boolean;

    constructor(private groceryItemService: GroceryItemService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ groceryItem }) => {
            this.groceryItem = groceryItem;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.groceryItem.id !== undefined) {
            this.subscribeToSaveResponse(this.groceryItemService.update(this.groceryItem));
        } else {
            this.subscribeToSaveResponse(this.groceryItemService.create(this.groceryItem));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IGroceryItem>>) {
        result.subscribe((res: HttpResponse<IGroceryItem>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get groceryItem() {
        return this._groceryItem;
    }

    set groceryItem(groceryItem: IGroceryItem) {
        this._groceryItem = groceryItem;
    }
}

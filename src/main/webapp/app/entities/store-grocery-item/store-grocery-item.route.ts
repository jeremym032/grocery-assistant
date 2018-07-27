import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { StoreGroceryItem } from 'app/shared/model/store-grocery-item.model';
import { StoreGroceryItemService } from './store-grocery-item.service';
import { StoreGroceryItemComponent } from './store-grocery-item.component';
import { StoreGroceryItemDetailComponent } from './store-grocery-item-detail.component';
import { StoreGroceryItemUpdateComponent } from './store-grocery-item-update.component';
import { StoreGroceryItemDeletePopupComponent } from './store-grocery-item-delete-dialog.component';
import { IStoreGroceryItem } from 'app/shared/model/store-grocery-item.model';

@Injectable({ providedIn: 'root' })
export class StoreGroceryItemResolve implements Resolve<IStoreGroceryItem> {
    constructor(private service: StoreGroceryItemService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((storeGroceryItem: HttpResponse<StoreGroceryItem>) => storeGroceryItem.body));
        }
        return of(new StoreGroceryItem());
    }
}

export const storeGroceryItemRoute: Routes = [
    {
        path: 'store-grocery-item',
        component: StoreGroceryItemComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StoreGroceryItems'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'store-grocery-item/:id/view',
        component: StoreGroceryItemDetailComponent,
        resolve: {
            storeGroceryItem: StoreGroceryItemResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StoreGroceryItems'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'store-grocery-item/new',
        component: StoreGroceryItemUpdateComponent,
        resolve: {
            storeGroceryItem: StoreGroceryItemResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StoreGroceryItems'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'store-grocery-item/:id/edit',
        component: StoreGroceryItemUpdateComponent,
        resolve: {
            storeGroceryItem: StoreGroceryItemResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StoreGroceryItems'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const storeGroceryItemPopupRoute: Routes = [
    {
        path: 'store-grocery-item/:id/delete',
        component: StoreGroceryItemDeletePopupComponent,
        resolve: {
            storeGroceryItem: StoreGroceryItemResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StoreGroceryItems'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

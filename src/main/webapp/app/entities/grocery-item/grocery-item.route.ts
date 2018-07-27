import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { GroceryItem } from 'app/shared/model/grocery-item.model';
import { GroceryItemService } from './grocery-item.service';
import { GroceryItemComponent } from './grocery-item.component';
import { GroceryItemDetailComponent } from './grocery-item-detail.component';
import { GroceryItemUpdateComponent } from './grocery-item-update.component';
import { GroceryItemDeletePopupComponent } from './grocery-item-delete-dialog.component';
import { IGroceryItem } from 'app/shared/model/grocery-item.model';

@Injectable({ providedIn: 'root' })
export class GroceryItemResolve implements Resolve<IGroceryItem> {
    constructor(private service: GroceryItemService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((groceryItem: HttpResponse<GroceryItem>) => groceryItem.body));
        }
        return of(new GroceryItem());
    }
}

export const groceryItemRoute: Routes = [
    {
        path: 'grocery-item',
        component: GroceryItemComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'GroceryItems'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'grocery-item/:id/view',
        component: GroceryItemDetailComponent,
        resolve: {
            groceryItem: GroceryItemResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'GroceryItems'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'grocery-item/new',
        component: GroceryItemUpdateComponent,
        resolve: {
            groceryItem: GroceryItemResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'GroceryItems'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'grocery-item/:id/edit',
        component: GroceryItemUpdateComponent,
        resolve: {
            groceryItem: GroceryItemResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'GroceryItems'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const groceryItemPopupRoute: Routes = [
    {
        path: 'grocery-item/:id/delete',
        component: GroceryItemDeletePopupComponent,
        resolve: {
            groceryItem: GroceryItemResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'GroceryItems'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

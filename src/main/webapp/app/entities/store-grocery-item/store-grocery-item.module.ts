import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GroceryAssistantSharedModule } from 'app/shared';
import {
    StoreGroceryItemComponent,
    StoreGroceryItemDetailComponent,
    StoreGroceryItemUpdateComponent,
    StoreGroceryItemDeletePopupComponent,
    StoreGroceryItemDeleteDialogComponent,
    storeGroceryItemRoute,
    storeGroceryItemPopupRoute
} from './';

const ENTITY_STATES = [...storeGroceryItemRoute, ...storeGroceryItemPopupRoute];

@NgModule({
    imports: [GroceryAssistantSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        StoreGroceryItemComponent,
        StoreGroceryItemDetailComponent,
        StoreGroceryItemUpdateComponent,
        StoreGroceryItemDeleteDialogComponent,
        StoreGroceryItemDeletePopupComponent
    ],
    entryComponents: [
        StoreGroceryItemComponent,
        StoreGroceryItemUpdateComponent,
        StoreGroceryItemDeleteDialogComponent,
        StoreGroceryItemDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GroceryAssistantStoreGroceryItemModule {}

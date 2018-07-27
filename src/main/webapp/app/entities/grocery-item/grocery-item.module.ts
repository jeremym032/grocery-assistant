import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GroceryAssistantSharedModule } from 'app/shared';
import {
    GroceryItemComponent,
    GroceryItemDetailComponent,
    GroceryItemUpdateComponent,
    GroceryItemDeletePopupComponent,
    GroceryItemDeleteDialogComponent,
    groceryItemRoute,
    groceryItemPopupRoute
} from './';

const ENTITY_STATES = [...groceryItemRoute, ...groceryItemPopupRoute];

@NgModule({
    imports: [GroceryAssistantSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        GroceryItemComponent,
        GroceryItemDetailComponent,
        GroceryItemUpdateComponent,
        GroceryItemDeleteDialogComponent,
        GroceryItemDeletePopupComponent
    ],
    entryComponents: [GroceryItemComponent, GroceryItemUpdateComponent, GroceryItemDeleteDialogComponent, GroceryItemDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GroceryAssistantGroceryItemModule {}

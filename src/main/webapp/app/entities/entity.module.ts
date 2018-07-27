import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { GroceryAssistantStoreModule } from './store/store.module';
import { GroceryAssistantGroceryItemModule } from './grocery-item/grocery-item.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        GroceryAssistantStoreModule,
        GroceryAssistantGroceryItemModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GroceryAssistantEntityModule {}

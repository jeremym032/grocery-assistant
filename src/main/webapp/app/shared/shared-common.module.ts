import { NgModule } from '@angular/core';

import { GroceryAssistantSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [GroceryAssistantSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [GroceryAssistantSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class GroceryAssistantSharedCommonModule {}

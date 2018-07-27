/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { GroceryAssistantTestModule } from '../../../test.module';
import { StoreGroceryItemUpdateComponent } from 'app/entities/store-grocery-item/store-grocery-item-update.component';
import { StoreGroceryItemService } from 'app/entities/store-grocery-item/store-grocery-item.service';
import { StoreGroceryItem } from 'app/shared/model/store-grocery-item.model';

describe('Component Tests', () => {
    describe('StoreGroceryItem Management Update Component', () => {
        let comp: StoreGroceryItemUpdateComponent;
        let fixture: ComponentFixture<StoreGroceryItemUpdateComponent>;
        let service: StoreGroceryItemService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GroceryAssistantTestModule],
                declarations: [StoreGroceryItemUpdateComponent]
            })
                .overrideTemplate(StoreGroceryItemUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(StoreGroceryItemUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StoreGroceryItemService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new StoreGroceryItem(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.storeGroceryItem = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new StoreGroceryItem();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.storeGroceryItem = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});

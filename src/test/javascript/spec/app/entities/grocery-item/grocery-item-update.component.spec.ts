/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { GroceryAssistantTestModule } from '../../../test.module';
import { GroceryItemUpdateComponent } from 'app/entities/grocery-item/grocery-item-update.component';
import { GroceryItemService } from 'app/entities/grocery-item/grocery-item.service';
import { GroceryItem } from 'app/shared/model/grocery-item.model';

describe('Component Tests', () => {
    describe('GroceryItem Management Update Component', () => {
        let comp: GroceryItemUpdateComponent;
        let fixture: ComponentFixture<GroceryItemUpdateComponent>;
        let service: GroceryItemService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GroceryAssistantTestModule],
                declarations: [GroceryItemUpdateComponent]
            })
                .overrideTemplate(GroceryItemUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(GroceryItemUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GroceryItemService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new GroceryItem(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.groceryItem = entity;
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
                    const entity = new GroceryItem();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.groceryItem = entity;
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

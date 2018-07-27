/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GroceryAssistantTestModule } from '../../../test.module';
import { StoreGroceryItemComponent } from 'app/entities/store-grocery-item/store-grocery-item.component';
import { StoreGroceryItemService } from 'app/entities/store-grocery-item/store-grocery-item.service';
import { StoreGroceryItem } from 'app/shared/model/store-grocery-item.model';

describe('Component Tests', () => {
    describe('StoreGroceryItem Management Component', () => {
        let comp: StoreGroceryItemComponent;
        let fixture: ComponentFixture<StoreGroceryItemComponent>;
        let service: StoreGroceryItemService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GroceryAssistantTestModule],
                declarations: [StoreGroceryItemComponent],
                providers: []
            })
                .overrideTemplate(StoreGroceryItemComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(StoreGroceryItemComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StoreGroceryItemService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new StoreGroceryItem(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.storeGroceryItems[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});

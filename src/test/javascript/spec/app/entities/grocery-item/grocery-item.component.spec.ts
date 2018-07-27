/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GroceryAssistantTestModule } from '../../../test.module';
import { GroceryItemComponent } from 'app/entities/grocery-item/grocery-item.component';
import { GroceryItemService } from 'app/entities/grocery-item/grocery-item.service';
import { GroceryItem } from 'app/shared/model/grocery-item.model';

describe('Component Tests', () => {
    describe('GroceryItem Management Component', () => {
        let comp: GroceryItemComponent;
        let fixture: ComponentFixture<GroceryItemComponent>;
        let service: GroceryItemService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GroceryAssistantTestModule],
                declarations: [GroceryItemComponent],
                providers: []
            })
                .overrideTemplate(GroceryItemComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(GroceryItemComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GroceryItemService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new GroceryItem(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.groceryItems[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});

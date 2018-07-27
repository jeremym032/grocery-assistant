/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GroceryAssistantTestModule } from '../../../test.module';
import { StoreGroceryItemDetailComponent } from 'app/entities/store-grocery-item/store-grocery-item-detail.component';
import { StoreGroceryItem } from 'app/shared/model/store-grocery-item.model';

describe('Component Tests', () => {
    describe('StoreGroceryItem Management Detail Component', () => {
        let comp: StoreGroceryItemDetailComponent;
        let fixture: ComponentFixture<StoreGroceryItemDetailComponent>;
        const route = ({ data: of({ storeGroceryItem: new StoreGroceryItem(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GroceryAssistantTestModule],
                declarations: [StoreGroceryItemDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(StoreGroceryItemDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(StoreGroceryItemDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.storeGroceryItem).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});

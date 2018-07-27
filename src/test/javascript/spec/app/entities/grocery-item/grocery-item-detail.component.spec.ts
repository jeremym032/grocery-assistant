/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GroceryAssistantTestModule } from '../../../test.module';
import { GroceryItemDetailComponent } from 'app/entities/grocery-item/grocery-item-detail.component';
import { GroceryItem } from 'app/shared/model/grocery-item.model';

describe('Component Tests', () => {
    describe('GroceryItem Management Detail Component', () => {
        let comp: GroceryItemDetailComponent;
        let fixture: ComponentFixture<GroceryItemDetailComponent>;
        const route = ({ data: of({ groceryItem: new GroceryItem(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GroceryAssistantTestModule],
                declarations: [GroceryItemDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(GroceryItemDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(GroceryItemDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.groceryItem).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});

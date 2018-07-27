/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { GroceryAssistantTestModule } from '../../../test.module';
import { StoreGroceryItemDeleteDialogComponent } from 'app/entities/store-grocery-item/store-grocery-item-delete-dialog.component';
import { StoreGroceryItemService } from 'app/entities/store-grocery-item/store-grocery-item.service';

describe('Component Tests', () => {
    describe('StoreGroceryItem Management Delete Component', () => {
        let comp: StoreGroceryItemDeleteDialogComponent;
        let fixture: ComponentFixture<StoreGroceryItemDeleteDialogComponent>;
        let service: StoreGroceryItemService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GroceryAssistantTestModule],
                declarations: [StoreGroceryItemDeleteDialogComponent]
            })
                .overrideTemplate(StoreGroceryItemDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(StoreGroceryItemDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StoreGroceryItemService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});

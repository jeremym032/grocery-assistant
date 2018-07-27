/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { GroceryAssistantTestModule } from '../../../test.module';
import { GroceryItemDeleteDialogComponent } from 'app/entities/grocery-item/grocery-item-delete-dialog.component';
import { GroceryItemService } from 'app/entities/grocery-item/grocery-item.service';

describe('Component Tests', () => {
    describe('GroceryItem Management Delete Component', () => {
        let comp: GroceryItemDeleteDialogComponent;
        let fixture: ComponentFixture<GroceryItemDeleteDialogComponent>;
        let service: GroceryItemService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GroceryAssistantTestModule],
                declarations: [GroceryItemDeleteDialogComponent]
            })
                .overrideTemplate(GroceryItemDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(GroceryItemDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GroceryItemService);
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

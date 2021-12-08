import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { JhipsterTestModule } from '../../../test.module';
import { ExperienciaDeleteDialogComponent } from 'app/entities/experiencia/experiencia-delete-dialog.component';
import { ExperienciaService } from 'app/entities/experiencia/experiencia.service';

describe('Component Tests', () => {
  describe('Experiencia Management Delete Component', () => {
    let comp: ExperienciaDeleteDialogComponent;
    let fixture: ComponentFixture<ExperienciaDeleteDialogComponent>;
    let service: ExperienciaService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterTestModule],
        declarations: [ExperienciaDeleteDialogComponent]
      })
        .overrideTemplate(ExperienciaDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ExperienciaDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ExperienciaService);
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

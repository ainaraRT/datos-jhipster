import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterTestModule } from '../../../test.module';
import { ExperienciaUpdateComponent } from 'app/entities/experiencia/experiencia-update.component';
import { ExperienciaService } from 'app/entities/experiencia/experiencia.service';
import { Experiencia } from 'app/shared/model/experiencia.model';

describe('Component Tests', () => {
  describe('Experiencia Management Update Component', () => {
    let comp: ExperienciaUpdateComponent;
    let fixture: ComponentFixture<ExperienciaUpdateComponent>;
    let service: ExperienciaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterTestModule],
        declarations: [ExperienciaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ExperienciaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ExperienciaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ExperienciaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Experiencia(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Experiencia();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterTestModule } from '../../../test.module';
import { ExperienciaDetailComponent } from 'app/entities/experiencia/experiencia-detail.component';
import { Experiencia } from 'app/shared/model/experiencia.model';

describe('Component Tests', () => {
  describe('Experiencia Management Detail Component', () => {
    let comp: ExperienciaDetailComponent;
    let fixture: ComponentFixture<ExperienciaDetailComponent>;
    const route = ({ data: of({ experiencia: new Experiencia(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterTestModule],
        declarations: [ExperienciaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ExperienciaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ExperienciaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.experiencia).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

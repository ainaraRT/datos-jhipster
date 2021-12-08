import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { IExperiencia, Experiencia } from 'app/shared/model/experiencia.model';
import { ExperienciaService } from './experiencia.service';

@Component({
  selector: 'jhi-experiencia-update',
  templateUrl: './experiencia-update.component.html'
})
export class ExperienciaUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    titulo: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(255)]],
    descripcion: [null, [Validators.minLength(10), Validators.maxLength(255)]],
    localizacion: [null, [Validators.minLength(10), Validators.maxLength(255)]],
    fecha: []
  });

  constructor(protected experienciaService: ExperienciaService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ experiencia }) => {
      this.updateForm(experiencia);
    });
  }

  updateForm(experiencia: IExperiencia) {
    this.editForm.patchValue({
      id: experiencia.id,
      titulo: experiencia.titulo,
      descripcion: experiencia.descripcion,
      localizacion: experiencia.localizacion,
      fecha: experiencia.fecha != null ? experiencia.fecha.format(DATE_TIME_FORMAT) : null
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const experiencia = this.createFromForm();
    if (experiencia.id !== undefined) {
      this.subscribeToSaveResponse(this.experienciaService.update(experiencia));
    } else {
      this.subscribeToSaveResponse(this.experienciaService.create(experiencia));
    }
  }

  private createFromForm(): IExperiencia {
    return {
      ...new Experiencia(),
      id: this.editForm.get(['id']).value,
      titulo: this.editForm.get(['titulo']).value,
      descripcion: this.editForm.get(['descripcion']).value,
      localizacion: this.editForm.get(['localizacion']).value,
      fecha: this.editForm.get(['fecha']).value != null ? moment(this.editForm.get(['fecha']).value, DATE_TIME_FORMAT) : undefined
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IExperiencia>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}

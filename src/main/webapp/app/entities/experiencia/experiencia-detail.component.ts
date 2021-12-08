import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IExperiencia } from 'app/shared/model/experiencia.model';

@Component({
  selector: 'jhi-experiencia-detail',
  templateUrl: './experiencia-detail.component.html'
})
export class ExperienciaDetailComponent implements OnInit {
  experiencia: IExperiencia;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ experiencia }) => {
      this.experiencia = experiencia;
    });
  }

  previousState() {
    window.history.back();
  }
}

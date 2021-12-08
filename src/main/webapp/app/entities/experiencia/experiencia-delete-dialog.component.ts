import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IExperiencia } from 'app/shared/model/experiencia.model';
import { ExperienciaService } from './experiencia.service';

@Component({
  selector: 'jhi-experiencia-delete-dialog',
  templateUrl: './experiencia-delete-dialog.component.html'
})
export class ExperienciaDeleteDialogComponent {
  experiencia: IExperiencia;

  constructor(
    protected experienciaService: ExperienciaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.experienciaService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'experienciaListModification',
        content: 'Deleted an experiencia'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-experiencia-delete-popup',
  template: ''
})
export class ExperienciaDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ experiencia }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ExperienciaDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.experiencia = experiencia;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/experiencia', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/experiencia', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}

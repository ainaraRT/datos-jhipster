import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Experiencia } from 'app/shared/model/experiencia.model';
import { ExperienciaService } from './experiencia.service';
import { ExperienciaComponent } from './experiencia.component';
import { ExperienciaDetailComponent } from './experiencia-detail.component';
import { ExperienciaUpdateComponent } from './experiencia-update.component';
import { ExperienciaDeletePopupComponent } from './experiencia-delete-dialog.component';
import { IExperiencia } from 'app/shared/model/experiencia.model';

@Injectable({ providedIn: 'root' })
export class ExperienciaResolve implements Resolve<IExperiencia> {
  constructor(private service: ExperienciaService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IExperiencia> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Experiencia>) => response.ok),
        map((experiencia: HttpResponse<Experiencia>) => experiencia.body)
      );
    }
    return of(new Experiencia());
  }
}

export const experienciaRoute: Routes = [
  {
    path: '',
    component: ExperienciaComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'Experiencias'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ExperienciaDetailComponent,
    resolve: {
      experiencia: ExperienciaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Experiencias'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ExperienciaUpdateComponent,
    resolve: {
      experiencia: ExperienciaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Experiencias'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ExperienciaUpdateComponent,
    resolve: {
      experiencia: ExperienciaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Experiencias'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const experienciaPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ExperienciaDeletePopupComponent,
    resolve: {
      experiencia: ExperienciaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Experiencias'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];

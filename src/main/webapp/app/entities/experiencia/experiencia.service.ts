import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IExperiencia } from 'app/shared/model/experiencia.model';

type EntityResponseType = HttpResponse<IExperiencia>;
type EntityArrayResponseType = HttpResponse<IExperiencia[]>;

@Injectable({ providedIn: 'root' })
export class ExperienciaService {
  public resourceUrl = SERVER_API_URL + 'api/experiencias';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/experiencias';

  constructor(protected http: HttpClient) {}

  create(experiencia: IExperiencia): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(experiencia);
    return this.http
      .post<IExperiencia>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(experiencia: IExperiencia): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(experiencia);
    return this.http
      .put<IExperiencia>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IExperiencia>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IExperiencia[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IExperiencia[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(experiencia: IExperiencia): IExperiencia {
    const copy: IExperiencia = Object.assign({}, experiencia, {
      fecha: experiencia.fecha != null && experiencia.fecha.isValid() ? experiencia.fecha.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fecha = res.body.fecha != null ? moment(res.body.fecha) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((experiencia: IExperiencia) => {
        experiencia.fecha = experiencia.fecha != null ? moment(experiencia.fecha) : null;
      });
    }
    return res;
  }
}

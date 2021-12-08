import { Moment } from 'moment';

export interface IExperiencia {
  id?: number;
  titulo?: string;
  descripcion?: string;
  localizacion?: string;
  fecha?: Moment;
}

export class Experiencia implements IExperiencia {
  constructor(
    public id?: number,
    public titulo?: string,
    public descripcion?: string,
    public localizacion?: string,
    public fecha?: Moment
  ) {}
}

import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSharedModule } from 'app/shared/shared.module';
import { ExperienciaComponent } from './experiencia.component';
import { ExperienciaDetailComponent } from './experiencia-detail.component';
import { ExperienciaUpdateComponent } from './experiencia-update.component';
import { ExperienciaDeletePopupComponent, ExperienciaDeleteDialogComponent } from './experiencia-delete-dialog.component';
import { experienciaRoute, experienciaPopupRoute } from './experiencia.route';

const ENTITY_STATES = [...experienciaRoute, ...experienciaPopupRoute];

@NgModule({
  imports: [JhipsterSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ExperienciaComponent,
    ExperienciaDetailComponent,
    ExperienciaUpdateComponent,
    ExperienciaDeleteDialogComponent,
    ExperienciaDeletePopupComponent
  ],
  entryComponents: [ExperienciaDeleteDialogComponent]
})
export class JhipsterExperienciaModule {}

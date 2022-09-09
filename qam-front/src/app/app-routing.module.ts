import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RecoverPasswordComponent }   from './components/recover-password/recover-password.component';
import { AppComponent } from './app.component';
import { GenericTableComponent } from './components/generic-table/generic-table.component';
import { MenuComponent } from './components/menu/menu.component';
import { GenericCatalogueFormComponent } from './components/generic-catalogue-form/generic-catalogue-form.component';
import { FormRolComponent } from './components/form-rol/form-rol.component';
import { FormServicioComponent } from './components/form-servicio/form-servicio.component';
import { FormInterfazComponent } from './components/form-interfaz/form-interfaz.component';
import { FormClienteComponent } from './components/form-cliente/form-cliente.component';
import { FormUsuarioComponent } from './components/form-usuario/form-usuario.component';
import { LoginComponent } from './components/login/login.component';
import { FormParametroComponent } from './components/form-parametro/form-parametro.component';
import { FormCorteComponent } from './components/form-corte/form-corte.component';
import { FormRincidenciasComponent } from './components/form-rincidencias/form-rincidencias.component';
import { FormRfacturacionComponent } from './components/form-rfacturacion/form-rfacturacion.component';
import { FormResetcontraComponent } from './components/form-resetcontra/form-resetcontra.component';
import { FormPromocionComponent } from './components/form-promocion/form-promocion.component';
import { FormSegmentoComponent } from './components/form-segmento/form-segmento.component';
import { NomReporteComponent } from './components/nom-reporte/nom-reporte.component';
import { NomListaComponent } from './components/nom-lista/nom-lista.component';
import { FormCargaComponent } from './components/form-carga/form-carga.component';
import { FormCargaMexComponent} from './components/form-carga-mex/form-carga-mex.component';
import { FormCargaColComponent} from './components/form-carga-col/form-carga-col.component';


const routes: Routes = [
  { path: '', component:LoginComponent},
  { path: 'menu', component:MenuComponent},
  { path: 'admin/form-generic/:type', component:GenericCatalogueFormComponent},
  { path: 'admin/form-usuario', component:FormUsuarioComponent},
  { path: 'admin/form-cliente', component:FormClienteComponent},
  { path: 'admin/form-interfaz', component:FormInterfazComponent},
  { path: 'admin/form-servicio', component:FormServicioComponent},
  { path: 'admin/form-rol', component:FormRolComponent},
  { path: 'admin/form-parametro', component:FormParametroComponent},
  { path: 'admin/form-corte', component:FormCorteComponent},
  { path: 'admin/form-rincidencias', component:FormRincidenciasComponent},
  { path: 'admin/form-rfacturacion', component:FormRfacturacionComponent},
  { path: 'admin/form-segmento', component:FormSegmentoComponent},
  { path: 'admin/nom-reporte', component:NomReporteComponent},
  { path: 'admin/nom-lista', component:NomListaComponent},
  { path: 'recupera_contrasena', component:FormResetcontraComponent},
  { path: 'admin/form-carga', component:FormCargaComponent},
  { path: 'admin/form-carga-mex',component:FormCargaMexComponent},
  { path: 'admin/form-carga-col',component:FormCargaColComponent},
  
 

  { path: 'admin/form-promocion', component:FormPromocionComponent},
  
  { path: 'admin/:type', component:GenericTableComponent},
  { path: 'login/recuperarPassword/:token', component: RecoverPasswordComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

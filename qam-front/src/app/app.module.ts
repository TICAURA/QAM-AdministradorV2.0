import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';


import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { RecoverPasswordComponent } from './components/recover-password/recover-password.component';
import { GenericTableComponent } from './components/generic-table/generic-table.component';
import { AssingTableComponent } from './components/assing-table/assing-table.component';
import { GenericCatalogueFormComponent } from './components/generic-catalogue-form/generic-catalogue-form.component';
import { MenuComponent } from './components/menu/menu.component';
import { HeaderMenuComponent } from './components/header-menu/header-menu.component';
import { FooterComponent } from './components/footer/footer.component';
import { FormUsuarioComponent } from './components/form-usuario/form-usuario.component';
import { FormClienteComponent } from './components/form-cliente/form-cliente.component';
import { FormInterfazComponent } from './components/form-interfaz/form-interfaz.component';
import { FormServicioComponent } from './components/form-servicio/form-servicio.component';
import { FormRolComponent } from './components/form-rol/form-rol.component';
import { LoginComponent } from './components/login/login.component';
import { HttpClientModule } from '@angular/common/http';

import { ColorPickerModule } from 'ngx-color-picker';
import { FormParametroComponent } from './components/form-parametro/form-parametro.component';
import { FilterParametroPipe } from './pipes/filter-parametro.pipe';
import { FormCorteComponent } from './components/form-corte/form-corte.component';
import { FormRincidenciasComponent } from './components/form-rincidencias/form-rincidencias.component';
import { FormRfacturacionComponent } from './components/form-rfacturacion/form-rfacturacion.component';
import { FormResetcontraComponent } from './components/form-resetcontra/form-resetcontra.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FormPromocionComponent } from './components/form-promocion/form-promocion.component';

import { ReactiveFormsModule } from '@angular/forms';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {MatNativeDateModule} from '@angular/material/core';
import { FormSegmentoComponent } from './components/form-segmento/form-segmento.component';
import { NomReporteComponent } from './components/nom-reporte/nom-reporte.component';
import { NomListaComponent } from './components/nom-lista/nom-lista.component';
import { FormCargaComponent } from './components/form-carga/form-carga.component';
import { CargaColComponent } from './components/carga-col/carga-col.component';
import { CargaMexComponent } from './components/carga-mex/carga-mex.component';
import { CargaMasivaComponent } from './components/carga-masiva/carga-masiva.component';

@NgModule({
  declarations: [
    AppComponent,
    RecoverPasswordComponent,
    GenericTableComponent,
    AssingTableComponent,
    GenericCatalogueFormComponent,
    MenuComponent,
    HeaderMenuComponent,
    FooterComponent,
    FormUsuarioComponent,
    FormClienteComponent,
    FormInterfazComponent,
    FormServicioComponent,
    FormRolComponent,
    LoginComponent,
    FormParametroComponent,
    FilterParametroPipe,
    FormCorteComponent,
    FormRincidenciasComponent,
    FormRfacturacionComponent,
    FormResetcontraComponent,
    FormPromocionComponent,
    FormSegmentoComponent,
    NomReporteComponent,
    NomListaComponent,
    FormCargaComponent,
    CargaColComponent,
    CargaMexComponent,
    CargaMasivaComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ColorPickerModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MatDatepickerModule, 
    MatNativeDateModule,
    MatInputModule,
    MatFormFieldModule,
    ReactiveFormsModule,
 
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }

import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ToolbarComponent } from './core/toolbar/toolbar.component';
import { HomePageComponent } from './page/home-page/home-page.component';
import { AppMaterialModule } from './app-material.module';
import { PlaceholderCardComponent } from './core/placeholder-card/placeholder-card.component';
import { CatService } from './service/cat.service';
import { HttpClientModule } from '@angular/common/http';

@NgModule({
  declarations: [
    AppComponent,
    ToolbarComponent,
    PlaceholderCardComponent,
    HomePageComponent,
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    AppMaterialModule,
    BrowserAnimationsModule,
  ],
  providers: [
    CatService,
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }

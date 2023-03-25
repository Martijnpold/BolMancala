import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ToolbarComponent } from './core/toolbar/toolbar.component';
import { GamesPageComponent } from './page/games-page/games-page.component';
import { AppMaterialModule } from './app-material.module';
import { HttpClientModule } from '@angular/common/http';
import { InvitesPageComponent } from './page/invites-page/invites-page.component';
import { ReactiveFormsModule } from '@angular/forms';
import { GamePageComponent } from './page/game-page/game-page.component';
import { PitComponent } from './core/pit/pit.component';
import { BoardComponent } from './core/board/board.component';

@NgModule({
  declarations: [
    AppComponent,
    ToolbarComponent,
    GamesPageComponent,
    GamePageComponent,
    InvitesPageComponent,
    PitComponent,
    BoardComponent,
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    AppRoutingModule,
    AppMaterialModule,
    ReactiveFormsModule,
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }

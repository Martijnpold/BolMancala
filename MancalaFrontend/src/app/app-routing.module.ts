import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { GamesPageComponent } from './page/games-page/games-page.component';
import { GamePageComponent } from './page/game-page/game-page.component';
import { InvitesPageComponent } from './page/invites-page/invites-page.component';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'games',
    pathMatch: 'full'
  },
  {
    path: 'games',
    children: [
      {
        path: '',
        component: GamesPageComponent
      },
      {
        path: ':id',
        component: GamePageComponent
      }
    ]
  },
  {
    path: 'invites',
    component: InvitesPageComponent
  },
]

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

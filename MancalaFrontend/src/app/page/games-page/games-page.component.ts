import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { Game } from 'src/app/model/game';
import { SelfGameService } from 'src/app/service/self.game.service';

@Component({
  selector: 'app-games-page',
  templateUrl: './games-page.component.html',
  styleUrls: ['./games-page.component.scss']
})
export class GamesPageComponent {
  games$: Observable<Game[]>;

  constructor(private selfGameService: SelfGameService, private router: Router) {
    this.games$ = selfGameService.getGames()
  }

  navigateTo(game: Game) {
    this.router.navigateByUrl(`/games`)
  }
}

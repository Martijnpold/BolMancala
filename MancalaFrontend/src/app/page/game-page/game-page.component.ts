import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { interval, Subscription } from 'rxjs';
import { FullGame } from 'src/app/model/fullgame';
import { Pit } from 'src/app/model/pit';
import { SelfGameService } from 'src/app/service/self.game.service';

@Component({
  selector: 'app-game-page',
  templateUrl: './game-page.component.html',
  styleUrls: ['./game-page.component.scss']
})
export class GamePageComponent implements OnInit, OnDestroy {
  game: FullGame;
  side: string;
  id: string;

  autoRefresh: Subscription;

  constructor(private route: ActivatedRoute, private selfGameService: SelfGameService) {
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.id = params['id'];
      this.load();

      this.autoRefresh = interval(500).subscribe(() => {
        this.load();
      })
    })
  }

  ngOnDestroy() {
    this.autoRefresh.unsubscribe();
  }

  load() {
    this.selfGameService.getGame(this.id).subscribe(game => {
      this.game = game;
      this.side = game.self.side;
    });
  }

  doMove(index: number) {
    this.selfGameService.doMove(this.id, index).subscribe(result => {
      this.load();
    })
  }

  pit(index: number): Pit {
    return this.game.board.pits[index]
  }
}

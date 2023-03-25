import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { FullGame } from 'src/app/model/fullgame';
import { Pit } from 'src/app/model/pit';
import { SelfGameService } from 'src/app/service/self.game.service';

@Component({
  selector: 'app-game-page',
  templateUrl: './game-page.component.html',
  styleUrls: ['./game-page.component.scss']
})
export class GamePageComponent implements OnInit {
  game: FullGame;
  side: string;
  id: string;

  constructor(private route: ActivatedRoute, private selfGameService: SelfGameService) {
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.id = params['id'];
      this.load();
    })
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

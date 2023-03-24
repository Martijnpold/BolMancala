import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { FullGame } from 'src/app/model/fullgame';
import { SelfGameService } from 'src/app/service/self.game.service';

@Component({
  selector: 'app-game-page',
  templateUrl: './game-page.component.html',
  styleUrls: ['./game-page.component.scss']
})
export class GamePageComponent implements OnInit {
  game$: Observable<FullGame>;

  constructor(private route: ActivatedRoute, private selfGameService: SelfGameService) {
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.game$ = this.selfGameService.getGame(params['id']);
    })
  }
}

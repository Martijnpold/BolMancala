import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { Game } from '../model/game';

@Injectable({
  providedIn: 'root'
})
export class SelfGameService {

  baseUrl = 'http://localhost:8080/api'

  constructor(private _http: HttpClient) { }

  getGames() {
    return this._http.get<Game[]>(`${this.baseUrl}/games`)
    // .pipe(map((games: object[]) => {
    //   return games.map(game => {
    //     return Game.fromDoc(game);
    //   });
    // }));
  }
}


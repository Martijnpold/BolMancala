import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { Game } from '../model/game';

@Injectable({
  providedIn: 'root'
})
export class SelfGameService {

  constructor(private _http: HttpClient) { }

  getGames(): Observable<Game[]> {
    return this._http.get<Game[]>(`api/games`)
    // .pipe(map((games: object[]) => {
    //   return games.map(game => {
    //     return Game.fromDoc(game);
    //   });
    // }));
  }
}

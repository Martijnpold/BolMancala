import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { first, Observable } from 'rxjs';
import { FullGame } from '../model/fullgame';
import { Game } from '../model/game';

@Injectable({
  providedIn: 'root'
})
export class SelfGameService {

  constructor(private _http: HttpClient) { }

  getGames(): Observable<Game[]> {
    return this._http.get<Game[]>(`api/users/current/games`)
      .pipe(first())
  }

  getGame(id: string): Observable<FullGame> {
    return this._http.get<FullGame>(`api/users/current/games/${id}`)
      .pipe(first())
  }

  doMove(id: string, index: number): Observable<FullGame> {
    return this._http.post<FullGame>(`api/users/current/games/${id}/move`, { pitIndex: index })
      .pipe(first())
  }
}

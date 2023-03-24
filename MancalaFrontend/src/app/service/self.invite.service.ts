import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { first, Observable } from 'rxjs';
import { Invite } from '../model/invite';

@Injectable({
  providedIn: 'root'
})
export class SelfInviteService {

  constructor(private _http: HttpClient) { }

  invite(email: string): Observable<Invite> {
    return this._http.post<Invite>(`api/users/current/invites`, { invitee: email })
      .pipe(first())
  }

  getIncoming(): Observable<Invite[]> {
    return this._http.get<Invite[]>(`api/users/current/invites`)
      .pipe(first())
  }

  getOutgoing(): Observable<Invite[]> {
    return this._http.get<Invite[]>(`api/users/current/invites/out`)
      .pipe(first())
  }

  accept(id: string): Observable<any> {
    return this._http.post(`api/users/current/invites/${id}/accept`, {})
      .pipe(first())
  }

  reject(id: string): Observable<any> {
    return this._http.post(`api/users/current/invites/${id}/reject`, {})
      .pipe(first())
  }

  cancel(id: string): Observable<any> {
    return this._http.post(`api/users/current/invites/${id}/cancel`, {})
      .pipe(first())
  }
}

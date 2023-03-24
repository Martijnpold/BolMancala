import { Component } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { Observable } from 'rxjs';
import { Invite } from 'src/app/model/invite';
import { SelfInviteService } from 'src/app/service/self.invite.service';

@Component({
  selector: 'app-invites-page',
  templateUrl: './invites-page.component.html',
  styleUrls: ['./invites-page.component.scss']
})
export class InvitesPageComponent {
  in$: Observable<Invite[]>;
  out$: Observable<Invite[]>;

  emailFormControl = new FormControl('', [Validators.required, Validators.email]);
  inputText: string;

  constructor(private selfInviteService: SelfInviteService) {
    this.load()
  }

  invite() {
    if (this.emailFormControl.valid) {
      this.selfInviteService.invite(this.emailFormControl.value!!).subscribe(result => {
        this.load()
      })
    }
  }

  accept(invite: Invite) {
    this.selfInviteService.accept(invite.id).subscribe(result => {
      this.load()
    })
  }

  reject(invite: Invite) {
    this.selfInviteService.reject(invite.id).subscribe(result => {
      this.load()
    })
  }

  cancel(invite: Invite) {
    console.log(`Cancelling ${invite.id}`)
    this.selfInviteService.cancel(invite.id).subscribe(result => {
      this.load()
    })
  }

  load() {
    this.in$ = this.selfInviteService.getIncoming();
    this.out$ = this.selfInviteService.getOutgoing();
  }
}

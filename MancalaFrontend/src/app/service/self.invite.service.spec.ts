import { TestBed } from '@angular/core/testing';

import { SelfInviteService } from './self.invite.service';

describe('SelfInviteService', () => {
  let service: SelfInviteService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SelfInviteService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

import { User } from "./user";

export class Invite {
    id: string;
    inviter: User;
    invitee: User;
}

export class Game {
    id: string;
    name: string;
    turn: string;
    winner: string;

    static fromDoc(data: any): Game {
        const obj = new Game();
        Object.assign(obj, { ...data });
        return obj;
    }
}

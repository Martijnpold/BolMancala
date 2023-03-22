export class Cat {
    tags: [];
    createdAt: string;
    updatedAt: string;
    validated: boolean;
    owner: string;
    file: string;
    mimetype: string;
    size: string;
    url: string;

    constructor() {
    }

    static fromDoc(data: any): Cat {
        const obj = new Cat();
        Object.assign(obj, { ...data });
        return obj;
    }
}

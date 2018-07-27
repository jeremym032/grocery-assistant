export interface IStore {
    id?: number;
    name?: string;
    address?: string;
}

export class Store implements IStore {
    constructor(public id?: number, public name?: string, public address?: string) {}
}

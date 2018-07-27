import { IStore } from 'app/shared/model//store.model';
import { IItem } from 'app/shared/model/item.model';

export interface IStoreGroceryItem {
    id?: number;
    price?: number;
    favoriteInd?: boolean;
    store?: IStore;
    item?: IItem;
}

export class StoreGroceryItem implements IStoreGroceryItem {
    constructor(public id?: number, public price?: number, public favoriteInd?: boolean, public store?: IStore, public item?: IItem) {
        this.favoriteInd = false;
    }
}

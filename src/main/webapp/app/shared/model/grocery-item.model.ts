export interface IGroceryItem {
    id?: number;
    name?: string;
    size?: number;
    unitOfMeasurement?: string;
    description?: string;
}

export class GroceryItem implements IGroceryItem {
    constructor(
        public id?: number,
        public name?: string,
        public size?: number,
        public unitOfMeasurement?: string,
        public description?: string
    ) {}
}

export interface IBookClassification {
  id?: number;
  name?: string;
}

export class BookClassification implements IBookClassification {
  constructor(public id?: number, public name?: string) {}
}

import { Moment } from 'moment';
import { IBookClassification } from 'app/shared/model/book-classification.model';

export interface IBook {
  id?: number;
  userId?: number;
  bookName?: string;
  bookImage?: string;
  bookPages?: number;
  isBorrow?: boolean;
  createTime?: Moment;
  bookClassificationId?: IBookClassification;
}

export class Book implements IBook {
  constructor(
    public id?: number,
    public userId?: number,
    public bookName?: string,
    public bookImage?: string,
    public bookPages?: number,
    public isBorrow?: boolean,
    public createTime?: Moment,
    public bookClassificationId?: IBookClassification
  ) {
    this.isBorrow = this.isBorrow || false;
  }
}

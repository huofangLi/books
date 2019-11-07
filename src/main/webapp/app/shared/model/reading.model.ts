import { Moment } from 'moment';
import { IBook } from 'app/shared/model/book.model';

export interface IReading {
  id?: number;
  page?: number;
  createTime?: Moment;
  bookId?: IBook;
}

export class Reading implements IReading {
  constructor(public id?: number, public page?: number, public createTime?: Moment, public bookId?: IBook) {}
}

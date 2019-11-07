import { Moment } from 'moment';
import { IBook } from 'app/shared/model/book.model';

export interface IBookSummary {
  id?: number;
  comment?: string;
  createTime?: Moment;
  bookId?: IBook;
}

export class BookSummary implements IBookSummary {
  constructor(public id?: number, public comment?: string, public createTime?: Moment, public bookId?: IBook) {}
}

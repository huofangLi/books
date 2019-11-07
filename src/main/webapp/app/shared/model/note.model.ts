import { Moment } from 'moment';
import { IBook } from 'app/shared/model/book.model';

export interface INote {
  id?: number;
  userId?: number;
  note?: string;
  createTime?: Moment;
  bookId?: IBook;
}

export class Note implements INote {
  constructor(public id?: number, public userId?: number, public note?: string, public createTime?: Moment, public bookId?: IBook) {}
}

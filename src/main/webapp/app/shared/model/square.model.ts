import { Moment } from 'moment';
import { ISquare } from 'app/shared/model/square.model';
import { IBook } from 'app/shared/model/book.model';

export interface ISquare {
  id?: number;
  userId?: number;
  bookSaying?: string;
  comment?: string;
  createTime?: Moment;
  parentId?: ISquare;
  bookId?: IBook;
}

export class Square implements ISquare {
  constructor(
    public id?: number,
    public userId?: number,
    public bookSaying?: string,
    public comment?: string,
    public createTime?: Moment,
    public parentId?: ISquare,
    public bookId?: IBook
  ) {}
}

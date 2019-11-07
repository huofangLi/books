import { Moment } from 'moment';
import { IBook } from 'app/shared/model/book.model';

export interface IWishList {
  id?: number;
  userId?: number;
  createTime?: Moment;
  bookId?: IBook;
}

export class WishList implements IWishList {
  constructor(public id?: number, public userId?: number, public createTime?: Moment, public bookId?: IBook) {}
}

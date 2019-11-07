import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IWishList } from 'app/shared/model/wish-list.model';

type EntityResponseType = HttpResponse<IWishList>;
type EntityArrayResponseType = HttpResponse<IWishList[]>;

@Injectable({ providedIn: 'root' })
export class WishListService {
  public resourceUrl = SERVER_API_URL + 'api/wish-lists';

  constructor(protected http: HttpClient) {}

  create(wishList: IWishList): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(wishList);
    return this.http
      .post<IWishList>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(wishList: IWishList): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(wishList);
    return this.http
      .put<IWishList>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IWishList>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IWishList[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(wishList: IWishList): IWishList {
    const copy: IWishList = Object.assign({}, wishList, {
      createTime: wishList.createTime != null && wishList.createTime.isValid() ? wishList.createTime.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createTime = res.body.createTime != null ? moment(res.body.createTime) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((wishList: IWishList) => {
        wishList.createTime = wishList.createTime != null ? moment(wishList.createTime) : null;
      });
    }
    return res;
  }
}

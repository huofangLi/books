import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IBookSummary } from 'app/shared/model/book-summary.model';

type EntityResponseType = HttpResponse<IBookSummary>;
type EntityArrayResponseType = HttpResponse<IBookSummary[]>;

@Injectable({ providedIn: 'root' })
export class BookSummaryService {
  public resourceUrl = SERVER_API_URL + 'api/book-summaries';

  constructor(protected http: HttpClient) {}

  create(bookSummary: IBookSummary): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(bookSummary);
    return this.http
      .post<IBookSummary>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(bookSummary: IBookSummary): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(bookSummary);
    return this.http
      .put<IBookSummary>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IBookSummary>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IBookSummary[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(bookSummary: IBookSummary): IBookSummary {
    const copy: IBookSummary = Object.assign({}, bookSummary, {
      createTime: bookSummary.createTime != null && bookSummary.createTime.isValid() ? bookSummary.createTime.toJSON() : null
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
      res.body.forEach((bookSummary: IBookSummary) => {
        bookSummary.createTime = bookSummary.createTime != null ? moment(bookSummary.createTime) : null;
      });
    }
    return res;
  }
}

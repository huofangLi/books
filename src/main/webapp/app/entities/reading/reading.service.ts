import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IReading } from 'app/shared/model/reading.model';

type EntityResponseType = HttpResponse<IReading>;
type EntityArrayResponseType = HttpResponse<IReading[]>;

@Injectable({ providedIn: 'root' })
export class ReadingService {
  public resourceUrl = SERVER_API_URL + 'api/readings';

  constructor(protected http: HttpClient) {}

  create(reading: IReading): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(reading);
    return this.http
      .post<IReading>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(reading: IReading): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(reading);
    return this.http
      .put<IReading>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IReading>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IReading[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(reading: IReading): IReading {
    const copy: IReading = Object.assign({}, reading, {
      createTime: reading.createTime != null && reading.createTime.isValid() ? reading.createTime.toJSON() : null
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
      res.body.forEach((reading: IReading) => {
        reading.createTime = reading.createTime != null ? moment(reading.createTime) : null;
      });
    }
    return res;
  }
}

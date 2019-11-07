import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITopic } from 'app/shared/model/topic.model';

type EntityResponseType = HttpResponse<ITopic>;
type EntityArrayResponseType = HttpResponse<ITopic[]>;

@Injectable({ providedIn: 'root' })
export class TopicService {
  public resourceUrl = SERVER_API_URL + 'api/topics';

  constructor(protected http: HttpClient) {}

  create(topic: ITopic): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(topic);
    return this.http
      .post<ITopic>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(topic: ITopic): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(topic);
    return this.http
      .put<ITopic>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITopic>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITopic[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(topic: ITopic): ITopic {
    const copy: ITopic = Object.assign({}, topic, {
      createTime: topic.createTime != null && topic.createTime.isValid() ? topic.createTime.toJSON() : null
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
      res.body.forEach((topic: ITopic) => {
        topic.createTime = topic.createTime != null ? moment(topic.createTime) : null;
      });
    }
    return res;
  }
}

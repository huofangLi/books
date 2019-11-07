import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISpeciclTopic } from 'app/shared/model/specicl-topic.model';

type EntityResponseType = HttpResponse<ISpeciclTopic>;
type EntityArrayResponseType = HttpResponse<ISpeciclTopic[]>;

@Injectable({ providedIn: 'root' })
export class SpeciclTopicService {
  public resourceUrl = SERVER_API_URL + 'api/specicl-topics';

  constructor(protected http: HttpClient) {}

  create(speciclTopic: ISpeciclTopic): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(speciclTopic);
    return this.http
      .post<ISpeciclTopic>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(speciclTopic: ISpeciclTopic): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(speciclTopic);
    return this.http
      .put<ISpeciclTopic>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISpeciclTopic>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISpeciclTopic[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(speciclTopic: ISpeciclTopic): ISpeciclTopic {
    const copy: ISpeciclTopic = Object.assign({}, speciclTopic, {
      createTime: speciclTopic.createTime != null && speciclTopic.createTime.isValid() ? speciclTopic.createTime.toJSON() : null
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
      res.body.forEach((speciclTopic: ISpeciclTopic) => {
        speciclTopic.createTime = speciclTopic.createTime != null ? moment(speciclTopic.createTime) : null;
      });
    }
    return res;
  }
}

import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IBookClassification } from 'app/shared/model/book-classification.model';

type EntityResponseType = HttpResponse<IBookClassification>;
type EntityArrayResponseType = HttpResponse<IBookClassification[]>;

@Injectable({ providedIn: 'root' })
export class BookClassificationService {
  public resourceUrl = SERVER_API_URL + 'api/book-classifications';

  constructor(protected http: HttpClient) {}

  create(bookClassification: IBookClassification): Observable<EntityResponseType> {
    return this.http.post<IBookClassification>(this.resourceUrl, bookClassification, { observe: 'response' });
  }

  update(bookClassification: IBookClassification): Observable<EntityResponseType> {
    return this.http.put<IBookClassification>(this.resourceUrl, bookClassification, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IBookClassification>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBookClassification[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}

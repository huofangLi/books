import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IExperience } from 'app/shared/model/experience.model';

type EntityResponseType = HttpResponse<IExperience>;
type EntityArrayResponseType = HttpResponse<IExperience[]>;

@Injectable({ providedIn: 'root' })
export class ExperienceService {
  public resourceUrl = SERVER_API_URL + 'api/experiences';

  constructor(protected http: HttpClient) {}

  create(experience: IExperience): Observable<EntityResponseType> {
    return this.http.post<IExperience>(this.resourceUrl, experience, { observe: 'response' });
  }

  update(experience: IExperience): Observable<EntityResponseType> {
    return this.http.put<IExperience>(this.resourceUrl, experience, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IExperience>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IExperience[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}

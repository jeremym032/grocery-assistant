import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IGroceryItem } from 'app/shared/model/grocery-item.model';

type EntityResponseType = HttpResponse<IGroceryItem>;
type EntityArrayResponseType = HttpResponse<IGroceryItem[]>;

@Injectable({ providedIn: 'root' })
export class GroceryItemService {
    private resourceUrl = SERVER_API_URL + 'api/grocery-items';

    constructor(private http: HttpClient) {}

    create(groceryItem: IGroceryItem): Observable<EntityResponseType> {
        return this.http.post<IGroceryItem>(this.resourceUrl, groceryItem, { observe: 'response' });
    }

    update(groceryItem: IGroceryItem): Observable<EntityResponseType> {
        return this.http.put<IGroceryItem>(this.resourceUrl, groceryItem, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IGroceryItem>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IGroceryItem[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}

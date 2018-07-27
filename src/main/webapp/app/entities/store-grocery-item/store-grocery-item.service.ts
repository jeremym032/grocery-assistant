import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IStoreGroceryItem } from 'app/shared/model/store-grocery-item.model';

type EntityResponseType = HttpResponse<IStoreGroceryItem>;
type EntityArrayResponseType = HttpResponse<IStoreGroceryItem[]>;

@Injectable({ providedIn: 'root' })
export class StoreGroceryItemService {
    private resourceUrl = SERVER_API_URL + 'api/store-grocery-items';

    constructor(private http: HttpClient) {}

    create(storeGroceryItem: IStoreGroceryItem): Observable<EntityResponseType> {
        return this.http.post<IStoreGroceryItem>(this.resourceUrl, storeGroceryItem, { observe: 'response' });
    }

    update(storeGroceryItem: IStoreGroceryItem): Observable<EntityResponseType> {
        return this.http.put<IStoreGroceryItem>(this.resourceUrl, storeGroceryItem, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IStoreGroceryItem>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IStoreGroceryItem[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}

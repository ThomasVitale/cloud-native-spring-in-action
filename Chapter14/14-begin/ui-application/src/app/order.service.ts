import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Order, OrderRequest } from './types';

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json'
  })
};

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  constructor(private httpClient: HttpClient) { }

  getOrders(): Observable<Order[]> {
    return this.httpClient.get<Order[]>('/orders');
  }

  submitOrder(orderRequest: OrderRequest): Observable<any> {
    return this.httpClient.post<any>(`/orders`,
      orderRequest,
      httpOptions
    );
  }
}

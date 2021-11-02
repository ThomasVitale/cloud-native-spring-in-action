import { Component, OnInit } from '@angular/core';
import { map } from 'rxjs/operators';
import { Breakpoints, BreakpointObserver } from '@angular/cdk/layout';
import { Order } from '../types';
import { OrderService } from '../order.service';

@Component({
  selector: 'app-order-list',
  templateUrl: './order-list.component.html',
  styleUrls: ['./order-list.component.css']
})
export class OrderListComponent implements OnInit {

  orders: Order[] = [];

  colNumber = this.breakpointObserver.observe(Breakpoints.Handset).pipe(
    map(({ matches }) => matches ? 2 : 3));

  constructor(
    private breakpointObserver: BreakpointObserver,
    private orderService: OrderService
  ) {}

  ngOnInit(): void {
    this.orderService.getOrders().subscribe(orders => this.orders = orders);
  }

}

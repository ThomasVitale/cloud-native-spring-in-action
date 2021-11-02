import { Component, OnInit } from '@angular/core';
import { map } from 'rxjs/operators';
import { Breakpoints, BreakpointObserver } from '@angular/cdk/layout';
import { Book } from '../types';
import { BookService } from '../book.service';
import { Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { OrderDialogComponent } from '../order-dialog/order-dialog.component';

@Component({
  selector: 'app-book-list',
  templateUrl: './book-list.component.html',
  styleUrls: ['./book-list.component.css']
})
export class BookListComponent implements OnInit {

  books: Book[] = [];

  colNumber = this.breakpointObserver.observe(Breakpoints.Handset).pipe(
    map(({ matches }) => matches ? 2 : 3));

  constructor(
    public dialog: MatDialog,
    private bookService: BookService,
    private breakpointObserver: BreakpointObserver,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.bookService.getBooks().subscribe(books => this.books = books);
  }

  removeBook(isbn: string): void {
    this.bookService.deleteBook(isbn)
      .subscribe(() => {
        this.router.navigateByUrl('/browse-books');
      });
  }

  createOrder(book: Book): void {
    const dialogRef = this.dialog.open(OrderDialogComponent, {
      width: '250px',
      data: book
    });

    dialogRef.afterClosed().subscribe(() => {
      this.router.navigateByUrl('/my-orders');
    });
  }

}

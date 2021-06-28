import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { BookService } from '../book.service';
import { Book } from '../types';

@Component({
  selector: 'app-edit-book',
  templateUrl: './edit-book.component.html',
  styleUrls: ['./edit-book.component.css']
})
export class EditBookComponent implements OnInit {

  book!: Book;

  constructor(private route: ActivatedRoute, private bookService: BookService, private router: Router) { }

  ngOnInit(): void {
    const isbn = this.route.snapshot.paramMap.get('isbn') as string;
    this.bookService.getBookByIsbn(isbn)
      .subscribe(book => this.book = book);
  }

  onSubmit(book: Book): void {
    this.bookService.editBook(book)
      .subscribe(() => {
        this.router.navigateByUrl('/browse-books');
      });
  }

}

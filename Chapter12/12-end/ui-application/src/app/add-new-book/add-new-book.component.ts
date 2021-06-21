import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { BookService } from '../book.service';
import { Book } from '../types';

@Component({
  selector: 'app-add-new-book',
  templateUrl: './add-new-book.component.html',
  styleUrls: ['./add-new-book.component.css']
})
export class AddNewBookComponent implements OnInit {

  constructor(private bookService: BookService, private router: Router) { }

  ngOnInit(): void {
  }

  onSubmit(book: Book): void {
    this.bookService.addBook(book)
      .subscribe(() => {
        this.router.navigateByUrl('/browse-books');
      });
  }

}

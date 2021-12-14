import { EventEmitter } from '@angular/core';
import { Output } from '@angular/core';
import { Input } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { Book } from '../types';

@Component({
  selector: 'app-book-form',
  templateUrl: './book-form.component.html',
  styleUrls: ['./book-form.component.css']
})
export class BookFormComponent implements OnInit {

  @Input() buttonText = 'Submit';
  @Input() titleText = 'Book Information';
  @Input() currentBook: Book | undefined;
  @Output() onSubmit = new EventEmitter<Book>();

  book: Book = {
    id: undefined,
    isbn: '',
    title: '',
    author: '',
    price: 0,
    publisher: ''
  };

  constructor() { }

  ngOnInit(): void {
    if (this.currentBook) {
      this.book = this.currentBook;
    }
  }

  onButtonClicked(): void {
    this.onSubmit.emit(this.book);
  }

}

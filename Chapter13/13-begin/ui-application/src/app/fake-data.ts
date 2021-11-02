import { Book, Order, OrderStatus } from "./types";

export const fakeBooks: Book[] = [{
    id: 1,
    isbn: '1234567890',
    title: 'Harry Potter',
    author: 'J.K. Rowling',
    publishingYear: 1997,
    price: 19.90,
    publisher: 'Bloomsbury'
}, {
    id: 2,
    isbn: '1234567891',
    title: 'The Lord of the Rings',
    author: 'J.R.R. Tolkien',
    publishingYear: 1997,
    price: 19.90,
    publisher: 'Tolkien Society'
}, {
    id: 3,
    isbn: '1234567892',
    title: 'His Dark Materials',
    author: 'Philip Pullman',
    publishingYear: 1997,
    price: 19.90,
    publisher: 'Northern'
}];

export const fakeOrders: Order[] = [{
    id: 42,
    bookIsbn: '1234567890',
    bookName: 'Harry Potter - J.K. Rowling',
    bookPrice: 19.90,
    quantity: 3,
    status: OrderStatus.ACCEPTED
}, {
    id: 43,
    bookIsbn: '1234567891',
    bookName: 'The Lord of the Rings - J.R.R. Tolkien',
    bookPrice: 19.90,
    quantity: 2,
    status: OrderStatus.DISPATCHED
}, {
    id: 44,
    bookIsbn: '1234567892',
    bookName: 'His Dark Materials - Philip Pullman',
    bookPrice: 19.90,
    quantity: 1,
    status: OrderStatus.REJECTED
}];

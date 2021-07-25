export interface Book {
    id: number | undefined,
    isbn: string,
    title: string,
    author: string,
    publishingYear: number | undefined,
    price: number,
    publisher: string | undefined
}

export interface Order {
    id: number,
    bookIsbn: string,
    bookName: string,
    bookPrice: number,
    quantity: number,
    status: OrderStatus
}

export enum OrderStatus {
    ACCEPTED = 'Accepted',
	  REJECTED = 'Rejected',
	  DISPATCHED = 'Dispatched'
}

export interface OrderRequest {
    isbn: string,
    quantity: number
}

export interface User {
    username: string,
    firstName: string,
    lastName: string,
    roles: string[]
}

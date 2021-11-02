import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AddNewBookComponent } from './add-new-book/add-new-book.component';
import { BookListComponent } from './book-list/book-list.component';
import { EditBookComponent } from './edit-book/edit-book.component';
import { OrderListComponent } from './order-list/order-list.component';

const routes: Routes = [
  { path: 'browse-books', component: BookListComponent, pathMatch: 'full' },
  { path: 'add-book', component: AddNewBookComponent },
  { path: 'edit-book/:isbn', component: EditBookComponent },
  { path: 'my-orders', component: OrderListComponent, pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true})],
  exports: [RouterModule]
})
export class AppRoutingModule { }

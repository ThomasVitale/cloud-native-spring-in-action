import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AddNewBookComponent } from './add-new-book/add-new-book.component';
import { BookFormComponent } from './book-form/book-form.component';
import { BookListComponent } from './book-list/book-list.component';
import { EditBookComponent } from './edit-book/edit-book.component';
import { HttpInterceptorImpl } from './shared/http.interceptor';
import { OrderDialogComponent } from './order-dialog/order-dialog.component';
import { OrderListComponent } from './order-list/order-list.component';

@NgModule({
  declarations: [
    AppComponent,
    AddNewBookComponent,
    BookFormComponent,
    BookListComponent,
    EditBookComponent,
    OrderDialogComponent,
    OrderListComponent
  ],
  imports: [
    AppRoutingModule,
    BrowserModule,
    HttpClientModule,
  ],
  providers: [{provide: HTTP_INTERCEPTORS, useClass: HttpInterceptorImpl, multi: true}],
  bootstrap: [AppComponent]
})
export class AppModule { }

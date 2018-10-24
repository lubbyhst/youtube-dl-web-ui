import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import {HttpClientModule} from '@angular/common/http';
import { FormsModule } from '@angular/forms';


import { AppComponent } from './app.component';
import { AddEntryFormComponent } from './forms/add-entry-form/add-entry-form.component';
import { DownloadQueueFormComponent } from './forms/download-queue-form/download-queue-form.component';


@NgModule({
  declarations: [
    AppComponent,
    AddEntryFormComponent,
    DownloadQueueFormComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }

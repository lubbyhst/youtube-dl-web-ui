import { Component, OnInit } from '@angular/core';
import {HttpClient} from "@angular/common/http";

import {MatProgressBarModule} from '@angular/material/progress-bar';

@Component({
  selector: 'app-download-queue-form',
  templateUrl: './download-queue-form.component.html',
  styleUrls: ['./download-queue-form.component.css']
})
export class DownloadQueueFormComponent implements OnInit {

  entries;
  interval;

  constructor(
    private http: HttpClient,
  ){}

  ngOnInit() {
    this.refreshEntries();
    this.interval = setInterval(() => {
      this.refreshEntries();
    }, 200);
  }

  ngOnDestroy() {
    if (this.entries){
      clearInterval(this.entries);
    }
  }

  refreshEntries(){
    this.http.get('http://localhost:8888/getAllEntries').subscribe(data => {
      this.entries = data;
    });
  }

}

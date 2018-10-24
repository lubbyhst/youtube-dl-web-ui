import { Component, OnInit } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Entry} from "../../model/entry";

@Component({
  selector: 'app-add-entry-form',
  templateUrl: './add-entry-form.component.html',
  styleUrls: ['./add-entry-form.component.css']
})
export class AddEntryFormComponent implements OnInit {

  entry = this.newEntry();
  inputValue;

  constructor(
    private http: HttpClient,
  ){}

  ngOnInit(): void {
  }

  onSubmit(): void{
    this.entry.url = this.inputValue;
    this.http.put('http://localhost:8888/addEntry', this.entry).subscribe();
    this.entry = this.newEntry();
  }

  newEntry(): Entry {
    return new Entry(null,null, 0, false);
  }

}

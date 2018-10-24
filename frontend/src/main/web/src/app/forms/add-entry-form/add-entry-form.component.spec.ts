import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddEntryFormComponent } from './add-entry-form.component';

describe('AddEntryFormComponent', () => {
  let component: AddEntryFormComponent;
  let fixture: ComponentFixture<AddEntryFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddEntryFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddEntryFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

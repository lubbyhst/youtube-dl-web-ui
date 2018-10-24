import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DownloadQueueFormComponent } from './download-queue-form.component';

describe('DownloadQueueFormComponent', () => {
  let component: DownloadQueueFormComponent;
  let fixture: ComponentFixture<DownloadQueueFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DownloadQueueFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DownloadQueueFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

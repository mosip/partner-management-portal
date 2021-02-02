import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SubListViewComponent } from './sub-list-view.component';

describe('ListViewComponent', () => {
  let component: SubListViewComponent;
  let fixture: ComponentFixture<SubListViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SubListViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SubListViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

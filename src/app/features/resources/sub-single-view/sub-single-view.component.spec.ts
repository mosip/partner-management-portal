import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SubSingleViewComponent } from './sub-single-view.component';

describe('SubSingleViewComponent', () => {
  let component: SubSingleViewComponent;
  let fixture: ComponentFixture<SubSingleViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SubSingleViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SubSingleViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

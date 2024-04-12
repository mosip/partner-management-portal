import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MasterDataCommonBodyComponent } from './master-data-common-body.component';

describe('MasterDataCommonBodyComponent', () => {
  let component: MasterDataCommonBodyComponent;
  let fixture: ComponentFixture<MasterDataCommonBodyComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MasterDataCommonBodyComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MasterDataCommonBodyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

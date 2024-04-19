import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MasterDataCommonViewComponent } from './master-data-common-view.component';

describe('MasterDataCommonViewComponent', () => {
  let component: MasterDataCommonViewComponent;
  let fixture: ComponentFixture<MasterDataCommonViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MasterDataCommonViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MasterDataCommonViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

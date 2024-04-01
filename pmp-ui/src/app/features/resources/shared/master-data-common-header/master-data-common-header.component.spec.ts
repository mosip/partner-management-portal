import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MasterDataCommonHeaderComponent } from './master-data-common-header.component';

describe('MasterDataCommonHeaderComponent', () => {
  let component: MasterDataCommonHeaderComponent;
  let fixture: ComponentFixture<MasterDataCommonHeaderComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MasterDataCommonHeaderComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MasterDataCommonHeaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

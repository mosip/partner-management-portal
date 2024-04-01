import { Component, OnInit, Input } from '@angular/core';
import { HeaderModel } from 'src/app/core/models/header.model';

@Component({
  selector: 'app-master-data-common-header',
  templateUrl: './master-data-common-header.component.html'
})
export class MasterDataCommonHeaderComponent implements OnInit {

  @Input() masterDataName: string;
  @Input() headerData: HeaderModel;

  constructor() { }

  ngOnInit() {
  }

}

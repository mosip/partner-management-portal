import { Component, Input } from '@angular/core';
import { HeaderModel } from 'src/app/core/models/header.model';

@Component({
  selector: 'app-master-data-common-header',
  templateUrl: './master-data-common-header.component.html'
})
export class MasterDataCommonHeaderComponent{

  @Input() masterDataName: string;
  @Input() headerData: HeaderModel;

}

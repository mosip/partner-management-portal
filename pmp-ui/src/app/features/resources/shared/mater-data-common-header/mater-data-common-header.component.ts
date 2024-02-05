import { Component, Input } from '@angular/core';
import { HeaderModel } from 'src/app/core/models/header.model';

@Component({
  selector: 'app-mater-data-common-header',
  templateUrl: './mater-data-common-header.component.html'
})
export class MaterDataCommonHeaderComponent{

  @Input() masterDataName: string;
  @Input() headerData: HeaderModel;

}

import { Component, ViewEncapsulation, Input } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { AppConfigService } from 'src/app/app-config.service';
import { HeaderModel } from 'src/app/core/models/header.model';

@Component({
  selector: 'app-master-data-common-view',
  templateUrl: './master-data-common-view.component.html',
  styleUrls: ['./master-data-common-view.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class MasterDataCommonViewComponent{

  @Input() masterDataName: string;
  @Input() headerData: HeaderModel;
  @Input() primaryData: any;
  @Input() secondaryData: any;
  @Input() fields: any;
  @Input() primaryLang: string;
  @Input() secondaryLang: string;
  @Input() masterdataType: any;

  constructor(public translateService: TranslateService, public appConfigService: AppConfigService) {
    // tslint:disable-next-line:no-string-literal
    this.primaryLang = appConfigService.getConfig()['primaryLangCode'];
    // tslint:disable-next-line:no-string-literal
    this.secondaryLang = appConfigService.getConfig()['secondaryLangCode'];
    translateService.use(this.primaryLang);
  }

}

import { Component, OnInit, ViewChild, ElementRef, ViewEncapsulation, AfterViewInit } from '@angular/core';

import { SideMenuService } from '../services/side-menu.service';
import { TranslateService } from '@ngx-translate/core';
import { NavItem } from '../../core/nav-item';
import * as cloneObject from 'lodash/cloneDeep';
import * as appConstants from '../../app.constants';
import { AppConfigService } from 'src/app/app-config.service';
import { RolesService } from '../services/roles.service';
import { HeaderService } from '../services/header.service';
import { DataStorageService } from '../services/data-storage.service';

@Component({
  selector: 'app-parent',
  templateUrl: './parent.component.html',
  styleUrls: ['./parent.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ParentComponent implements OnInit, AfterViewInit {

  screenWidth: number;

  @ViewChild('appDrawer', { static: true }) appDrawer: ElementRef;

  languageData: any;
  navItems: NavItem[];
  primaryLang: string;
  secondaryLang: string;
  sitealignment = 'ltr';

  constructor(public headerService: HeaderService, 
              public sideMenuService: SideMenuService,
              public translateService: TranslateService,
              public appConfigService: AppConfigService,
              public dataService: DataStorageService,
              public rolesService: RolesService) {
    this.screenWidth = window.innerWidth;
    window.onresize = () => {
      return this.screenWidth = window.innerWidth;
    };
  }

  ngOnInit() {
    this.primaryLang = this.headerService.getlanguageCode();
    this.secondaryLang = this.appConfigService.getConfig()['secondaryLangCode'];
    if(this.primaryLang === "ara"){
      this.sitealignment = 'rtl';
    }
    this.translateService.use(this.primaryLang);
    this.navItems = cloneObject(appConstants.navItems);    
    if (this.headerService.getRoles()) {
      this.dataService.getRoles().subscribe(({ response }) => {
        console.log("response>>>"+response.roles);                   
      });
    }           
  }

  ngAfterViewInit() {
    this.sideMenuService.appDrawer = this.appDrawer;
  }
}

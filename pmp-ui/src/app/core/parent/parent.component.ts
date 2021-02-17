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

  constructor(private headerService: HeaderService, 
              private sideMenuService: SideMenuService,
              private translateService: TranslateService,
              private appConfigService: AppConfigService,
              private dataService: DataStorageService,
              public rolesService: RolesService) {
    this.screenWidth = window.innerWidth;
    window.onresize = () => {
      return this.screenWidth = window.innerWidth;
    };
  }

  ngOnInit() {
    this.primaryLang = this.appConfigService.getConfig()['primaryLangCode'];
    this.secondaryLang = this.appConfigService.getConfig()['secondaryLangCode'];
    this.translateService.use(this.primaryLang);
    this.navItems = cloneObject(appConstants.navItems);
    let self = this;
    if (this.headerService.getRoles()) {
      console.log("this.headerService.getlanguageCode>>>"+this.headerService.getlanguageCode());
      this.headerService.getRoles().split(",").forEach(function (value) {
        if(value.trim() == "PARTNER ADMIN"){
          /*self.dataService.getPartnerType(value).subscribe(
            response => {
              if (!response.errors || response.errors.length === 0) {
                response.response.data.forEach(function (partnerDetail) {
                  if(partnerDetail.partnerDescription != "Partner Admin"){
                    let child = {
                      displayName: partnerDetail.partnerDescription,
                      icon: null,
                      route: '/pmp/resources/'+partnerDetail.code+'/view',
                      auditEventId: 'ADM-005',
                      roles: ['PARTNER_ADMIN']
                    }
                    self.navItems[2].children.push(child);
                  }
                });
              }
            }
          );*/
          let newMenu = {
            displayName: 'Device Details',
            icon: './assets/images/home.svg',
            route: '/pmp/resources/devicedetails/view',
            children: null,
            auditEventId: 'ADM-002',
            roles: ['PARTNER_ADMIN']
          }
          self.navItems.push(newMenu);
          newMenu = {
            displayName: 'FTM Details',
            icon: './assets/images/home.svg',
            route: '/pmp/resources/ftmdetails/view',
            children: null,
            auditEventId: 'ADM-002',
            roles: ['PARTNER_ADMIN']
          }
          self.navItems.push(newMenu);
          newMenu = {
            displayName: 'Partner Policy Mapping',
            icon: './assets/images/home.svg',
            route: '/pmp/resources/policymapping/view',
            children: null,
            auditEventId: 'ADM-002',
            roles: ['PARTNER_ADMIN']
          }
          self.navItems.push(newMenu);
          /*newMenu = {
            displayName: 'SBI Details',
            icon: './assets/images/home.svg',
            route: '/pmp/resources/sbidetails/view',
            children: null,
            auditEventId: 'ADM-002',
            roles: ['PARTNER_ADMIN']
          }
          self.navItems.push(newMenu);*/
        }else if(value.trim() == "DEVICE PROVIDER"){
          let newMenu = {
            displayName: 'Device Details',
            icon: './assets/images/home.svg',
            route: '/pmp/resources/devicedetails/view',
            children: null,
            auditEventId: 'ADM-002',
            roles: ['DEVICE_PROVIDER']
          }
          self.navItems.push(newMenu);
          /*newMenu = {
            displayName: 'SBI Details',
            icon: './assets/images/home.svg',
            route: '/pmp/resources/sbidetails/view',
            children: null,
            auditEventId: 'ADM-002',
            roles: ['PARTNER_ADMIN']
          }
          self.navItems.push(newMenu);*/
        }
      });  
    }
  }

  ngAfterViewInit() {
    this.sideMenuService.appDrawer = this.appDrawer;
  }
}

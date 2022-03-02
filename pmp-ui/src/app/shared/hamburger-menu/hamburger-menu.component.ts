import { HeaderService } from 'src/app/core/services/header.service';
import { LogoutService } from './../../core/services/logout.service';
import { Component, OnInit, Input, ViewEncapsulation } from '@angular/core';
import { AuditService } from 'src/app/core/services/audit.service';
import { TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-hamburger-menu',
  templateUrl: './hamburger-menu.component.html',
  styleUrls: ['./hamburger-menu.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class HamburgerComponent implements OnInit {
  @Input() data: any;
  roleName: string;
  roleNameSubstr: string;
  userName: string;

  dataList: any[];
  buttonName:string="";

  constructor(
    public headerService: HeaderService,
    public logoutService: LogoutService,
    public auditService: AuditService,
    public translateService: TranslateService
  ) {}

  ngOnInit() {
    if (this.data !== null && this.data.menuList) {
      this.dataList = this.data.menuList;
    }
    if (this.headerService.getUsername()) {
      this.userName = this.headerService.getUsername();
    }
    if (this.headerService.getRoles()) {
      this.translateService
      .getTranslation(this.headerService.getlanguageCode())
      .subscribe(response => {
        console.log("response.dashboard.Logout>>>"+response);
        this.buttonName = response.dashboard.Logout;
      });
      this.roleNameSubstr = this.headerService.getRoles();
      if (this.roleNameSubstr.indexOf(',') !== -1) {
        const roleNameSplit = this.headerService.getRoles().indexOf(',');
        this.roleName = this.headerService
          .getRoles()
          .substring(0, roleNameSplit);
      } else {
        this.roleName = this.headerService.getRoles();
      }
    }
  }

  onItem() {
    this.auditService.audit(1, 'ADM-001', 'Logout');
    this.logoutService.logout();
  }
}

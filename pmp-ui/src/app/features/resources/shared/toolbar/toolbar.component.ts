import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { MatPaginatorIntl, MatDialog } from '@angular/material';
import { DialogComponent } from '../dialog/dialog.component';
import { Router } from '@angular/router';
import { AppConfigService } from 'src/app/app-config.service';
import { AuditService } from 'src/app/core/services/audit.service';

@Component({
  selector: 'app-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrls: ['./toolbar.component.scss']
})
export class ToolbarComponent extends MatPaginatorIntl implements OnInit {
  @Input() buttonList: any;
  @Input() paginationOptions: any;
  @Input() filtersAppliedFlag: boolean;
  @Output() pageEvent = new EventEmitter();
  lang: string;

  pageSize: number;

  constructor(
    public dialog: MatDialog,
    public router: Router,
    public appConfig: AppConfigService,
    public auditService: AuditService
  ) {
    super();
    this.itemsPerPageLabel = 'Show rows';
  }

  ngOnInit() {
    this.lang = this.appConfig.getConfig().primaryLangCode;
    this.pageSize = Number(this.paginationOptions.pageSize);
  }

  actionEvent(buttonAction) {
    if (buttonAction.actionListType === 'action') {
      this.openFilterDialog(buttonAction.actionURL);
    }
    if (buttonAction.actionListType === 'redirect') {
      console.log(buttonAction.actionListType);
      this.auditService.audit(9, 'ADM-083', {
        buttonName: buttonAction.buttonName.eng,
        masterdataName: this.router.url.split('/')[
          this.router.url.split('/').length - 2
        ]
      });
      this.router.navigateByUrl(buttonAction.redirectURL);
    }
    if (buttonAction.actionListType === 'replaceIdRedirect') {
      let url = buttonAction.redirectURL.replace('$id', this.router.url.split('/')[6]);
      /*this.auditService.audit(9, 'ADM-083', {
        buttonName: buttonAction.buttonName.eng,
        masterdataName: this.router.url.split('/')[
          this.router.url.split('/').length - 2
        ]
      });*/
      this.router.navigateByUrl(url);
    }
  }
  openFilterDialog(action): void {
    this.dialog
      .open(DialogComponent, {
        data: action,
        width: '700px',
        autoFocus: false,
        disableClose: true,
        restoreFocus: false
      })
      .afterClosed()
      .subscribe(result => {
        console.log('dislog is closed');
      });
  }

  onPaginateChange(event: Event) {
    console.log(event);
    if (this.pageSize !== event['pageSize']) {
      this.pageSize = event['pageSize'];
      this.auditService.audit(14, 'ADM-094', {
        noOfRows: this.pageSize,
        masterdataName: this.router.url.split('/')[
          this.router.url.split('/').length - 2
        ]
      });
    }
    if (event['previousPageIndex'] !== event['pageIndex']) {
      this.auditService.audit(15, 'ADM-095', {
        pageNo: Number(event['pageIndex'] + 1),
        masterdataName: this.router.url.split('/')[
          this.router.url.split('/').length - 2
        ]
      });
    }
    this.pageEvent.emit(event);
  }

  export() {
    this.auditService.audit(9, 'ADM-081', {
      buttonName: 'export',
      masterdataName: this.router.url.split('/')[
        this.router.url.split('/').length - 2
      ]
    });
  }
}

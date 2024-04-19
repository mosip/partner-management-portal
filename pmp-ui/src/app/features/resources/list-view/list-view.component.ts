import { Component, OnDestroy } from '@angular/core';
import { Router, ActivatedRoute, NavigationEnd } from '@angular/router';
import * as appConstants from 'src/app/app.constants';
import { DataStorageService } from 'src/app/core/services/data-storage.service';
import { RequestModel } from 'src/app/core/models/request.model';
import { CenterRequest } from 'src/app/core/models/centerRequest.model';
import { PaginationModel } from 'src/app/core/models/pagination.model';
import { SortModel } from 'src/app/core/models/sort.model';
import { AppConfigService } from 'src/app/app-config.service';
import Utils from 'src/app/app.utils';
import { MatDialog, MatPaginatorIntl } from '@angular/material';
/*import { DialogComponent } from 'src/app/shared/dialog/dialog.component';*/
import { TranslateService } from '@ngx-translate/core';
import { AuditService } from 'src/app/core/services/audit.service';
import { HeaderService } from 'src/app/core/services/header.service';

@Component({
  selector: 'app-list-view',
  templateUrl: './list-view.component.html',
  styleUrls: ['./list-view.component.scss']
})
export class ListViewComponent implements OnDestroy {
  headerName: string;
  displayedColumns = [];
  actionButtons = [];
  actionEllipsis = [];
  paginatorOptions: any;
  sortFilter = [];
  pagination = new PaginationModel();
  centerRequest = {} as CenterRequest;
  requestModel: RequestModel;
  masterData = [];
  mapping: any;
  errorMessages: any;
  subscribed: any;
  noData = false;
  filtersApplied = false;
  masterDataType: string;
  auditEventId: string[];
  labels:any;

  constructor(
    public router: Router,
    public dataStorageService: DataStorageService,
    public appService: AppConfigService,
    public activatedRoute: ActivatedRoute,
    public dialog: MatDialog,
    public translateService: TranslateService,
    public auditService: AuditService, 
    public headerService: HeaderService,
    private paginator: MatPaginatorIntl
  ) {
    translateService
      .getTranslation(this.headerService.getlanguageCode())
      .subscribe(response => {
        this.labels = response;
        this.errorMessages = response.errorPopup;
      });
    this.subscribed = router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        this.initializeComponent();
      }
    });
  }

  async initializeComponent() {
    await this.loadData();
    this.paginator.itemsPerPageLabel = this.labels['paginatorIntl'].itemsPerPageLabel;
    const originalGetRangeLabel = this.paginator.getRangeLabel;
      this.paginator.getRangeLabel = (page: number, size: number, len: number) => {
        return originalGetRangeLabel(page, size, len)
            .replace('of', this.labels['paginatorIntl'].of);
    }; 
    if (this.activatedRoute.snapshot.params.type !== this.masterDataType) {
      this.masterDataType = this.activatedRoute.snapshot.params.type;
      this.auditService.audit(3, this.auditEventId[0], this.masterDataType);
    }
    if (this.masterDataType.toLowerCase() === 'blacklisted-words') {
      await this.loadBlacklistedWords();
    } else {
      await this.getMasterDataTypeValues(
        this.headerService.getlanguageCode()
      );
    }
  }

  loadBlacklistedWords() {
    return new Promise(async (resolve, reject) => {
      const data = [];
      await this.getMasterDataTypeValues('all').then(response => {
        if (response['data']) {
          data.push(...response['data']);
          console.log(response);
        }
      });
      this.masterData = data;
      console.log(this.masterData);
      this.paginatorOptions.totalEntries = this.masterData.length;
      resolve(true);
    });
  }

  loadData() {
    return new Promise((resolve, reject) => {
      const routeParts = this.activatedRoute.snapshot.params.type;
      if(appConstants.masterdataMapping[`${routeParts}`]){
        this.mapping = appConstants.masterdataMapping[`${routeParts}`];
        this.headerName = appConstants.masterdataMapping[`${routeParts}`].headerName;        
      }else{
        this.mapping = { apiName: 'partnermanager/partners', specFileName: 'partner', name: 'Auth Partner', nameKey: 'titleName',
         idKey: 'id', headerName: `${routeParts}`};
        this.headerName = `${routeParts}`.replace(/_/g, " ");
      }
      this.dataStorageService
        .getSpecFileForMasterDataEntity(this.mapping.specFileName)
        .subscribe(response => {
          console.log(response);
          this.displayedColumns = response.columnsToDisplay.filter(
            values => values.showInListView === 'true'
          );
          console.log(this.displayedColumns.length);
          this.actionButtons = response.actionButtons.filter(
            value => value.showIn.toLowerCase() === 'ellipsis'
          );
          console.log(this.actionButtons);
          this.actionEllipsis = response.actionButtons.filter(
            value => value.showIn.toLowerCase() === 'button'
          );
          console.log(this.actionEllipsis);
          this.paginatorOptions = response.paginator;
          console.log(this.paginatorOptions);
          this.auditEventId = response.auditEventIds;
          resolve(true);
        });
    });
  }

  getSortColumn(event: SortModel) {
    console.log(event);
    this.sortFilter.forEach(element => {
      if (element.sortField === event.sortField) {
        const index = this.sortFilter.indexOf(element);
        this.sortFilter.splice(index, 1);
      }
    });
    if (event.sortType != null) {
      this.sortFilter.push(event);
    }
    console.log(this.sortFilter);
    const filters = Utils.convertFilter(
      this.activatedRoute.snapshot.queryParams,
      this.headerService.getlanguageCode()
    );
    filters.sort = this.sortFilter;
    const url = Utils.convertFilterToUrl(filters);
    this.router.navigateByUrl(
      `pmp/resources/${this.activatedRoute.snapshot.params.type}/view?${url}`
    );
  }

  pageEvent(event: any) {
    const filters = Utils.convertFilter(
      this.activatedRoute.snapshot.queryParams,
      this.headerService.getlanguageCode()
    );
    filters.pagination.pageFetch = event.pageSize;
    filters.pagination.pageStart = event.pageIndex;
    const url = Utils.convertFilterToUrl(filters);
    this.router.navigateByUrl(
      `pmp/resources/${this.activatedRoute.snapshot.params.type}/view?${url}`
    );
  }

  getMasterDataTypeValues(language: string) {
    let self = this;
    return new Promise((resolve, reject) => {
      this.masterData = [];
      this.noData = false;
      this.filtersApplied = false;
      const routeParts = this.activatedRoute.snapshot.params.type;
      const filters = Utils.convertFilter(
        this.activatedRoute.snapshot.queryParams,
        language
      );
      if (filters.filters.length > 0) {
        this.filtersApplied = true;
      }
      /*this.sortFilter = filters.sort;
      if(this.sortFilter.length == 0){
        if(routeParts != "policymapping"){
          this.sortFilter.push({"sortType":"desc","sortField":"isActive"});
        }else if(routeParts == "policymapping"){
          this.sortFilter.push({"sortType":"desc","sortField":"statusCode"});
        }     
      }*/
      this.requestModel = new RequestModel(null, null, filters);

      if(appConstants.masterdataMapping[`${routeParts}`]){
        this.mapping = appConstants.masterdataMapping[`${routeParts}`];
        this.headerName = appConstants.masterdataMapping[`${routeParts}`].headerName;
      }else{
        this.mapping = { apiName: 'partnermanager/partners', specFileName: 'partner', name: 'Auth Partner', nameKey: 'titleName',
         idKey: 'id', headerName: `${routeParts}`};
        this.headerName = "Partner";
        this.requestModel.request["partnerType"] = "all";
      }

      let appConstantsValue = appConstants.navItems;
      appConstantsValue.forEach(element => {
        if(element.children){
          element.children.forEach(childelement => {
            if (childelement.route.includes(routeParts)) {
              self.headerName = self.labels[childelement.displayName.split('.')[0]][childelement.displayName.split('.')[1]][childelement.displayName.split('.')[2]];
            }
          });
        }else{
          if (element.route.includes(routeParts)) {
            self.headerName = self.labels[element.displayName.split('.')[0]][element.displayName.split('.')[1]][element.displayName.split('.')[2]];
          }
        }
      });

      this.dataStorageService
        .getDataByTypeAndId(this.mapping, this.requestModel)
        .subscribe(({ response }) => {
          if (response != null) {
            this.paginatorOptions.totalEntries = response.totalRecord;
            this.paginatorOptions.pageIndex = filters.pagination.pageStart;
            this.paginatorOptions.pageSize = filters.pagination.pageFetch;
            if (response.data) {
              this.masterData = response.data ? [...response.data] : [];
            } else {
              this.noData = true;
            }
          } else {
            this.noData = true;
            /*this.dialog
              .open(DialogComponent, {
                data: {
                  case: 'MESSAGE',
                  title: this.errorMessages.technicalError.title,
                  message: this.errorMessages.technicalError.message,
                  btnTxt: this.errorMessages.technicalError.btnTxt
                },
                width: '700px'
              })
              .afterClosed()
              .subscribe(result => {
                console.log('dialog is closed from view component');
              });*/
          }
          resolve(response);
        });
    });
  }

  changePage() {
    this.router.navigateByUrl('admin/masterdata/home');
  }

  ngOnDestroy() {
    this.subscribed.unsubscribe();
  }
}

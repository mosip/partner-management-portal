import { Component, OnInit } from '@angular/core';
import { AuditService } from '../services/audit.service';
import { DataStorageService } from '../services/data-storage.service';
import { HeaderService } from '../services/header.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
	partnerIdDetail: any;
  	constructor(private dataService: DataStorageService, private headerService: HeaderService, private auditService: AuditService) { }

 	ngOnInit() {
	  	if (this.headerService.getRoles()) {	      
			let partnerId = this.headerService.getUsername();
			this.dataService.getPartnerIdDetail(partnerId).subscribe(response => {
				this.partnerIdDetail = response.response;
			});
	    }
  	}
}

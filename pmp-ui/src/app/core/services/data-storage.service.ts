import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as appConstants from '../../app.constants';
import { RequestModel } from '../models/request.model';
import { AppConfigService } from 'src/app/app-config.service';
import { HeaderService } from '../services/header.service';

@Injectable()
export class DataStorageService {
  constructor(private headerService: HeaderService, private http: HttpClient, private appService: AppConfigService) {}

  private BASE_URL = this.appService.getConfig().baseUrl;

  getCenterSpecificLabelsAndActions(): Observable<any> {
    return this.http.get('./assets/entity-spec/center.json');
  }

  getMispSpecificLabelsAndActions(): Observable<any> {
    return this.http.get('./assets/entity-spec/misp.json');
  }

  getPolicySpecificLabelsAndActions(): Observable<any> {
    return this.http.get('./assets/entity-spec/policy.json');
  }

  getImmediateChildren(
    locationCode: string,
    langCode: string
  ): Observable<any> {
    return this.http.get(
      this.BASE_URL +
        appConstants.MASTERDATA_BASE_URL +
        'locations/immediatechildren/' +
        locationCode +
        '/' +
        langCode
    );
  }

  updatePartner(data: RequestModel, pid: string): Observable<any> {
    return this.http.put(
      "/partners" + "/" + pid,
      data
    );
  }

  getPartnerManagerData(request: RequestModel): Observable<any> {
    return this.http.get("/pmpartners/getManager");
  }

  public submitRequest(data: RequestModel, pid: string) : Observable<any>{
    return this.http.post(
      "/partners/submit" + "/" + pid + "/partnerAPIKeyRequests",
      data
    );
  }
  
  public submitAuthPolicyReq(data: RequestModel, pid: string) : Observable<any>{
    return this.http.post(
      "/policies" + "/" + pid + "/authPolicies",
      data
    );
  }

  public activatePartnerStatus(data: RequestModel, pid: string) : Observable<any>{
    return this.http.put(
      "/pmpartners/updateStatus" + "/" + pid,
      data
    );
  }

  public approvePartnerRequest(data: RequestModel, reqid: string) : Observable<any>{
    return this.http.put(
      "/pmpartners/PartnerAPIKeyRequests" + "/" + reqid,
      data
    );
  }

  public deactivateAPIKey(data: RequestModel, pid: string , apikey: string) : Observable<any>{
    console.log("Request is: "+ data);
    return this.http.put(
      "/pmpartners" + "/" + pid + "/" + apikey,
      data
    );
  }

  getStubbedDataForDropdowns(): Observable<any> {
    return this.http.get('./assets/data/centers-stub-data.json');
  }


  createPartner(data: RequestModel): Observable<any> {
    return this.http.post("/partnerReg", data);
  }

  getPolicyID(PolicyName:string): Observable<any> {
    return this.http.get("/pmpartners/policyname" + "/" + PolicyName);
  }


  updatePaPolicy(request: RequestModel , partnerID:string , partnerAPIKey:string): Observable<any> {
    return this.http.post(
      "/pmpartners" + "/" + partnerID + "/" + partnerAPIKey , request
    );
  }

  createCenter(data: RequestModel): Observable<any> {
    return this.http.post(
      this.BASE_URL + appConstants.MASTERDATA_BASE_URL + 'registrationcenters',
      data
    );
  }

  data = [];
    popupMessages: any;

  createMisp(data: RequestModel): Observable<any> {    
    return this.http.post(
      "https://dev.mosip.net/partnermanagement/v1/misps/misps",data
    );
  }
  createPolicy(data: RequestModel): Observable<any> {
    
    return this.http.post(
      "/policies",data
    );
    console.log(this.popupMessages);
  }

  updateMisp(data: RequestModel): Observable<any> {
    return this.http.put("/misps/" +data.request.id ,data
    );
  }

  ApproveorRejectPolicyFunction(mapping: any, data: RequestModel): Observable<any> {
    return this.http.patch(
      this.BASE_URL+ 'v1/partnermanager/partners/apikey/'+data.request.apikeyRequestId,
      data
    );
  }

  updatePolicyStatus(mapping: any, data: RequestModel): Observable<any> {
    let url = ""
    if(mapping.specFileName === "policy-group"){
      return this.http.put(
        this.BASE_URL+ 'v1/policymanager/policies/group/'+data.request.id,
        data
      );
    }else{
      if(data.request.status){
        data.request.status = "Active"
      }else{
        data.request.status = "De-active"
      }
      return this.http.patch(
        this.BASE_URL+ 'v1/policymanager/policies/'+data.request.id+'/group/'+data.request.policyGroupId,
        data
      );
    }    
  }

  publishPolicy(mapping: any, data: RequestModel): Observable<any> {    
    return this.http.post(
      this.BASE_URL+ 'v1/policymanager/policies/'+data.request.id+'/group/'+data.request.policyGroupId+'/publish',
      data
    );
  }

  updateDetails(mapping: any, data: RequestModel): Observable<any> {
    return this.http.patch(
      this.BASE_URL+ 'v1/'+ mapping.apiName,
      data
    );
  }
  updatePolicy(data: RequestModel): Observable<any> {
    return this.http.post("/policies/" +data.request.id ,data
    );
  }

  viewCertificate(data: any): Observable<any> {
    return this.http.get(this.BASE_URL+ 'v1/partnermanager/partners/' +  data.id +'/certificate');
  }

  approveMisp(data: RequestModel): Observable<any> {
    return this.http.post("/misps/" + data.request.id +
    '/status',data
    );
  }

  updateCenter(data: RequestModel): Observable<any> {
    return this.http.post(
      '/misps/'+data.request.id,
      data
    );
  }

  getDevicesData(request: RequestModel): Observable<any> {
    return this.http.post(this.BASE_URL + appConstants.URL.devices, request);
  }

  getMachinesData(request: RequestModel): Observable<any> {
    console.log(request);
    return this.http.post(this.BASE_URL + appConstants.URL.machines, request);
  }

  getMasterDataTypesList(): Observable<any> {
    return this.http.get('./assets/entity-spec/master-data-entity-spec.json');
  }

  getPartnerType(partnerName : string): Observable<any> {
    let json = {"id": "string","metadata": {},"request": {"filters": [],"pagination": {"pageFetch": 10,"pageStart": 0},"sort": []},  "requesttime": "",
    "version": "string"}
    return this.http.post(
      this.BASE_URL+ 'v1/partnermanager/partners/partnerType/search',
      json
    );
  }

  getPartnerIdDetail(partnerId: any): Observable<any> {
    return this.http.get(
      this.BASE_URL+ 'v1/partnermanager/partners/' + partnerId
    );
  }

  getDataByTypeAndId(mapping: any, data: RequestModel): Observable<any> {
    if(mapping.headerName == "Auth Policy"){
      data.request["policyType"] = "Auth";
    }
    else if(mapping.headerName == "Data Share Policy"){
      data.request["policyType"] = "DataShare";
    }
    else if(mapping.headerName == "Device Detail"){
      data.request["purpose"] = "REGISTRATION";
      if(this.headerService.getRoles().indexOf("DEVICE PROVIDER") != -1)
        data.request["deviceProviderId"] = this.headerService.getUsername();
      else        
        data.request["deviceProviderId"] = "all";
    }
    else if(mapping.headerName == "Secure Biometric Interface"){
      data.request["purpose"] = "REGISTRATION";
      data.request["deviceDetailId"] = "all";
    }
    else if(mapping.headerName == "FTM Detail"){
      data.request["purpose"] = "REGISTRATION";
    }      
    /*else if(mapping.headerName == "Auth Partner")
      data.request["partnerType"] = "Auth_Partner";
    else if(mapping.headerName == "Device Partner")
      data.request["partnerType"] = "Device_Provider";
    else if(mapping.headerName == "FTM Partner")
      data.request["partnerType"] = "FTM_Provider";
    else if(mapping.headerName == "MISP Partner")
      data.request["partnerType"] = "MISP_Partner";
    else if(mapping.headerName == "Device Detail")
      if(mapping.name == "Device Partner")
        data.request["purpose"] = "REGISTRATION";
      else if(mapping.name == "Auth Partner")
        data.request["purpose"] = "AUTH";
      data.request["deviceProviderId"] = mapping.id;*/
    
    return this.http.post(
      this.BASE_URL+ 'v1/' + mapping.apiName + '/search',
      data
    );
  }

  getSpecFileForMasterDataEntity(filename: string): Observable<any> {
    return this.http.get(`./assets/entity-spec/${filename}.json`);
  }

  getFiltersForListView(filename: string): Observable<any> {
    return this.http.get(`./assets/entity-spec/${filename}.json`);
  }

  getFiltersForAllMaterDataTypes(
    type: string,
    data: RequestModel
  ): Observable<any> {
    return this.http.post(
      this.BASE_URL + appConstants.MASTERDATA_BASE_URL + type + '/filtervalues',
      data
    );
  }

  getFiltersForAllDropDown(
    type: string,
    data: RequestModel
  ): Observable<any> {
    return this.http.post(
      this.BASE_URL+ 'v1/' + type + '/filtervalues',
      data
    );
  }

  createData(type: string, data: RequestModel): Observable<any> {
    let url = "";
    if(type == "policymanager/policies/group"){
      url = '/new';
    }
    return this.http.post(
      this.BASE_URL+ 'v1/' + type + url,
      data
    );
  }

  updateData(type: string, data: RequestModel): Observable<any> {    
    if(type === "policymanager/policies"){
      type = type+"/"+data.request.id;
    }
    return this.http.put(
      this.BASE_URL+ 'v1/' + type ,
      data
    );
  }

  getZoneData(langCode: string): Observable<any> {
    return this.http.get(
      this.BASE_URL +
        appConstants.MASTERDATA_BASE_URL +
        'zones/leafs/' +
        langCode
    );
  }

  getLoggedInUserZone(userId: string, langCode: string): Observable<any> {
    let params = new HttpParams();
    params = params.append('userID', userId);
    params = params.append('langCode', langCode);
    return this.http.get(
      this.BASE_URL + appConstants.MASTERDATA_BASE_URL + 'zones/zonename',
      { params }
    );
  }

  decommissionCenter(centerId: string) {
    return this.http.put(
      // this.BASE_URL +
      //   appConstants.MASTERDATA_BASE_URL +
      //   'registrationcenters/' +
        '/misps/' +
        centerId +
        '/status',
      {}
    );
  }

  uploadCertificate(data: any, url : any): Observable<any> {
    return this.http.post(
      this.BASE_URL  + 'v1/partnermanager/partners/certificate/'+url+'upload',
      data
    );
  }

  getPartnerInfo(url : any): Observable<any> {
    return this.http.get(
      this.BASE_URL  + 'v1/'+url
    );
  }

  requestAPIKey(url : any, data : any): Observable<any> {
    return this.http.patch(
      this.BASE_URL  + 'v1/'+url,
      data
    );
  }
}
import { of } from 'rxjs';
import { OriginDestinationService } from './origin-destination.service';
import { environment } from 'src/environments/environment';
describe('OriginDestinationService', () => {
  let service: OriginDestinationService;
  let httpMock: any ;
  const apiUrl= `http://localhost:9000/origin-destination`
  beforeEach(async () => {
    httpMock ={
      get: jest.fn(() => of()),
      post: jest.fn(() => of()),
      delete: jest.fn(() => of()),
      put: jest.fn(() => of())
    };
    service = new OriginDestinationService(httpMock);
  });

  it('should be created', () => {
    expect(service).toBeInstanceOf(OriginDestinationService);
  });

  it('should call get the airports', () => {
    environment.apiUrl = `http://localhost:9000/origin-destination`
    service.getAirports()
    expect(httpMock.get).toHaveBeenCalledWith(apiUrl+`/airports`);
  });

  it('should call get the fare details', () => {
    environment.apiUrl = `http://localhost:9000/origin-destination`
    service.getFare("ORI","DES").subscribe(()=>{})
   expect(httpMock.get).toHaveBeenCalledWith(apiUrl+`fare/ORI/DES`);
  });
});

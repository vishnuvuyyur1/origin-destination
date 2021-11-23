import { OriginDestinationService } from 'src/app/service';
import { PriceSearchComponent } from './price-search.component';
import { of, throwError } from 'rxjs';
describe('PriceSearchComponent', () => {
  let component: PriceSearchComponent;
  const airportsMock =[
    {
        "code": "BBA",
        "name": "location.airport.BBA.long",
        "description": "location.city.BBA.long - location.airport.BBA.long (BBA), Chile"
    },
    {
        "code": "YOW",
        "name": "Ottawa International",
        "description": "Ottawa - Ottawa International (YOW), Canada"
    },
    {
        "code": "JRO",
        "name": "Kilimanjaro International Airport",
        "description": "Kilimanjaro - Kilimanjaro International Airport (JRO), Tanzania"
    },
    {
        "code": "HNL",
        "name": "Honolulu Intl.",
        "description": "Honolulu - Honolulu Intl. (HNL), USA"
    }]

    const resultMock ={
      fare:{
        currency: 'EUR'
      }
    }
  const originDestinationService = ({
    getAirports: jest.fn(()=> of(airportsMock)),
    getFare: jest.fn(()=> of(resultMock)) 
  } as unknown) as OriginDestinationService;
  
  beforeEach(() => {
    component= new PriceSearchComponent(originDestinationService);
  });

  it('should create', () => {
    expect(component).toBeInstanceOf(PriceSearchComponent);
  });

  it('should get airports data on init', () => {
    component.ngOnInit()
    expect(component.airports.length).toBe(4);
  });

  it('should get fare data on submit', () => {
    component.getPrice()
    component.priceForm.patchValue({ origin: 'ALA' })
    component.priceForm.patchValue({ destination: 'BLB' })
    const spy = jest.spyOn(component, 'filterAirports')
    expect(component.airports.length).toBe(4);
    expect(component.loading).toBeFalsy();
    expect(spy).toHaveBeenCalled();
  });

  it('should get error when service call failed for getAirports', () => {
    originDestinationService.getAirports = jest.fn().mockReturnValue(throwError('Service Failed'))
    let errorEmitterSpy = jest.spyOn(originDestinationService, 'getAirports');
    component.ngOnInit();
    expect(errorEmitterSpy).toHaveBeenCalled();
    expect(component.serviceError).toBeTruthy();
  });
});

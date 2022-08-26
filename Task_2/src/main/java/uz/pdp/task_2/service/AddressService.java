package uz.pdp.task_2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.task_2.entity.Address;
import uz.pdp.task_2.payload.AddrossDto;
import uz.pdp.task_2.payload.Result;
import uz.pdp.task_2.repository.AddressRepository;

import java.util.Optional;


@Service
public class AddressService {

      @Autowired
      AddressRepository addressRepository;


      public Result addAddress(AddrossDto addressDto){

             Address address=new Address();
            address.setHouse(addressDto.getHouse());
            address.setStreet(addressDto.getStreet());
            address.setCity(addressDto.getCity());
            addressRepository.save(address);
            return new Result("Address added",true);
      }

      public Result editAddressById(AddrossDto addressDto, Integer id){

            Optional<Address> byId = addressRepository.findById(id);
            if (!byId.isPresent())
                  return new Result("Address not found",false);
              Address address = byId.get();
            address.setHouse(addressDto.getHouse());
            address.setStreet(addressDto.getStreet());
            address.setCity(addressDto.getCity());
            addressRepository.save(address);
            return new Result("Address edited",true);
      }

}


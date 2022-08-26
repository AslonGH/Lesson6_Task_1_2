package uz.pdp.cascade_types_annotatsiyalar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.cascade_types_annotatsiyalar.entity.EmpCustomer;
import uz.pdp.cascade_types_annotatsiyalar.entity.Payments;
import uz.pdp.cascade_types_annotatsiyalar.entity.enums.ServiceName;
import uz.pdp.cascade_types_annotatsiyalar.payload.PaymentsDto;
import uz.pdp.cascade_types_annotatsiyalar.repository.EmpCustomerRepository;
import uz.pdp.cascade_types_annotatsiyalar.repository.PaymentRepository;
import uz.pdp.cascade_types_annotatsiyalar.repository.TariffRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PaymentService {

    @Autowired
    TariffRepository tariffRepository;

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    EmpCustomerRepository customerRepository;

    // Payment Customerdan Company ga böladi
    public ApiResponse addPayment(PaymentsDto paymentsDto) {

        Optional<EmpCustomer> optionalCustomer = customerRepository.findById(paymentsDto.getCustomerId());
        if (!optionalCustomer.isPresent())
            return new ApiResponse("Customer not found",false);

          Payments payments = new Payments();
        switch (paymentsDto.getTypeOfPayment()){
            case 1: payments.setTypeOfPayment(ServiceName.CLICK.name());break;
            case 2: payments.setTypeOfPayment(ServiceName.TRANSFER.name());break;
            case 3: payments.setTypeOfPayment(ServiceName.CASH.name());break;
            case 4: payments.setTypeOfPayment(ServiceName.CARD.name());break;
            default:return new ApiResponse("CLICK=1;\n" +" TRANSFER=2;\n"+" CASH=3;\n" +" CARD=4;",false);
        }
        payments.setLocalDate(payments.getLocalDate());
        payments.setCustomer(optionalCustomer.get());
        paymentRepository.save(payments);
        return new ApiResponse("Payment saved",true);
    }

    public ApiResponse getPayment() {
        List<Payments> all = paymentRepository.findAll();
        return new ApiResponse("Payments: ",true,all);
    }

    public ApiResponse editPayment(UUID id, PaymentsDto paymentsDto) {

          Optional<Payments> optionalPayments = paymentRepository.findById(id);
        if (!optionalPayments.isPresent())
            return new ApiResponse("Payment not found",false);


          Optional<EmpCustomer> optionalCustomer = customerRepository.findById(paymentsDto.getCustomerId());
        if (!optionalCustomer.isPresent())
            return new ApiResponse("Customer not found",false);

        Payments payments = optionalPayments.get();
        switch (paymentsDto.getTypeOfPayment()){
            case 1: payments.setTypeOfPayment(ServiceName.CLICK.name());break;
            case 2: payments.setTypeOfPayment(ServiceName.TRANSFER.name());break;
            case 3: payments.setTypeOfPayment(ServiceName.CASH.name());break;
            case 4: payments.setTypeOfPayment(ServiceName.CARD.name());break;
            default:return new ApiResponse("CLICK=1;\n" +" TRANSFER=2;\n"+" CASH=3;\n" +" CARD=4;",false);
        }
        payments.setLocalDate(payments.getLocalDate());
        payments.setCustomer(optionalCustomer.get());
        paymentRepository.save(payments);
        return new ApiResponse("Payment saved",true);
    }

}

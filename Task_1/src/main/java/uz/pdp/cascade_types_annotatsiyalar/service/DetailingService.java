package uz.pdp.cascade_types_annotatsiyalar.service;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.pdp.cascade_types_annotatsiyalar.entity.Detailing;
import uz.pdp.cascade_types_annotatsiyalar.entity.EmpCustomer;
import uz.pdp.cascade_types_annotatsiyalar.entity.SimCard;
import uz.pdp.cascade_types_annotatsiyalar.entity.enums.UssdCodsName;
import uz.pdp.cascade_types_annotatsiyalar.payload.DetailingDto;
import uz.pdp.cascade_types_annotatsiyalar.repository.DetailingRepository;
import uz.pdp.cascade_types_annotatsiyalar.repository.SimCardRepository;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DetailingService {

    @Autowired
    SimCardRepository simCardRepository;

    @Autowired
    DetailingRepository detailingRepository;


    public UUID getUUID() {
        EmpCustomer customer = (EmpCustomer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return customer.getId();
    }

    public ApiResponse getDetailing(UUID uuid, String date,String date1) {

        List<Detailing> all = detailingRepository.findAllBySimCardFrom_CustomerIdAndLocalDateBetween
                (uuid,LocalDate.parse(date),LocalDate.parse(date1));

            // ITEXT 7 Kutubxonasi ning kornel,IO va loyout tekin Modul/ ini pom.xml ga joylaymiz.
            // File Örnida PdfWriter b-n  yoziladigan faylni yaratamiz
            try (PdfWriter pdfWriter = new PdfWriter("src/main/resources/detailingPdf.pdf")) {

                // Pdf File ichiga yoziladigan PDf Document yaratamiz
                PdfDocument pdfDocument=new PdfDocument(pdfWriter);

                // pdfDocument ga Yangi sahifa qöshamiz
                pdfDocument.addNewPage();

                // PdfDocument ni saqlash, itex layout ni tanlsh kerak
                Document document=new Document(pdfDocument);

                // Shu yerdan barcha ish/ imizni boshlaymiz
                // Paragraph ichida Text yozamiz. Bunda run bömaydi
                Paragraph paragraph=new Paragraph("Detailing");
                document.add(paragraph);

               // List<Detailing> albums = all;

                // Pdf dagi jadval Id, user Id va Title ustun /ning ölcham/i:
                float [] columns={30f,60f,70f,300f};
                Table table=new Table(columns);

                // Table table=new Table(new float[]{30f, 30, 300f});

                // table ichiga katak/ni solamiz
                table.addCell("Id");
                table.addCell("kindOfAction");
                table.addCell("localDate");
                table.addCell("simCardFrom");


                // For b-n bir martada Cel/ ichiga Array Element/ini solamiz.Har aylanishda bitta qator qöshiladi
                for (Detailing detailing : all) {

                    table.addCell(detailing.getId().toString());
                    table.addCell(detailing.getKindOfAction());
                    table.addCell(String.valueOf(detailing.getLocalDate()));
                    table.addCell(String.valueOf(detailing.getSimCardFrom().getId()));

                }
                document.add(table);
                document.close();

            }
            catch (IOException e) {
                e.printStackTrace();
            }

            return new ApiResponse("Detailing saved",true);
    }

    public ApiResponse addDetailing(DetailingDto detailingDto) {

        Optional<SimCard> optionalFromSimCard = simCardRepository.findById(detailingDto.getSimCardFromUUID());
        if (!optionalFromSimCard.isPresent())
        return new ApiResponse("SimCard with UUID " + (optionalFromSimCard) + " not found", false);


              Detailing detailing=new Detailing();
            switch (detailingDto.getKindOfAction()){
            case 1: detailing.setKindOfAction(UssdCodsName.SEND_SMS.name());break;
            case 2: detailing.setKindOfAction(UssdCodsName.CALL.name());break;
            case 3: detailing.setKindOfAction(UssdCodsName.USE_INTERNET.name());break;
            case 4: detailing.setKindOfAction(UssdCodsName.BUY_PACKAGE.name());break;
            case 5: detailing.setKindOfAction(UssdCodsName.FILL_BALANCE.name());break;
            case 6: detailing.setKindOfAction(UssdCodsName.CHECK_TARIFF.name());break;
            case 7: detailing.setKindOfAction(UssdCodsName.ACTIVATE_TARIFF.name());break;
            case 8: detailing.setKindOfAction(UssdCodsName.CHANGE_TARIFF.name());break;
            case 9: detailing.setKindOfAction(UssdCodsName.BUY_INFO_ENTERTAINMENT.name());break;
            default:return new ApiResponse("SEND_SMS=1, CALL=2, USE_INTERNET=3, BAY_PACKAGE=4,"+
                    " CHANGE_TARIFF=5, USER_INFO_ENTERTAINMENT=6",false);
        }

        detailing.setSimCardFrom(optionalFromSimCard.get());
        detailing.setLocalDate(detailingDto.getLocalDate());
        detailingRepository.save(detailing);
        return new ApiResponse("Detailing saved",true);
    }

    public ApiResponse editDetailing(UUID id, DetailingDto detailingDto) {
        Optional<Detailing> optionalDetailing = detailingRepository.findById(id);
        if (!optionalDetailing.isPresent())
            return new ApiResponse("Detailing not found",false);

        Optional<SimCard> optionalFromSimCard = simCardRepository.findById(detailingDto.getSimCardFromUUID());
        if (!optionalFromSimCard.isPresent())
            return new ApiResponse("SimCard with UUID " + (optionalFromSimCard) + " not found", false);

        Optional<SimCard> optionalToSimCard = simCardRepository.findById(detailingDto.getSimCardToUUID());
        if (!optionalToSimCard.isPresent())
            return new ApiResponse("SimCard with UUID " + (optionalToSimCard) + " not found", false);

            Detailing detailing = optionalDetailing.get();
        switch (detailingDto.getKindOfAction()){
            case 1: detailing.setKindOfAction(UssdCodsName.SEND_SMS.name());break;
            case 2: detailing.setKindOfAction(UssdCodsName.CALL.name());break;
            case 3: detailing.setKindOfAction(UssdCodsName.USE_INTERNET.name());break;
            case 4: detailing.setKindOfAction(UssdCodsName.BUY_PACKAGE.name());break;
            case 5: detailing.setKindOfAction(UssdCodsName.CHANGE_TARIFF.name());break;
            case 6: detailing.setKindOfAction(UssdCodsName.CHECK_TARIFF.name());break;
            case 7: detailing.setKindOfAction(UssdCodsName.ACTIVATE_TARIFF.name());break;
            case 8: detailing.setKindOfAction(UssdCodsName.USER_INFO_ENTERTAINMENT.name());break;
            case 9: detailing.setKindOfAction(UssdCodsName.BUY_INFO_ENTERTAINMENT.name());break;
            default:return new ApiResponse("SEND_SMS=1, CALL=2, USE_INTERNET=3, BAY_PACKAGE=4,"+
                    " CHANGE_TARIFF=5, USER_INFO_ENTERTAINMENT=6",false);
        }
        detailing.setSimCardFrom(optionalFromSimCard.get());
        detailing.setLocalDate(detailingDto.getLocalDate());
        detailingRepository.save(detailing);
        return new ApiResponse("Detailing saved",true);
    }
}







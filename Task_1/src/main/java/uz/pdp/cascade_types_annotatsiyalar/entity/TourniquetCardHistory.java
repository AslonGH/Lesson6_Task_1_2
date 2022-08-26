package uz.pdp.cascade_types_annotatsiyalar.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class TourniquetCardHistory {


    @Id
    @GeneratedValue
    private UUID  id;

    @ManyToOne
    private TourniquetCard  tourniquetCard;

    private Timestamp  getInTime;

    private Timestamp  getOutTime;




    @CreatedBy
    private UUID     createdBy;   // KIM QÖSHGANLIGI

    @LastModifiedBy
    private UUID   updatedBy;    //  KIM TAHRIRLAGANLIGI

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

}

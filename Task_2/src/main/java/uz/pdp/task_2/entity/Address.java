package uz.pdp.task_2.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)  // KIM TOMINIDAN BAJARILGANLIGINI BILISH UCHUN
public class Address {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        private String house;
        private String street;
        private String city;



        @CreatedBy
        private Integer createdBy;

        @LastModifiedBy
        private Integer updatedBy;

        @CreationTimestamp
        private Timestamp createdAt;

        @UpdateTimestamp
        private Timestamp updatedAt;
}

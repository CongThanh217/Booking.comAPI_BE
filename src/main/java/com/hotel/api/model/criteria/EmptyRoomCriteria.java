package com.hotel.api.model.criteria;

import com.hotel.api.model.EmptyRoom;
import com.hotel.api.model.Service;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Data
public class EmptyRoomCriteria {
    private Long id;
    private Long kindId;
    private Integer status;

    public Specification<EmptyRoom> getSpecification() {
        return new Specification<EmptyRoom>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<EmptyRoom> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (getId() != null) {
                    predicates.add(cb.equal(root.get("id"), getId()));
                }
                if (getKindId() != null) {
                    predicates.add(cb.equal(root.get("kindOfRoom").get("id"), getKindId()));
                }
                if (getStatus() != null) {
                    predicates.add(cb.equal(root.get("status"), getStatus()));
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}

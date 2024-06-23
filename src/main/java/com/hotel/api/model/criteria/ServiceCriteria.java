package com.hotel.api.model.criteria;

import com.hotel.api.model.Account;
import com.hotel.api.model.Service;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Data
public class ServiceCriteria {

    private Long id;
    private Long kindId;
    private String serviceName;
    private Integer status;

    public static Specification<Service> findServiceByCriteria(final ServiceCriteria serviceCriteria) {
        return new Specification<Service>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<Service> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();

                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
